package com.br.microservice.financeiro.queue.in;


import com.br.microservice.financeiro.dto.OrdemCompraDTO;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;
import com.br.microservice.financeiro.model.OrdemCompra;
import com.br.microservice.financeiro.service.OrdemCompraService;
import com.br.microservice.financeiro.service.PagamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraPagoMessageConsumer {
    private final ObjectMapper objectMapper;
    private final PagamentoService pagamentoService;

    @RabbitListener(queues = {"${business.financeiro.message.queue.pagamento}"})
    public void receiveMessage(String message) throws JsonProcessingException {
        log.info(message);
        String ordemCompra = objectMapper.readValue(message, String.class);
        pagamentoService.pagarOrdemCompraIdentificador(ordemCompra);
    }
}
