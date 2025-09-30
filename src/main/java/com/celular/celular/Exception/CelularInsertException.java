package com.celular.celular.Exception;

public class CelularInsertException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CelularInsertException(String msg) {
        super(msg);
    }

    public CelularInsertException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
