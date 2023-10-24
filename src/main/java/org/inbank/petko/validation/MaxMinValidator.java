package org.inbank.petko.validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * {@link MaxMin} annotation Validator. Doesn't throw Exception, if User passed Argument of wrong Type
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Component
@Slf4j
public class MaxMinValidator implements ConstraintValidator<MaxMin, Number> {

    private final String VALIDATION_BAD_OPTION = "validation.bad.option";
    private final String VALIDATION_VALUE_ERROR_MAX = "validation.value.error.max";
    private final String VALIDATION_VALUE_ERROR_MIN = "validation.value.error.min";

    private final Environment env;
    private final MessageSource messageSource;
    private final HttpServletRequest request;

    private Double max;
    private Double min;

    /**
     * Constructor with required Autowired Spring beans
     * @param env           current Spring Environment
     * @param messageSource current Spring messages Source
     * @param request       current HttpServletRequest
     */
    @Autowired
    public MaxMinValidator(Environment env, MessageSource messageSource, HttpServletRequest request) {
        this.env = env;
        this.messageSource = messageSource;
        this.request = request;
    }

    /**
     * Override default initializer in order to operate with all Annotation's parameters
     * @param constraintAnnotation annotation to be connected to
     */
    @Override
    public void initialize(MaxMin constraintAnnotation) {
        try {
            if (!constraintAnnotation.max().isBlank()) {
                max = parseAttribute(constraintAnnotation.max());
            }
            if (!constraintAnnotation.min().isBlank()) {
                min = parseAttribute(constraintAnnotation.min());
            }
        } catch (Exception e) {
            log.error("At least one of validation parameters is invalid. [{}, {}]",
                    constraintAnnotation.max(), constraintAnnotation.min());
            max = min = null;
        }
    }

    /**
     * Check if passed Value meets requirements
     * @param value   to be checked
     * @param context requirements holder
     * @return answer of if passed Value meets requirements
     */
    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        StringBuilder msgBuilder = new StringBuilder();
        boolean result = true;
        if (max == null && min == null) {
            msgBuilder.append(messageSource.getMessage(VALIDATION_BAD_OPTION, null, request.getLocale()));
            buildFinalMessage(context, msgBuilder);

            return false;
        }
        if (max != null && value.doubleValue() > max) {
            msgBuilder.append(String.format(messageSource.getMessage(VALIDATION_VALUE_ERROR_MAX, null, request.getLocale()), max));
            result = false;
        }
        if (min != null && value.doubleValue() < min) {
            msgBuilder.append(String.format(messageSource.getMessage(VALIDATION_VALUE_ERROR_MIN, null, request.getLocale()), min));
            result = false;
        }
        if (!result) {
            buildFinalMessage(context, msgBuilder);
        }

        return result;
    }

    private double parseAttribute(String attribute) {
        try {
            return Double.parseDouble(attribute);
        } catch (NumberFormatException e) {
            return Double.parseDouble(Objects.requireNonNull(env.getProperty(attribute)));
        }
    }
    private void buildFinalMessage(ConstraintValidatorContext context, StringBuilder msgBuilder) {
        context.disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
        violationBuilder = context.buildConstraintViolationWithTemplate(msgBuilder.toString());
        violationBuilder.addConstraintViolation();
    }

}
