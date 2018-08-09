package com.kartoflane.inteca.eval.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FamilyNotFoundException extends RuntimeException {
	FamilyNotFoundException(Integer id) {
		super(String.format("Could not find family with id '%d'.", id));
	}
}