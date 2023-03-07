package com.br.microservice.financeiro.queue.out;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraMessageSender {

    private final RabbitTemplate rabbitTemplate;

    private final Queue ordemCompraPagoQueue;

    private final ObjectMapper objectMapper;

    public void send(String identificador) {
        String message = null;
        try {
            message = objectMapper.writeValueAsString(identificador);
            log.info("Mensagem enviada para o Rabbit {}", identificador);
            rabbitTemplate.convertSendAndReceive(ordemCompraPagoQueue.getName(),message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
