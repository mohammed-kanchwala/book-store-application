package com.emirates.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emirates.entity.Book;
import com.emirates.model.BookRequest;
import com.emirates.model.CheckoutRequest;
import com.emirates.service.BookService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book-store")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/books")
	public Mono<List<Book>> getAllBooks() {
		return Mono.just(bookService.findAllBooks());
	}

	@GetMapping("/books/search")
	public ResponseEntity<List<Book>> findBook(@RequestParam Map<String, String> requestParam) {
		return ResponseEntity.ok(bookService.findBook(requestParam));
	}

	@GetMapping("/books/{bookId}")
	public Mono<Optional<Book>> findBook(@PathVariable Integer bookId) {
		return Mono.just(bookService.findBookById(bookId));
	}

	@PostMapping("/books")
	public Mono<String> addBook(@Valid @RequestBody List<BookRequest> book) {
		return Mono.just(bookService.addBook(book));
	}

	@PutMapping( path = "/books/{bookId}")
	public Mono<String> updateBook(@PathVariable Integer bookId,
			@Valid @RequestBody BookRequest bookRequest) {
		return Mono.just(bookService.updateBook(bookId, bookRequest));
	}

	@DeleteMapping("/books/{bookId}")
	public Mono<String> deleteBook(@PathVariable Integer bookId) {
		bookService.deleteBook(bookId);
		return Mono.empty();
	}

	@PostMapping("books/checkout")
	public Mono<Double> checkout(@RequestBody CheckoutRequest request) {
		return Mono.just(bookService.checkout(request));
	}

}
