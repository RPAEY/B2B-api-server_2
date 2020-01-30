package com.ey.telefonica.rpa.mongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DispatcherEmptyException extends RuntimeException {
    public DispatcherEmptyException() {
        super(String.format("No tasks for Dispatcher."));
    }
}
