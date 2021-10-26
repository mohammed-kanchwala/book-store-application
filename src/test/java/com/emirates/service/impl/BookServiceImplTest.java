package com.emirates.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.emirates.entity.Book;
import com.emirates.model.ApplicationProps;
import com.emirates.model.BookRequest;
import com.emirates.model.CheckoutRequest;
import com.emirates.repository.BookRepository;
import com.emirates.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceImplTest {

	@Autowired
	private BookService service;

	@Autowired
	private ApplicationProps props;

	@MockBean
	BookRepository repository;

	private List<Book> bookList;

	@BeforeEach
	void setUp() {
		assertNotNull(service);
		assertNotNull(repository);
		assertNotNull(props);
		this.bookList = new ArrayList<>();
		this.bookList.add(Book.builder()
				.id(1)
				.name("Tech 1")
				.bookType("Technology")
				.price(10.00)
				.author("T 1")
				.isbn("9781111")
				.build());
		this.bookList.add(Book.builder()
				.id(2)
				.name("Tech 2")
				.bookType("Technology")
				.price(10.00)
				.author("T 2")
				.isbn("9782222")
				.build());
		this.bookList.add(Book.builder()
				.id(3)
				.name("Science 1")
				.bookType("Science")
				.price(20.00)
				.author("S 1")
				.isbn("9703333")
				.build());
		this.bookList.add(Book.builder()
				.id(4)
				.name("Science 2")
				.bookType("Science")
				.price(20.00)
				.author("S 2")
				.isbn("9704444")
				.build());
	}

	@Test
	@DisplayName("Get All Books Returns Success")
	void getAllBooksReturnsSuccess() {
		when(repository.findAll()).thenReturn(bookList);

		Flux<Book> response = service.findAllBooks();
		assertNotNull(response);
		assertEquals(4, (int) response.toStream().count());
	}

	@Test
	@DisplayName("Get All Books Returns Error")
	void getAllBooksReturnsError() {
		when(repository.findAll()).thenThrow(new RuntimeException());
		assertThrows(Exception.class, () -> service.findAllBooks());
	}

	@Test
	@DisplayName("Get Book based on ID Returns Success")
	void getBookBasedOnIdReturnsSuccess() {
		when(repository.findById(any())).thenReturn(Optional.of(bookList.get(0)));

		Mono<Book> response = service.findBookById(any());
		assertNotNull(response);
		assertEquals("Tech 1", Objects.requireNonNull(response.block()).getName());
		assertEquals("Technology", Objects.requireNonNull(response.block()).getBookType());
	}

	@Test
	@DisplayName("Get Book based on ID Returns Empty")
	void getBookBasedOnIdReturnsEmpty() {
		when(repository.findById(any())).thenReturn(Optional.empty());

		Mono<Book> response = service.findBookById(any());
		assertNull(response);
	}

	@Test
	@DisplayName("Get Book based on ID Returns Error")
	void getBookBasedOnIdReturnsError() {
		when(repository.findById(any())).thenThrow(new RuntimeException());
		assertThrows(Exception.class, () -> service.findBookById(any()));
	}

	@Test
	@DisplayName("Get Books based on Name Request Param Returns Success")
	void getBooksBasedOnNameReturnsSuccess() {
		List<Book> filteredBooks = bookList.stream()
				.filter(book -> book.getName().contains("1"))
				.collect(Collectors.toList());
		when(repository.findAll(any())).thenReturn(filteredBooks);

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("name", "1");
		Flux<Book> response = service.findBook(requestParam);
		assertNotNull(response);
		assertEquals(2, response.toStream().count());
	}

	@Test
	@DisplayName("Get Books based on Author Request Param Returns Success")
	void getBooksBasedOnAuthorReturnsSuccess() {
		List<Book> filteredBooks = bookList.stream()
				.filter(book -> book.getAuthor().contains("T"))
				.collect(Collectors.toList());
		when(repository.findAll(any())).thenReturn(filteredBooks);

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("author", "T");
		Flux<Book> response = service.findBook(requestParam);
		assertNotNull(response);
		assertEquals(2, response.toStream().count());
	}

	@Test
	@DisplayName("Get Books based on Book Type Request Param Returns Success")
	void getBooksBasedOnBookTypeReturnsSuccess() {
		List<Book> filteredBooks = bookList.stream()
				.filter(book -> book.getBookType().contains("Tech"))
				.collect(Collectors.toList());
		when(repository.findAll(any())).thenReturn(filteredBooks);

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("bookType", "Tech");
		Flux<Book> response = service.findBook(requestParam);
		assertNotNull(response);
		assertEquals(2, response.toStream().count());

	}

	@Test
	@DisplayName("Get Books based on Price Request Param Returns Success")
	void getBooksBasedOnPriceReturnsSuccess() {
		List<Book> filteredBooks = bookList.stream()
				.filter(book -> book.getPrice().equals(10.00))
				.collect(Collectors.toList());
		when(repository.findAll(any())).thenReturn(filteredBooks);

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("price", "10.00");
		Flux<Book> response = service.findBook(requestParam);
		assertNotNull(response);
		assertEquals(2, response.toStream().count());

	}

	@Test
	@DisplayName("Get Books based on ISBN Request Param Returns Success")
	void getBooksBasedOnIsbnReturnsSuccess() {
		List<Book> filteredBooks = bookList.stream()
				.filter(book -> book.getIsbn().contains("1111"))
				.collect(Collectors.toList());
		when(repository.findAll(any())).thenReturn(filteredBooks);

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("isbn", "1111");
		Flux<Book> response = service.findBook(requestParam);
		assertNotNull(response);
		assertEquals(1, response.toStream().count());

	}

	@Test
	@DisplayName("Add Book(s) Should Return Success")
	void addBooksShouldReturnSuccess() {
		when(repository.saveAll(mock(List.class))).thenReturn(mock(List.class));

		Mono<String> response = service.addBook(mock(List.class));

		assertNotNull(response);
		assertEquals("0 - Book(s) Added Successfully !!", response.block());
	}

	@Test
	@DisplayName("Update Book Should Return Success")
	void updatedBookShouldReturnSuccess() {
		when(repository.findById(anyInt())).thenReturn(Optional.of(bookList.get(0)));
		service.updateBook(anyInt(), BookRequest.builder().build());
		verify(repository).save(any(Book.class));
	}

	@Test
	@DisplayName("Delete Book Should Return Success")
	void deleteBookShouldReturnSuccess() {

		Mono<String> response = service.deleteBook(anyInt());
		verify(repository).deleteById(anyInt());
		assertNotNull(response);
	}

	@Test
	@DisplayName("Checkout Should Return Success")
	void checkoutShouldReturnSuccess() {
		when(repository.findAllById(anyList())).thenReturn(bookList);

		CheckoutRequest request = new CheckoutRequest();
		request.setBookIds(anyList());
		request.setPromocode("OFFERFIC10");
		props.getPromos().forEach(promo -> {
			if (promo.getBookType().equalsIgnoreCase("TECHNOLOGY")) {
				promo.setDiscount(10);
			}
			if (promo.getBookType().equalsIgnoreCase("SCIENCE")) {
				promo.setDiscount(0);
			}

		});

		Mono<Double> response = service.checkout(request);
		assertNotNull(response);
		assertEquals(58.00, response.block());
	}

	@Test
	@DisplayName("Checkout Should Return Success for 0 Discount")
	void checkoutShouldReturnSuccess_0Discount() {
		when(repository.findAllById(anyList())).thenReturn(bookList);

		CheckoutRequest request = new CheckoutRequest();
		request.setBookIds(anyList());
		request.setPromocode("OFFERFIC10");
		props.getPromos().forEach(p -> p.setDiscount(0));

		Mono<Double> response = service.checkout(request);
		assertNotNull(response);
		assertEquals(60.00, response.block());
	}
}
