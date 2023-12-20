package com.joaolucas.study.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithCardEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class JacksonConfiguration {
    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare

    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentWithCardEntity.class);
                objectMapper.registerSubtypes(PaymentWithSlipEntity.class);
                objectMapper.setDateFormat(dateFormat);
                super.configure(objectMapper);
            }
        };
    }
}
