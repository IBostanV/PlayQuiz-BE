package com.play.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer applicationProperties() {
        final PropertySourcesPlaceholderConfigurer propertyPlaceholder = new PropertySourcesPlaceholderConfigurer();
        propertyPlaceholder.setIgnoreUnresolvablePlaceholders(true);
        propertyPlaceholder.setIgnoreResourceNotFound(true);
        propertyPlaceholder.setLocations(retrieveResources());
        return propertyPlaceholder;
    }

    private Resource[] retrieveResources() {
        final String env = System.getProperties().getProperty("env");
        return new Resource[]{
                new ClassPathResource("environments/" + env + "/credentials.properties")};
    }
}
