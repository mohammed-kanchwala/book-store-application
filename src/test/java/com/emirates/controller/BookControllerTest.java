package com.emirates.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

import com.emirates.entity.Book;
import com.emirates.model.BookRequest;
import com.emirates.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@WebFluxTest(value = BookController.class, excludeAutoConfiguration = {
		SecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class})
class BookControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	private BookService service;

	private List<Book> bookList;

	@BeforeEach
	void setUp() {
		this.bookList = new ArrayList<>();
		this.bookList.add(
				Book.builder().name("Tech 1").bookType("Technology").price(0.99).author("T 1").build());
		this.bookList.add(
				Book.builder().name("Tech 2").bookType("Technology").price(1.99).author("T 2").build());
		this.bookList.add(
				Book.builder().name("Science 1").bookType("Science").price(0.99).author("S 1").build());
		this.bookList.add(
				Book.builder().name("Science 2").bookType("Science").price(1.99).author("S 1").build());
	}

	@Test
	@DisplayName("Fetch All Books")
	void shouldFetchAllBooks() {
		when(service.findAllBooks()).thenReturn(Flux.fromIterable(bookList));

		webTestClient.get()
				.uri("/api/book-store/books")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(Book.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.get(0).getName().equals("Tech 1"), "name not equal");
					Assert.isTrue(response.get(0).getBookType().equals("Technology"), "book type not equal");
				});
	}

	@Test
	@DisplayName("Fetch All Books Exception Test")
	void shouldReturnExceptionForFetchAllBooks() {
		when(service.findAllBooks()).thenReturn(Flux.error(new Exception("Error")));

		webTestClient.get()
				.uri("/api/book-store/books")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

	@Test
	@DisplayName("Find By ID Returns Book")
	void shouldReturnBookById() {
		Book book = Book.builder().id(1).name("Tech 1").bookType("Technology").build();
		when(service.findBookById(1)).thenReturn(Mono.just(book));

		webTestClient.get()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(Book.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.getName().equals("Tech 1"), "name not equal");
					Assert.isTrue(response.getBookType().equals("Technology"), "book type not equal");
				});
	}

	@Test
	@DisplayName("Find By ID Returns Exception")
	void shouldReturnExceptionOnBookById() {
		Book book = Book.builder().id(1).name("Tech 1").bookType("Technology").build();
		when(service.findBookById(1)).thenReturn(Mono.error(new Exception("Error")));

		webTestClient.get()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

	@Test
	@DisplayName("Find By ID Returns NULL")
	void shouldReturnNullOnBookById() {
		Book book = Book.builder().id(1).name("Tech 1").bookType("Technology").build();
		when(service.findBookById(1)).thenReturn(null);

		webTestClient.get()
				.uri("/api/book-store/books/2")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	@DisplayName("Update Book based on ID Returns Success")
	void shouldUpdateBasedOnId() {
		BookRequest request = BookRequest.builder().name("Tech updated").build();
		when(service.updateBook(1, request)).thenReturn(Mono.just("Book Updated !!!"));

		webTestClient.put()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(request), BookRequest.class)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(String.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.equals("Book Updated !!!"), "not updated");
				});
	}

	@Test
	@DisplayName("Update Book based on ID Returns Exception")
	void shouldReturnExceptionOnUpdateBasedOnId() {
		BookRequest request = BookRequest.builder().name("Tech updated").build();
		when(service.updateBook(1, request)).thenReturn(Mono.error(new Exception("Error")));

		webTestClient.put()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(request), BookRequest.class)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

	@Test
	@DisplayName("Update Book based on ID Returns Error")
	void shouldNotUpdateBasedOnId() {
		BookRequest request = BookRequest.builder().name("Tech updated").build();
		when(service.updateBook(1, request)).thenReturn(Mono.just("Oops, The Book does not exist."));

		webTestClient.put()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(request), BookRequest.class)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(String.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.equals("Oops, The Book does not exist."), "book updated");
				});
	}

	@Test
	@DisplayName("Add Books Returns Success")
	void shouldAddBook() {
		List<BookRequest> bookRequestList = new ArrayList<>();
		bookList.forEach(book -> {
			BookRequest request = BookRequest.builder().build();
			BeanUtils.copyProperties(book, request);
			bookRequestList.add(request);
		});
		when(service.addBook(bookRequestList)).thenReturn(Mono.just("Book(s) Added Successfully !!"));

		webTestClient.post()
				.uri("/api/book-store/books")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(bookRequestList), List.class)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(String.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.equals("Book(s) Added Successfully !!"), "addition failed");
				});
	}

	@Test
	@DisplayName("Add Books Returns Exception")
	void returnExceptionOnAddBook() {
		List<BookRequest> bookRequestList = new ArrayList<>();
		bookList.forEach(book -> {
			BookRequest request = BookRequest.builder().build();
			BeanUtils.copyProperties(book, request);
			bookRequestList.add(request);
		});
		when(service.addBook(bookRequestList)).thenReturn(Mono.error(new Exception("Error")));

		webTestClient.post()
				.uri("/api/book-store/books")
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(bookRequestList), List.class)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}


	@Test
	@DisplayName("Delete Book based on ID Returns Success")
	void shouldDeleteBasedOnId() {
		when(service.deleteBook(1)).thenReturn(Mono.just("Book Deleted Successfully !!"));

		webTestClient.delete()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(String.class)
				.value(response -> {
					assertNotNull(response);
					Assert.isTrue(response.equals("Book Deleted Successfully !!"), "not updated");
				});
	}

	@Test
	@DisplayName("Delete Book based on ID Returns Exception")
	void shouldReturnExceptionOnDeleteBasedOnId() {
		when(service.deleteBook(1)).thenReturn(Mono.error(new Exception("")));

		webTestClient.delete()
				.uri("/api/book-store/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.is5xxServerError();
	}

}
