package org.inbank.petko.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for both Max and Min validations.
 * Is used by {@link MaxMinValidator} to validate passed vars Types(!) and Values
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MaxMinValidator.class})
public @interface MaxMin {
    String max() default "";
    String min() default "";
    String message() default "Default error message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
