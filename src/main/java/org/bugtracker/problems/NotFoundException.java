package org.bugtracker.problems;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Requested item not found")
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long objIdentifier;

    public <T> NotFoundException(Class<T> cls, Long id) {
        super(cls.getSimpleName() + " with id: " + id + " does not exist!");
    }

    public NotFoundException(String s) {
        super(s);
    }

    public Long getObjIdentifier() {
        return objIdentifier;
    }
}