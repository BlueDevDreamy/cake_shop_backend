package com.andjela.diplomski.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends AppException {
    private String entityName;
    private String entityValue;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
