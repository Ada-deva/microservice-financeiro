package com.br.microservice.financeiro.service;

import com.br.microservice.financeiro.model.OrdemCompra;
import com.br.microservice.financeiro.producer.OrdemCompraProducer;
import com.br.microservice.financeiro.repository.FornecedorRepository;
import com.br.microservice.financeiro.repository.OrdemCompraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(propagation= Propagation.REQUIRED, noRollbackFor=Exception.class)
public class PagamentoService {

    @Autowired
    private OrdemCompraRepository ordemCompraRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    OrdemCompraProducer ordemCompraProducer;

    public OrdemCompra pagarOrdemCompraIdentificador(String ordemCompra) {

        LocalDate today = LocalDate.now();
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findByIdentificador(ordemCompra);

        List<OrdemCompra> ordemVencida =  ordemCompraRepository.findByDataVencimentoLessThanAndIsPagoFalse(today);

        if(ordemEncontrada.isPresent()) {
            if(ordemVencida.contains(ordemEncontrada.get())){
                double juros = ordemEncontrada.get().getValorTotal() + ordemEncontrada.get().getValorTotal() * 0.05;
                ordemEncontrada.get().setValorTotal(juros);
                ordemEncontrada.get().setDataPagamento(LocalDateTime.now());
                ordemEncontrada.get().setPago(true);
            } else {
                ordemEncontrada.get().setDataPagamento(LocalDateTime.now());
                ordemEncontrada.get().setPago(true);
            }
        }


        ordemCompraProducer.retornarOrdemPaga(ordemCompra);
        return ordemCompraRepository.save(ordemEncontrada.get());



    }
}
