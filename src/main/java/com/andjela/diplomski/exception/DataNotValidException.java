package com.andjela.diplomski.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNotValidException extends AppException{
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public DataNotValidException(String message) {super(message);}

    public DataNotValidException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is not in a valid format", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public DataNotValidException(String resourceName, String fieldName, Long fieldValue) {
        this(resourceName, fieldName, fieldValue != null ? fieldValue.toString() : null);
    }
}
