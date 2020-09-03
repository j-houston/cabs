package com.guild.cabs.handlers;

import com.guild.cabs.exceptions.CabNotFoundException;
import com.guild.cabs.exceptions.CabValidationException;
import com.guild.cabs.exceptions.InvalidCabSearchException;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.CabSearchInputRep;
import com.guild.cabs.view.CabsPageRep;
import com.guild.cabs.view.LatLongRep;
import org.jetbrains.annotations.NotNull;

public interface ICabHandler {

    @NotNull
    CabRep createNewCab(@NotNull LatLongRep initialLocation) throws CabValidationException;

    void destroyAllCabs();

    void destroyCab(@NotNull final String cabId);

    @NotNull
    CabRep getCab(@NotNull String cabId) throws CabNotFoundException;

    @NotNull
    CabRep modelToRep(@NotNull CabModel cabModel);

    @NotNull
    CabsPageRep searchCabs(@NotNull CabSearchInputRep searchCriteria) throws InvalidCabSearchException;

    @NotNull
    CabRep updateCab(@NotNull CabRep cabInput) throws CabNotFoundException, CabValidationException;
}
