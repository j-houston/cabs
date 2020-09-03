package com.guild.cabs.exceptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class CabValidationException extends BaseCabsException {

    public CabValidationException() {}

    public CabValidationException(@NotNull final String message) {
        super(
            CabsErrorCode.CAB_VALIDATION_EXCEPTION,
            message,
            HttpStatus.BAD_REQUEST,
            null
        );
    }
}
