package com.kartoflane.inteca.eval.spring.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChildNotFoundException extends RuntimeException {
	ChildNotFoundException(Long id) {
		super(String.format("Could not find child with id '%d'.", id));
	}
}