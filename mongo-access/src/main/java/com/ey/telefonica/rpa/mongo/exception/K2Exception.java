package com.ey.telefonica.rpa.mongo.exception;

public class K2Exception extends RuntimeException {
    public K2Exception(String k2Id) {
        super(String.format("IVR with  Id '%s' not found", k2Id));
    }
}
