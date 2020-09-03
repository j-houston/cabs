package com.guild.cabs.persistence;

import com.guild.cabs.AbstractCabsDataTest;
import com.guild.cabs.model.CabModel;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestCabRepository extends AbstractCabsDataTest {

    private final ICabRepository _cabRepository;

    @Autowired
    public TestCabRepository(final ICabRepository cabRepository) {
        _cabRepository = cabRepository;
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        final Optional<CabModel> optCab = _cabRepository.findById("testFindByIdNotFound");
        Assert.assertFalse(optCab.isPresent());
    }

    @Test
    public void testSaveAndGetById() throws Exception {
        final LatLongRep nearby = _cabsScenarioModelHelper.getTestClientPoint();
        final CabModel testCab = new CabModel(
            CabRep.newId(),
            nearby.getLatitude(),
            nearby.getLongitude()
        );
        final CabModel savedCab = _cabRepository.save(testCab);
        _cabsScenarioModelHelper.assertCabsEqual(
            testCab,
            savedCab
        );

        final Optional<CabModel> foundCab = _cabRepository.findById(testCab.getId());
        Assert.assertTrue(foundCab.isPresent());
        _cabsScenarioModelHelper.assertCabsEqual(
            testCab,
            foundCab.get()
        );
    }
}
