package com.br.microservice.financeiro.queue.in;

import com.br.microservice.financeiro.dto.OrdemCompraDTO;
import com.br.microservice.financeiro.dto.OrdemCompraReqDTO;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;
import com.br.microservice.financeiro.service.OrdemCompraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdemCompraMessageConsumer {

    private final ObjectMapper objectMapper;
    private final OrdemCompraService ordemCompraService;
    @RabbitListener(queues = {"${business.financeiro.message.queue.ordem_compra}"})

    public void receiveMessage(String message) throws JsonProcessingException, InformacaoNaoEncontradaException {
        OrdemCompraReqDTO ordemCompraDTO = objectMapper.readValue(message, OrdemCompraReqDTO.class);
        ordemCompraService.cadastrarPorIdentificador(ordemCompraDTO);
    }
}
