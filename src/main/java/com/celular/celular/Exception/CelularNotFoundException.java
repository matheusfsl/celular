package com.celular.celular.Exception;

public class CelularNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CelularNotFoundException(String msg) {
        super(msg);
    }

    public CelularNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
