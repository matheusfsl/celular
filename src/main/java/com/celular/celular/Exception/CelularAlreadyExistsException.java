package com.celular.celular.Exception;

public class CelularAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CelularAlreadyExistsException(String msg) {
        super(msg);
    }

    public CelularAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
