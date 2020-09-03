package com.guild.cabs.rest;

import com.guild.cabs.exceptions.CabsErrorCode;
import com.guild.cabs.persistence.ICabRepository;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestCabsControllerCRUD extends AbstractControllerTest {

    private final ICabRepository _cabRepository;

    @Autowired
    public TestCabsControllerCRUD(final ICabRepository cabRepository) {
        _cabRepository = cabRepository;
    }

    @Test
    public void testGetCab() throws Exception {
        final CabRep testCab = _cabsScenarioModelHelper.createTestableCab();

        _mvc.perform(MockMvcRequestBuilders.get("/cabs/" + testCab.getId()))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                .value(testCab.getId())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.latitude")
                .value(testCab.getLatitude())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.longitude")
                .value(testCab.getLongitude())
            );
    }

    @Test
    public void testCreateCab() throws Exception {
        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);

        _mvc.perform(
            MockMvcRequestBuilders.put("/cabs")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(testPoint.toJson())
        )
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                .isNotEmpty()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.latitude")
                .value(testPoint.getLatitude())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.longitude")
                .value(testPoint.getLongitude())
            );

        Assert.assertEquals(
            1,
            _cabRepository.count()
        );
    }

    @Test
    public void testCreateCabHighLat() throws Exception {
        final LatLongRep testPoint = _cabsScenarioModelHelper.getPointNearClient(1000);
        testPoint.setLatitude(91.0);

        _mvc.perform(
            MockMvcRequestBuilders.put("/cabs")
                .content(testPoint.toJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(
                MockMvcResultMatchers.status()
                    .isBadRequest()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                .value(CabsErrorCode.CAB_VALIDATION_EXCEPTION.name())
            );
    }

    @Test
    public void testDestroyCab() throws Exception {
        final CabRep testCab = _cabsScenarioModelHelper.createTestableCab();

        Assert.assertEquals(
            1,
            _cabRepository.count()
        );

        _mvc.perform(MockMvcRequestBuilders.delete("/cabs/" + testCab.getId()))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            );

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        // Idempotency
        _mvc.perform(MockMvcRequestBuilders.delete("/cabs/" + testCab.getId()))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            );
    }

    @Test
    public void testDestroyAllCabs() throws Exception {
        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        for (int i = 0; i < 10; i++) {
            _cabsScenarioModelHelper.createTestableCab();
            Assert.assertEquals(
                i + 1,
                _cabRepository.count()
            );
        }

        _mvc.perform(MockMvcRequestBuilders.delete("/cabs"))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            );

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );

        _mvc.perform(MockMvcRequestBuilders.delete("/cabs"))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            );

        Assert.assertEquals(
            0,
            _cabRepository.count()
        );
    }

    @Test
    public void testUpdateCabNotFound() throws Exception {
        _mvc.perform(MockMvcRequestBuilders.get("/cabs/not-found"))
            .andExpect(
                MockMvcResultMatchers.status()
                    .isNotFound()
            );
    }

    @Test
    public void testUpdateCabLowLat() throws Exception {
        final CabRep testCab = _cabsScenarioModelHelper.createTestableCab();
        testCab.setLatitude(-91.0);

        _mvc.perform(
            MockMvcRequestBuilders.put("/cabs/" + testCab.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(testCab.toJson())
        )
            .andExpect(
                MockMvcResultMatchers.status()
                    .isBadRequest()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                .value(CabsErrorCode.CAB_VALIDATION_EXCEPTION.name())
            );
    }

    @Test
    public void testUpdateCab() throws Exception {
        final CabRep testCab = _cabsScenarioModelHelper.createTestableCab();
        testCab.setLatitude(-11.0);
        testCab.setLongitude(-12.0);

        _mvc.perform(
            MockMvcRequestBuilders.put("/cabs/" + testCab.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(testCab.toJson())
        )
            .andExpect(
                MockMvcResultMatchers.status()
                    .isOk()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                .value(testCab.getId())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.latitude")
                .value(testCab.getLatitude())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.longitude")
                .value(testCab.getLongitude())
            );
    }
}
