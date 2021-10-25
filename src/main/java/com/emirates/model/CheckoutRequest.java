package com.emirates.model;

import java.util.List;

import lombok.Data;

@Data
public class CheckoutRequest {

	private List<Integer> bookIds;
	private String promocode;
}
