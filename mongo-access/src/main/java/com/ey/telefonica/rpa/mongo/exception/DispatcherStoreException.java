package com.ey.telefonica.rpa.mongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class DispatcherStoreException extends RuntimeException {

    public DispatcherStoreException(Throwable cause) {
        super(cause);
    }

    public DispatcherStoreException(String message) {
        super(message);
    }
}
