package com.emirates.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.emirates.entity.Book;
import com.emirates.model.ApplicationProps;
import com.emirates.model.BookRequest;
import com.emirates.model.CheckoutRequest;
import com.emirates.model.Promo;
import com.emirates.repository.BookRepository;
import com.emirates.service.BookService;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository repository;

	@Autowired
	private ApplicationProps applicationProps;

	@Override
	public List<Book> findAllBooks() {
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<Book> findBook(Map<String, String> requestParam) {
		String name = requestParam.getOrDefault("name", null);
		String description = requestParam.getOrDefault("description", null);
		String author = requestParam.getOrDefault("author", null);
		String bookType = requestParam.getOrDefault("bookType", null);
		String price = requestParam.getOrDefault("price", null);
		String isbn = requestParam.getOrDefault("isbn", null);

		Book book = Book.builder()
				.name(name)
				.description(description)
				.author(author)
				.bookType(bookType)
				.isbn(isbn)
				.price(Objects.nonNull(price) ? Double.parseDouble(price) : null)
				.build();

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("price", exact());

		Example<Book> example = Example.of(book, matcher);
		return StreamSupport.stream(repository.findAll(example).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Book> findBookById(Integer bookId) {
		return repository.findById(bookId);
	}

	@Override
	public String addBook(List<BookRequest> book) {
		List<Book> entityList = new ArrayList<>();
		book.forEach(bookRequest -> {
			Book entity = Book.builder().build();
			BeanUtils.copyProperties(bookRequest, entity);
			entityList.add(entity);
		});
		repository.saveAll(entityList);
		return "Book(s) Added Successfully !!";
	}

	@Override
	public String updateBook(Integer bookId, @Valid BookRequest bookRequest) {

		Optional<Book> optionalBook = repository.findById(bookId)
				.map(blogs -> repository.save(Book.builder()
						.id(bookId)
						.name(bookRequest.getName())
						.description(bookRequest.getDescription())
						.author(bookRequest.getAuthor())
						.bookType(bookRequest.getBookType())
						.price(bookRequest.getPrice())
						.isbn(bookRequest.getIsbn())
						.build()));
		if (optionalBook.isPresent()) {
			return "Book Updated !!!";
		} else {
			return "Oops, The Book does not exist.";
		}
	}

	@Override
	public void deleteBook(Integer bookId) {
		repository.deleteById(bookId);
	}

	@Override
	public Double checkout(CheckoutRequest request) {

		List<Book> bookList = StreamSupport.stream(
						repository.findAllById(request.getBookIds()).spliterator(), false)
				.collect(Collectors.toList());

		Map<String, Double> priceList = new HashMap<>();
		bookList.forEach(book -> applicationProps.getPromos().forEach(promo -> {
			if (isBookValidForPromotion(request, book, promo)) {
				double discount = 100.00 - promo.getDiscount();
				priceList.put(book.getName(),
						discount == 0.0 ? book.getPrice() : ((book.getPrice() * discount) / 100));
			}
		}));
		return priceList.values().stream().mapToDouble(Double::doubleValue).sum();
	}

	private boolean isBookValidForPromotion(CheckoutRequest request, Book book, Promo promo) {
		return promo.getPromocode().equalsIgnoreCase(request.getPromocode()) && book.getBookType()
				.equalsIgnoreCase(promo.getBookType()) && LocalDateTime.now()
				.isBefore(
						promo.getExpireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	}
}
