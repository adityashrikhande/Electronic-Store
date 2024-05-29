package com.aditya_shrikhande.electronic_store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * Configuration class for the project.
 */
@Configuration
public class ProjectConfig {
    private static final Logger logger = Logger.getLogger(ProjectConfig.class.getName());

    /**
     * Creates and configures a ModelMapper bean.
     *
     * @return The configured ModelMapper bean.
     */
    @Bean
    public ModelMapper mapper() {
        logger.info("Creating and configuring ModelMapper bean.");
        return new ModelMapper();
    }
}
