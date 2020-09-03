package com.guild.cabs.exceptions;

import com.guild.cabs.view.CabSearchInputRep;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class InvalidCabSearchException extends BaseCabsException {

    private CabSearchInputRep _searchInput;

    public InvalidCabSearchException() {}

    public InvalidCabSearchException(@NotNull final CabSearchInputRep searchInput) {
        super(
            CabsErrorCode.INVALID_CAB_SEARCH_CRITERIA,
            "Invalid cab search criteria",
            HttpStatus.BAD_REQUEST,
            null
        );

        _searchInput = searchInput;
    }

    public CabSearchInputRep getSearchInput() {
        return _searchInput;
    }

    public void setSearchInput(CabSearchInputRep searchInput) {
        _searchInput = searchInput;
    }
}
