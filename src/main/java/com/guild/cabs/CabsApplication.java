package com.guild.cabs;

import com.guild.cabs.config.CabsApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class CabsApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CabsApiConfig.class);

    /**
     * Helps to verify that the right Spring active profile is set
     */
    private static void logEnvironmentVariables() {
        LOG.info("**** STARTUP ENVIRONMENT VARIABLES ****");

        final Map<String, String> envVars = System.getenv();
        for (Map.Entry<String, String> nameValue : envVars.entrySet()) {
            LOG.info(
                String.format(
                    "      %s=%s",
                    nameValue.getKey(),
                    nameValue.getValue()
                )
            );
        }
    }

    public static void main(final String... args) {
        logEnvironmentVariables();
        SpringApplication.run(
            CabsApplication.class,
            args
        );
        System.out.println("STARTED");
    }
}
