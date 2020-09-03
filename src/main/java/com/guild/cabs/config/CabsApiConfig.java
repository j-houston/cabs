package com.guild.cabs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableSwagger2
// should be implicit in spring boot, unless autoconfigure is disabled. Let's add it to
// be explicit in cases autoconfigure is indeed disabled.
@EnableTransactionManagement
public class CabsApiConfig implements WebMvcConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(CabsApiConfig.class);

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).directModelSubstitute(
            LocalDate.class,
            String.class
        )
            .directModelSubstitute(
                LocalDateTime.class,
                String.class
            )
            .directModelSubstitute(
                Instant.class,
                String.class
            )
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    @Profile({
        "development", "integration", "test"
    })
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("sql/schema-h2-spatial.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}
