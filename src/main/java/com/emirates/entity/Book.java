package com.emirates.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@Entity(name = "BOOK")
@Table(name = "BOOK")
@AllArgsConstructor
public class Book {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(max = 200)
	@Column(name = "NAME")
	private String name;

	@Size(max = 500)
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "BOOK_TYPE")
	private String bookType;

	@NotNull
	@Column(name = "PRICE")
	private Double price;

	@NotNull
	@Column(name = "ISBN")
	private String isbn;

	public Book() {
	}
}
