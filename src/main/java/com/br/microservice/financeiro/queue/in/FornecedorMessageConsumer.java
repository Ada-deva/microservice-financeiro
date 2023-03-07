package com.br.microservice.financeiro.queue.in;

import com.br.microservice.financeiro.dto.FornecedorDTO;
import com.br.microservice.financeiro.service.FornecedorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FornecedorMessageConsumer {
    private final ObjectMapper objectMapper;
    private final FornecedorService fornecedorService;
    @RabbitListener(queues = {"${business.financeiro.message.queue.fornecedor}"})

    public void receiveMessage(String message) throws JsonProcessingException {
        FornecedorDTO fornecedorDTO = objectMapper.readValue(message, FornecedorDTO.class);
        fornecedorService.cadastrar(fornecedorDTO);
    }
}
