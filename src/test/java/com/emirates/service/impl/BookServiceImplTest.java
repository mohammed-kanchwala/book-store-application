package com.emirates.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.emirates.controller.BookController;
import com.emirates.entity.Book;
import com.emirates.repository.BookRepository;
import com.emirates.service.BookService;

import static org.mockito.Mockito.times;

@WebFluxTest(controllers = BookController.class)
class BookServiceImplTest {

	@MockBean
	BookService bookService;

	@Autowired
	private WebTestClient client;

	@MockBean
	BookRepository repository;

	@BeforeEach
	void verify() {
		Assertions.assertNotNull(bookService);
		Assertions.assertNotNull(client);
		Assertions.assertNotNull(repository);
	}

	@Test
	void getAllBooks() {
		List<Book> list = new ArrayList<>();

		client.get().uri("/api/book-store/books").exchange().expectStatus().isOk();

		Mockito.verify(repository, times(1)).findAll();

	}
}
