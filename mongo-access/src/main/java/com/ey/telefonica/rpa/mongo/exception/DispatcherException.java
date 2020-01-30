package com.ey.telefonica.rpa.mongo.exception;

public class DispatcherException extends RuntimeException {
    public DispatcherException(String dispatcherId) {
        super(String.format("Dispatcher with  Id '%s' not found", dispatcherId));
    }
}
