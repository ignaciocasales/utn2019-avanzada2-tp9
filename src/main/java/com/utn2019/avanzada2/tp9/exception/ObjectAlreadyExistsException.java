package com.utn2019.avanzada2.tp9.exception;

import static java.text.MessageFormat.format;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String object, String field, String value) {
        super(format("{0} with {1} of {2} already exists", object, field, value));
    }
}
