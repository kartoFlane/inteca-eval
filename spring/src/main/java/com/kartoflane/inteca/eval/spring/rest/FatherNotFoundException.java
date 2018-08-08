package com.kartoflane.inteca.eval.spring.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FatherNotFoundException extends RuntimeException {
	FatherNotFoundException(Long id) {
		super(String.format("Could not find father with id '%d'.", id));
	}
}