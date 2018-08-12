package com.kartoflane.inteca.eval.spring.data.validator;

public interface Validator<T> {
	boolean isValid(T o);
}
