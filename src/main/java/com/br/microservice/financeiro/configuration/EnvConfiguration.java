package com.br.microservice.financeiro.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="client.viacepapi")
@PropertySource("classpath:application.yml")
@Data
public class EnvConfiguration {

    private String uri;

}
