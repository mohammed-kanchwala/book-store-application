package com.emirates.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.emirates.entity.Book;
import com.emirates.model.BookRequest;
import com.emirates.model.CheckoutRequest;

public interface BookService {

	List<Book> findAllBooks();

	List<Book> findBook(Map<String, String> requestParam);

	Optional<Book> findBookById(Integer bookId);

	String addBook(List<BookRequest> book);

	String updateBook(Integer bookId, @Valid BookRequest bookRequest);

	void deleteBook(Integer bookId);

	Double checkout(CheckoutRequest request);

}
