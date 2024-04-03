package com.andjela.diplomski.exception;

public class ApiValidationException extends AppException {
    private String field;
    private String value;

    public ApiValidationException(String message) {
        super(message);
    }

    public ApiValidationException(String field, String value, String message) {
        super(message);
        this.field = field;
        this.value = value;
    }
}
