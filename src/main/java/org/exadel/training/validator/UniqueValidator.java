package org.exadel.training.validator;

import org.exadel.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(Unique unique) {
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUserByEmail(string) == null;
    }
}
