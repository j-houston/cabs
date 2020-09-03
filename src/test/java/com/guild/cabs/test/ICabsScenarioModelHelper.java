package com.guild.cabs.test;

import com.guild.cabs.handlers.ICabHandler;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.CabSearchInputRep;
import com.guild.cabs.view.LatLongRep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ICabsScenarioModelHelper {

    void assertCabsEqual(
        @Nullable final CabModel cab1,
        @Nullable final CabModel cab2
    );

    void assertCabsEqual(
        @Nullable final CabRep cab1,
        @Nullable final CabRep cab2
    );

    void assertLatLongsEqual(
        @Nullable final LatLongRep latLong1,
        @Nullable final LatLongRep latLong2
    );

    void clean() throws Exception;

    @NotNull
    CabRep createTestableCab() throws Exception;

    @NotNull
    CabRep createTestableCab(final double distanceFromTestPointM) throws Exception;

    @NotNull
    ICabHandler getCabHandler();

    @NotNull
    LatLongRep getPointNearClient(final double distanceFromPointM);

    @NotNull
    CabSearchInputRep getSearchInput();

    @NotNull
    LatLongRep getTestClientPoint();

    void setup() throws Exception;
}
