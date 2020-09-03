package com.guild.cabs.exceptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class CabNotFoundException extends BaseCabsException {

    private String _cabId;

    public CabNotFoundException() {}

    public CabNotFoundException(@NotNull final String cabId) {
        super(
            CabsErrorCode.CAB_NOT_FOUND,
            String.format(
                "Cab at ID [%s] was not found",
                cabId
            ),
            HttpStatus.NOT_FOUND,
            null
        );

        _cabId = cabId;
    }

    public String getCabId() {
        return _cabId;
    }

    public void setCabId(String cabId) {
        _cabId = cabId;
    }
}
