package org.exadel.training.validator;

import org.exadel.training.model.User;
import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, User> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(Unique unique) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUserByEmail(user.getEmail()) == null;
    }
}
