package com.joaolucas.study.core.configuration;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    @Bean
    public Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger("core.config.LoggerConfiguration");
    }
}
