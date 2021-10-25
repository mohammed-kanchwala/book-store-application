package com.emirates.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.emirates.entity.Book;
import com.emirates.model.BookRequest;
import com.emirates.model.CheckoutRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

	Flux<Book> findAllBooks();

	Flux<Book> findBook(Map<String, String> requestParam);

	Mono<Book> findBookById(Integer bookId);

	Mono<String> addBook(List<BookRequest> book);

	Mono<String> updateBook(Integer bookId, @Valid BookRequest bookRequest);

	Mono<String> deleteBook(Integer bookId);

	Mono<Double> checkout(CheckoutRequest request);

}
