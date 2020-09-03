package com.guild.cabs.handlers;

import com.guild.cabs.AbstractCabsDataTest;
import com.guild.cabs.exceptions.CabNotFoundException;
import com.guild.cabs.exceptions.CabValidationException;
import com.guild.cabs.persistence.ICabRepository;
import com.guild.cabs.utils.SpatialUtils;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCabHandlerCRUD extends AbstractCabsDataTest {

    private final ICabHandler _cabHandler;
    private final ICabRepository _cabRepository;

    @Autowired
    public TestCabHandlerCRUD(
        final ICabHandler cabHandler,
        final ICabRepository cabRepository
    ) {
        _cabHandler = cabHandler;
        _cabRepository = cabRepository;
    }

    @Test
    public void testCreateCab() throws Exception {
        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        final CabRep cabRep = _cabHandler.createNewCab(testPoint);
        Assert.assertNotNull(cabRep);

        Assert.assertEquals(
            1,
            _cabRepository.count()
        );

        final String cabId = cabRep.getId();
        Assert.assertNotNull(cabId);
        _cabsScenarioModelHelper.assertLatLongsEqual(
            testPoint,
            cabRep
        );

        final CabRep loadedCab = _cabHandler.getCab(cabId);
        _cabsScenarioModelHelper.assertCabsEqual(
            cabRep,
            loadedCab
        );
    }

    @Test
    public void testCreateCabHighLat() throws Exception {
        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        testPoint.setLatitude(91.0);
        try {
            _cabHandler.createNewCab(testPoint);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // expected
        }
    }

    @Test
    public void testCreateCabLowLat() throws Exception {
        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        testPoint.setLatitude(-91.0);
        try {
            _cabHandler.createNewCab(testPoint);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // expected
        }
    }

    @Test
    public void testCreateCabHighLon() throws Exception {
        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        testPoint.setLongitude(181.0);
        try {
            _cabHandler.createNewCab(testPoint);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // expected
        }
    }

    @Test
    public void testCreateCabLowLon() throws Exception {
        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        testPoint.setLongitude(-181.0);
        try {
            _cabHandler.createNewCab(testPoint);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // expected
        }
    }

    @Test
    public void testDestroyCab() throws Exception {
        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        final CabRep cabRep = _cabHandler.createNewCab(testPoint);
        Assert.assertNotNull(cabRep);

        final String cabId = cabRep.getId();
        Assert.assertNotNull(cabId);

        Assert.assertEquals(
            1,
            _cabRepository.count()
        );

        _cabHandler.destroyCab(cabId);
        try {
            _cabHandler.getCab(cabId);
            Assert.fail("expected exception");
        } catch(CabNotFoundException e) {
            // expected
        }

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        // Just shouldn't blow up on a double delete
        _cabHandler.destroyCab(cabId);

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );
    }

    @Test
    public void testDestroyAllCabs() throws Exception {
        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        for (int i = 0; i < 10; i++) {
            final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
            _cabHandler.createNewCab(testPoint);
            Assert.assertEquals(
                i + 1,
                _cabRepository.count()
            );
        }

        _cabHandler.destroyAllCabs();

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        // Just shouldn't blow up on a double nuke from orbit
        _cabHandler.destroyAllCabs();

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );
    }

    @Test
    public void testUpdateCabNotFound() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        cabInput.setId("notfound");

        try {
            _cabHandler.updateCab(cabInput);
            Assert.fail("expected exception");
        } catch(CabNotFoundException e) {
            // Expected
        }
    }

    @Test
    public void testUpdateCabLowLat() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        cabInput.setLatitude(-91.0);

        try {
            _cabHandler.updateCab(cabInput);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // Expected
        }
    }

    @Test
    public void testUpdateCabHighLat() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        cabInput.setLatitude(91.0);

        try {
            _cabHandler.updateCab(cabInput);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // Expected
        }
    }

    @Test
    public void testUpdateCabLowLon() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        cabInput.setLongitude(-181.0);

        try {
            _cabHandler.updateCab(cabInput);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // Expected
        }
    }

    @Test
    public void testUpdateCabHighLon() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        cabInput.setLongitude(181.0);

        try {
            _cabHandler.updateCab(cabInput);
            Assert.fail("expected exception");
        } catch(CabValidationException e) {
            // Expected
        }
    }

    @Test
    public void testUpdateCab() throws Exception {
        final CabRep cabInput = _cabsScenarioModelHelper.createTestableCab();
        final double[] newPoints = SpatialUtils.getLocationFromSourceAndDistance(
            cabInput.getLatitude(),
            cabInput.getLongitude(),
            100
        );
        cabInput.setLatitude(newPoints[0]);
        cabInput.setLongitude(newPoints[1]);

        final CabRep updatedCab = _cabHandler.updateCab(cabInput);
        _cabsScenarioModelHelper.assertCabsEqual(
            cabInput,
            updatedCab
        );

        final CabRep loadedCab = _cabHandler.getCab(updatedCab.getId());
        _cabsScenarioModelHelper.assertCabsEqual(
            cabInput,
            loadedCab
        );
    }
}
