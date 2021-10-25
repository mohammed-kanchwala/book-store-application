package com.emirates.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emirates.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	Iterable<Book> findAll(Example<Book> example);

}
