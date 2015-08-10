package org.exadel.training.validator;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	UserService userService;

	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(Class clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		String email = user.getEmail();

		if (!(userService.getUserByEmail(email) == null)) {
			errors.rejectValue("email", "label.error.email.exists");
		}
		// do "complex" validation here

	}

}