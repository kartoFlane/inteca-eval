package com.kartoflane.inteca.eval.spring.data.validator;

import com.kartoflane.inteca.eval.spring.data.entity.Child;

public class ChildValidator implements Validator<Child> {
	private static final ChildValidator _instance = new ChildValidator();

	private ChildValidator() {
	}

	public static ChildValidator getInstance() {
		return _instance;
	}

	public boolean isValid(Child child) {
		if (
			child.getFirstName()  == null ||
			child.getSecondName() == null ||
			child.getPesel()      == null ||
			child.getBirthDate()  == null ||
			child.getSex()        == null
		) {
			return false;
		}

		if (child.getFirstName().length() == 0 || child.getSecondName().length() == 0) {
			return false;
		}

		if (!child.getSex().matches("[MF]")) {
			return false;
		}

		if (child.getPesel().length() != 11 || !tryParse(child.getPesel())) {
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
