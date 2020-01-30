package com.ey.telefonica.rpa.mongo.exception;

public class IVRException extends RuntimeException {
    public IVRException(String ivrId) {
        super(String.format("IVR with  Id '%s' not found", ivrId));
    }
}
