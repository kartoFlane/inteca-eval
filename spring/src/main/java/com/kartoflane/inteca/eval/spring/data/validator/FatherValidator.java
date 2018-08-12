package com.kartoflane.inteca.eval.spring.data.validator;

import com.kartoflane.inteca.eval.spring.data.entity.Father;

public class FatherValidator implements Validator<Father> {
	private static final FatherValidator _instance = new FatherValidator();

	private FatherValidator() {
	}

	public static FatherValidator getInstance() {
		return _instance;
	}

	public boolean isValid(Father father) {
		if (
			father.getFirstName()  == null ||
			father.getSecondName() == null ||
			father.getPesel()      == null ||
			father.getBirthDate()  == null
		) {
			return false;
		}

		if (father.getFirstName().length() == 0 || father.getSecondName().length() == 0) {
			return false;
		}

		if (father.getPesel().length() != 11 || !tryParse(father.getPesel())) {
			return false;
		}

		// TODO: Could go more in-depth and validate pesel format...

		return true;
	}

	private boolean tryParse(String input) {
		try {
			Long.parseLong(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
