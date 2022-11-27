package com.play.quiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:jdbc-persistence.xml")
public class PersistenceConfig {
}
