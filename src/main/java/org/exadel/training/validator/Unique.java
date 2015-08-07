package org.exadel.training.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {
    String message() default "{User}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
/*
    Class<?> entity();

    String property() default "email";*/
}
