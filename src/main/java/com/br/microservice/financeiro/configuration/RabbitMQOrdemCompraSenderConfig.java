package com.br.microservice.financeiro.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQOrdemCompraSenderConfig {

    @Value("${business.financeiro.message.queue.ordem_compra_pago}")
    private String queueName;


    @Bean
    public Queue ordemCompraPagoQueue() {
        return new Queue(queueName, true);
    }


}
