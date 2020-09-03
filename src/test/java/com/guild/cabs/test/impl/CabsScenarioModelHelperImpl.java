package com.guild.cabs.test.impl;

import com.guild.cabs.handlers.ICabHandler;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.test.ICabsScenarioModelHelper;
import com.guild.cabs.utils.SpatialUtils;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabsScenarioModelHelperImpl implements ICabsScenarioModelHelper {

    private static final LatLongRep TEST_CLIENT_POINT = new LatLongRep(
        39.928160,
        105.162143
    );

    private final ICabHandler _cabHandler;

    @Autowired
    public CabsScenarioModelHelperImpl(final ICabHandler cabHandler) {
        _cabHandler = cabHandler;
    }

    @Override
    public void assertCabsEqual(
        @Nullable final CabModel cab1,
        @Nullable final CabModel cab2
    ) {
        if ((cab1 == null) && (cab2 == null)) {
            return;
        }

        if ((cab1 != null) && (cab2 == null)) {
            Assert.fail("cab1 was not null while cab2 was null");
        }

        if ((cab1 == null) && (cab2 != null)) {
            Assert.fail("cab1 was null while cab2 was not");
        }

        Assert.assertEquals(
            cab1.getId(),
            cab2.getId()
        );

        Assert.assertEquals(
            cab1.getLatitude(),
            cab2.getLatitude()
        );

        Assert.assertEquals(
            cab1.getLongitude(),
            cab2.getLongitude()
        );
    }

    @Override
    public void clean() throws Exception {
        _cabHandler.destroyAllCabs();
    }

    @Override
    public @NotNull CabRep createTestableCab() {
        return createTestableCab(100);
    }

    @Override
    public @NotNull CabRep createTestableCab(final double distanceFromTestPointM) {
        final LatLongRep nearby = getPointNearClient(distanceFromTestPointM);
        return _cabHandler.createNewCab(nearby);
    }

    @Override
    public @NotNull ICabHandler getCabHandler() {
        return _cabHandler;
    }

    @Override
    public @NotNull LatLongRep getTestClientPoint() {
        return TEST_CLIENT_POINT;
    }

    public @NotNull LatLongRep getPointNearClient(final double distanceFromPointM) {
        final double[] nearby = SpatialUtils.getLocationFromSourceAndDistance(
            TEST_CLIENT_POINT.getLatitude(),
            TEST_CLIENT_POINT.getLongitude(),
            distanceFromPointM
        );

        return new LatLongRep(
            nearby[0],
            nearby[1]
        );
    }

    @Override
    public void setup() throws Exception {
        clean();
    }
}
