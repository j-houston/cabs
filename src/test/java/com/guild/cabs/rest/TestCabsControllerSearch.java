package com.guild.cabs.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.guild.cabs.view.CabRep;
import com.guild.cabs.view.LatLongRep;
import com.guild.cabs.view.json.CabsObjectMapperFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCabsControllerSearch extends AbstractControllerTest {

    @Test
    public void testSearchCabs() throws Exception {
        final List<CabRep> allCabs = new ArrayList<CabRep>(10);
        for (int i = 0; i < 10; i++) {
            allCabs.add(_cabsScenarioModelHelper.createTestableCab(i * 200));
        }

        final LatLongRep testPoint = _cabsScenarioModelHelper.getTestClientPoint();
        for (int i = 9; i >= 0; i--) {
            final MvcResult result = _mvc.perform(
                MockMvcRequestBuilders.get("/cabs")
                    .param(
                        "latitude",
                        String.valueOf(testPoint.getLatitude())
                    )
                    .param(
                        "longitude",
                        String.valueOf(testPoint.getLongitude())
                    )
                    .param(
                        "limit",
                        String.valueOf(20)
                    )
                    .param(
                        "radius",
                        String.valueOf(i * 200 + 50)
                    )
            )

                .andExpect(
                    MockMvcResultMatchers.status()
                        .isOk()
                )
                .andReturn();

            assertSearchMatches(
                allCabs,
                result
            );

            allCabs.remove(i);
        }

    }

    private void assertSearchMatches(
        final List<CabRep> expected,
        final MvcResult result
    ) throws Exception {
        final ObjectMapper mapper = CabsObjectMapperFactory.getObjectMapper();
        final JsonNode rootNode = mapper.readTree(
            result.getResponse()
                .getContentAsString()
        );
        final ArrayNode itemsArray = (ArrayNode)rootNode.get("cabs");

        Assert.assertEquals(
            expected.size(),
            itemsArray.size()
        );

        final Set<String> expectedIds = new HashSet<>();
        final Set<String> foundIds = new HashSet<>();
        for (int i = 0; i < itemsArray.size(); i++) {
            expectedIds.add(
                expected.get(i)
                    .getId()
            );
            foundIds.add(
                itemsArray.get(i)
                    .get("id")
                    .textValue()
            );
        }

        Assert.assertEquals(
            expectedIds,
            foundIds
        );
    }
}
