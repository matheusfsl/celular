package com.celular.celular.Exception;

public class CelularUpdateExeception extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CelularUpdateExeception(String msg) {
        super(msg);
    }

    public CelularUpdateExeception(String msg, Throwable cause) {
        super(msg, cause);
    }
}
