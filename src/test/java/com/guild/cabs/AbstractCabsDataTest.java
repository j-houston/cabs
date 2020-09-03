package com.guild.cabs;

import com.guild.cabs.config.CabsApiTestConfig;
import com.guild.cabs.test.ICabsScenarioModelHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Any class that generates test data should be cleaned up after the test
 */
@SpringJUnitConfig(classes = CabsApiTestConfig.class)
@SpringBootTest(classes = com.guild.cabs.CabsApplication.class)
public abstract class AbstractCabsDataTest {

    @Autowired
    protected ICabsScenarioModelHelper _cabsScenarioModelHelper;

    @AfterEach
    public void afterEachTestMethod() throws Exception {
        _cabsScenarioModelHelper.clean();
    }

    @BeforeEach
    public void beforeEachTestMethod() throws Exception {
        _cabsScenarioModelHelper.setup();
    }
}
