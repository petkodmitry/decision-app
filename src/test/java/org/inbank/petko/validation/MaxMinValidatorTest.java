package org.inbank.petko.validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test of the MaxMin Validator of MaxMin annotation
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@ExtendWith(MockitoExtension.class)
public class MaxMinValidatorTest {

    @Mock
    private Environment env;
    @Mock
    private HttpServletRequest request;
    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
    @Mock
    ConstraintValidatorContext context;

    private ResourceBundleMessageSource messageSource;

    @BeforeEach
    void setup() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages");
    }

    @Test
    void testValidationOk() {
        MaxMinImpl maxMin = new MaxMinImpl("2000", "10000");
        MaxMinValidator maxMinValidator = new MaxMinValidator(env, messageSource, request);
        maxMinValidator.initialize(maxMin);
        assertTrue(maxMinValidator.isValid(3000, context));
    }

    @Test
    void testValidationLess() {
        MaxMinImpl maxMin = new MaxMinImpl("2000", "10000");
        MaxMinValidator maxMinValidator = new MaxMinValidator(env, messageSource, request);
        maxMinValidator.initialize(maxMin);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        assertFalse(maxMinValidator.isValid(200, context));
    }

    @Test
    void testValidationMore() {
        MaxMinImpl maxMin = new MaxMinImpl("2000", "10000");
        MaxMinValidator maxMinValidator = new MaxMinValidator(env, messageSource, request);
        maxMinValidator.initialize(maxMin);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        assertFalse(maxMinValidator.isValid(40000, context));
    }

    @Test
    void testValidationInvalidConstraint() {
        MaxMinImpl maxMin = new MaxMinImpl("qwe", "rty");
        MaxMinValidator maxMinValidator = new MaxMinValidator(env, messageSource, request);
        maxMinValidator.initialize(maxMin);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        assertFalse(maxMinValidator.isValid(9876, context));
    }

    static class MaxMinImpl implements MaxMin {
        private final String max;
        private final String min;

        public MaxMinImpl(String min, String max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String max() { return max;}
        @Override
        public String min() { return min; }
        @Override
        public String message() { return "test"; }
        @Override
        public Class<?>[] groups() { return new Class[0]; }
        @Override
        public Class<? extends Payload>[] payload() { return new Class[0]; }
        @Override
        public Class<? extends Annotation> annotationType() { return MaxMin.class; }
    }
}
