package com.br.microservice.financeiro.producer;

import com.br.microservice.financeiro.queue.out.OrdemCompraMessageSender;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrdemCompraProducer {
    @Autowired
    private OrdemCompraMessageSender ordemCompraMessageSender;
    public void retornarOrdemPaga(String identificador) {;
        ordemCompraMessageSender.send(identificador);
    }

}
