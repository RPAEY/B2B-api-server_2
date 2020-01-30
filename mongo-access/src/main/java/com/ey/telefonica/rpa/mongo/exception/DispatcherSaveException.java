package com.ey.telefonica.rpa.mongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class DispatcherSaveException extends RuntimeException {
    public DispatcherSaveException() {
        super(String.format("Error updating dispatcher task."));
    }
}
