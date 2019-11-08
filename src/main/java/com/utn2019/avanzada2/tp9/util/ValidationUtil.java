package com.utn2019.avanzada2.tp9.util;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

import com.utn2019.avanzada2.tp9.exception.InvalidDomainException;
import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@UtilityClass
public class ValidationUtil {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validateObject(T object) {
        final Set<ConstraintViolation<T>> violations = VALIDATOR.validate(object);
        if (!violations.isEmpty()) {
            final String allViolations = violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).sorted().collect(joining(" - "));
            throw new InvalidDomainException(format("{0} is invalid: {1}", object.getClass().getSimpleName(), allViolations));
        }
    }
}
