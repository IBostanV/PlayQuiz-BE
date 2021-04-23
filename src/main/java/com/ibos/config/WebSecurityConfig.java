package com.ibos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebSecurityConfig {
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/ws")
                .allowedOrigins("http://localhost:3000");
    }
}
