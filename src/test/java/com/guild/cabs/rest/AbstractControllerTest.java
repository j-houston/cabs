package com.guild.cabs.rest;

import com.guild.cabs.AbstractCabsDataTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class AbstractControllerTest extends AbstractCabsDataTest {

    @Autowired
    protected MockMvc _mvc;
}
