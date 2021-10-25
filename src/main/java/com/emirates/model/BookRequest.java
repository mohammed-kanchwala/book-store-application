package com.emirates.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequest {

	@NotNull
	@Size(max = 200)
	private String name;

	@Size(max = 500)
	private String description;

	private String author;

	private String bookType;

	@NotNull
	private Double price;

	@NotNull
	private String isbn;
}
