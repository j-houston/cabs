package com.guild.cabs.handlers;

import com.guild.cabs.AbstractCabsDataTest;
import com.guild.cabs.exceptions.InvalidCabSearchException;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.CabSearchInputRep;
import com.guild.cabs.view.CabsPageRep;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCabHandlerSearch extends AbstractCabsDataTest {

    private final ICabHandler _cabHandler;

    @Autowired
    public TestCabHandlerSearch(final ICabHandler cabHandler) {
        _cabHandler = cabHandler;
    }

    @Test
    public void testSearchEmpty() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        final CabsPageRep results = _cabHandler.searchCabs(searchInput);
        Assert.assertNotNull(results);
        Assert.assertEquals(
            0,
            results.getCabs()
                .size()
        );
    }

    @Test
    public void testSearchHighLat() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLatitude(91.0);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchLowLat() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLatitude(-91.0);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchHighLon() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLongitude(181.0);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchLowLon() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLongitude(-181.0);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchLowRadius() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setRadius(-1.0);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchLowLimit() throws Exception {
        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLimit(-1);

        try {
            _cabHandler.searchCabs(searchInput);
            Assert.fail("Expected an exception");
        } catch(InvalidCabSearchException e) {
            // expected
        }
    }

    @Test
    public void testSearchCabs() throws Exception {
        final List<CabRep> allCabs = new ArrayList<CabRep>(10);
        for (int i = 0; i < 10; i++) {
            allCabs.add(_cabsScenarioModelHelper.createTestableCab(i * 200));
        }

        final CabSearchInputRep searchInput = _cabsScenarioModelHelper.getSearchInput();
        searchInput.setLimit(2);
        searchInput.setRadius(10 * 200 + 50);
        CabsPageRep found = _cabHandler.searchCabs(searchInput);
        Assert.assertNotNull(found);
        Assert.assertEquals(
            2,
            found.getCabs()
                .size()
        );

        searchInput.setLimit(20);
        for (int i = 9; i >= 0; i--) {
            searchInput.setRadius(i * 200 + 50);

            found = _cabHandler.searchCabs(searchInput);
            assertSearchMatches(
                allCabs,
                found
            );

            allCabs.remove(i);
        }

    }

    private void assertSearchMatches(
        final List<CabRep> expected,
        final CabsPageRep found
    ) {
        final List<CabRep> foundCabs = found.getCabs();
        Assert.assertEquals(
            expected.size(),
            foundCabs.size()
        );

        final Set<String> expectedIds = new HashSet<>();
        final Set<String> foundIds = new HashSet<>();
        for (int i = 0; i < foundCabs.size(); i++) {
            expectedIds.add(
                expected.get(i)
                    .getId()
            );
            foundIds.add(
                foundCabs.get(i)
                    .getId()
            );
        }

        Assert.assertEquals(
            expectedIds,
            foundIds
        );
    }
}
