package com.guild.cabs.test;

import com.guild.cabs.handlers.ICabHandler;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ICabsScenarioModelHelper {

    void assertCabsEqual(
        @Nullable final CabModel cab1,
        @Nullable final CabModel cab2
    );

    void clean() throws Exception;

    @NotNull
    CabRep createTestableCab();

    @NotNull
    CabRep createTestableCab(final double distanceFromTestPointM);

    @NotNull
    ICabHandler getCabHandler();

    @NotNull
    LatLongRep getTestClientPoint();

    void setup() throws Exception;
}
