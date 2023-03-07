package com.br.microservice.financeiro.service;


import com.br.microservice.financeiro.dto.OrdemCompraDTO;
import com.br.microservice.financeiro.dto.OrdemCompraReqDTO;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;

import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.model.Insumo;
import com.br.microservice.financeiro.model.OrdemCompra;
import com.br.microservice.financeiro.producer.OrdemCompraProducer;
import com.br.microservice.financeiro.repository.FornecedorRepository;
import com.br.microservice.financeiro.repository.InsumoRepository;
import com.br.microservice.financeiro.repository.OrdemCompraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrdemCompraService {

    @Autowired
    private OrdemCompraRepository ordemCompraRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private OrdemCompraProducer ordemCompraProducer;

    public Optional<OrdemCompra> cadastrar(OrdemCompraDTO ordemCompra) throws InformacaoNaoEncontradaException {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(ordemCompra.getFornecedor());
        OrdemCompra novaOrdem = ordemCompra.toEntity();
        novaOrdem.setIdentificador(UUID.randomUUID().toString());
        if (fornecedorEncontrado.isEmpty()) {
            throw new InformacaoNaoEncontradaException("Fornecedor não encontrado!");
        } else {

            fornecedorEncontrado.ifPresent(novaOrdem::setFornecedor);
            novaOrdem.setDataCriacao(LocalDateTime.now());
            List<Insumo> insumoList = ordemCompra.getListaInsumos();
            int quantidadeInsumos =
                    insumoList.stream().reduce(0,
                            (subtotal, insumo) -> subtotal + insumo.getQuantidade(), Integer::sum);


            novaOrdem.setQuantidadeTotal(quantidadeInsumos);

            double valorTotal = 0;

            for (Insumo insumo : insumoList) {
                double valorTotalInsumo = insumo.getValor() * insumo.getQuantidade();
                valorTotal = valorTotal + valorTotalInsumo;
                insumoRepository.save(insumo);
            }
            novaOrdem.setValorTotal(valorTotal);
            ordemCompraRepository.save(novaOrdem);
        }
        return Optional.of(novaOrdem);
    }

    public Optional<OrdemCompra> cadastrarPorIdentificador(OrdemCompraReqDTO ordemCompra) throws InformacaoNaoEncontradaException {
        Optional<Fornecedor> fornecedorEncontrado =
                fornecedorRepository.findByIdentificador(ordemCompra.getFornecedor().getIdentificador());


        OrdemCompra novaOrdem = ordemCompra.toEntity();

        if (fornecedorEncontrado.isEmpty()) {
            throw new InformacaoNaoEncontradaException("Fornecedor não encontrado!");
        } else {


            List<Insumo> insumoList = ordemCompra.getListaInsumos();
            for (Insumo insumo : insumoList) {
                insumoRepository.save(insumo);
            }
            novaOrdem.setDataCriacao(LocalDateTime.now());
            novaOrdem.setFornecedor(fornecedorEncontrado.get());

            ordemCompraRepository.save(novaOrdem);
      }
        return Optional.of(novaOrdem);

    }

    public List<OrdemCompra> ordemCompraList() {
        return (List<OrdemCompra>) ordemCompraRepository.findAll();
    }

    public Optional<OrdemCompra> encontrarPorId(Long id) {
        return ordemCompraRepository.findById(id);
    }

    public Optional<OrdemCompra> atualizarOrdemCompra(OrdemCompraDTO ordemCompra, Long id) throws InformacaoNaoEncontradaException {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findById(id);

        if(ordemEncontrada.isPresent()) {

            if(ordemCompra.getDataVencimento() != null) {
                ordemEncontrada.get().setDataVencimento(ordemCompra.getDataVencimento());
            }

            if(ordemCompra.getFornecedor() != 0) {
                Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(ordemCompra.getFornecedor());
                if (fornecedorEncontrado.isEmpty()) {
                    throw new InformacaoNaoEncontradaException("Fornecedor não encontrado!");
                } else {
                    ordemEncontrada.get().setFornecedor(fornecedorEncontrado.get());
                }
            }

            ordemCompraRepository.save(ordemEncontrada.get());
        }

        return ordemEncontrada;
    }

    public Optional<OrdemCompra> deletarOrdemCompra(Long id) {
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findById(id);
        ordemEncontrada.ifPresent(ordemCompra -> ordemCompraRepository.delete(ordemCompra));

        return ordemEncontrada;
    }

    public List<OrdemCompra> obterOrdemCompraVencida() {
        LocalDate today = LocalDate.now();

        return ordemCompraRepository.findByDataVencimentoLessThanAndIsPagoFalse(today);
    }

    public List<OrdemCompra> obterOrdemCompraNoPrazo() {
        LocalDate today = LocalDate.now();
        return ordemCompraRepository.findByDataVencimentoGreaterThanEqual(today);
    }

    public Optional<OrdemCompra> pagarOrdemCompra(long id) {
        LocalDate today = LocalDate.now();
        Optional<OrdemCompra> ordemEncontrada = ordemCompraRepository.findById(id);
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
            ordemCompraRepository.save(ordemEncontrada.get());
        }

        return ordemEncontrada;
    }



    public List<OrdemCompra> obterOrdemCompraPaga() {
        return ordemCompraRepository.findByIsPagoTrue();
    }

    public List<OrdemCompra> obterOrdemCompraNaoPaga() {
        return ordemCompraRepository.findByIsPagoFalse();
    }
}
