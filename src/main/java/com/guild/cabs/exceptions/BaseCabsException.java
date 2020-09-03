package com.guild.cabs.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseCabsException extends Exception {

    private CabsErrorCode _errorCode;
    private String _message;
    private HttpStatus _statusCode;

    public BaseCabsException() {}

    public BaseCabsException(
        final CabsErrorCode errorCode,
        final String message,
        final HttpStatus statusCode,
        final Exception rootException
    ) {
        super(
            message,
            rootException
        );

        _errorCode = errorCode;
        _message = message;
        _statusCode = statusCode;
    }

    public CabsErrorCode getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(CabsErrorCode errorCode) {
        _errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public HttpStatus getStatusCode() {
        return _statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        _statusCode = statusCode;
    }
}
