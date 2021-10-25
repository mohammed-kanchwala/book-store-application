package com.emirates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emirates.service.BookService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookStoreApplicationTest {

	@Autowired
	private BookService service;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(service);
	}
}
