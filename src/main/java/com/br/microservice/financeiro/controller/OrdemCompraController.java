package com.br.microservice.financeiro.controller;


import com.br.microservice.financeiro.dto.FornecedorDTO;
import com.br.microservice.financeiro.dto.OrdemCompraDTO;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;
import com.br.microservice.financeiro.model.Comanda;

import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.model.OrdemCompra;
import com.br.microservice.financeiro.response.message.ResponseMessage;

import com.br.microservice.financeiro.service.OrdemCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordemcompra")
@RequiredArgsConstructor
@RestControllerAdvice
public class OrdemCompraController {
    private final OrdemCompraService ordemCompraService;

    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(OrdemCompra.class.getSimpleName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<OrdemCompra> cadastrarOrdemCompra(@RequestBody OrdemCompraDTO ordemCompra) throws InformacaoNaoEncontradaException {

        Optional<OrdemCompra> novaOrdemCompra = ordemCompraService.cadastrar(ordemCompra);
        if(novaOrdemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        } else {
            OrdemCompra response = novaOrdemCompra.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdemCompra>> obterListaOrdemCompra() {
        return new ResponseEntity<>(ordemCompraService.ordemCompraList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemCompra> encontrarPorId(@PathVariable Long id) {
        Optional<OrdemCompra> ordemCompra = ordemCompraService.encontrarPorId(id);

        if(ordemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(ordemCompra.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrdemCompra> atualizarOrdemCompra(@RequestBody OrdemCompraDTO ordemCompra,
                                                            @PathVariable Long id) throws InformacaoNaoEncontradaException {

        Optional<OrdemCompra> ordemAtualizada = ordemCompraService.atualizarOrdemCompra(ordemCompra, id);

        if(ordemAtualizada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(ordemAtualizada.get(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrdemCompra> deletarOrdemCompra(@PathVariable Long id) {

        Optional<OrdemCompra> ordemCompraDeletada = ordemCompraService.deletarOrdemCompra(id);

        if(ordemCompraDeletada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(ordemCompraDeletada.get(), HttpStatus.OK);
        }

    }

    @GetMapping("/vencido")
    public ResponseEntity<List<OrdemCompra>> obterOrdemCompraVencida() {
        return new ResponseEntity<>(ordemCompraService.obterOrdemCompraVencida(), HttpStatus.OK);
    }

    @GetMapping("/regular")
    public ResponseEntity<List<OrdemCompra>> obterOrdemCompraNoPrazo() {
        return new ResponseEntity<>(ordemCompraService.obterOrdemCompraNoPrazo(), HttpStatus.OK);
    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<OrdemCompra> pagarOrdemCompra(@PathVariable Long id) {
        Optional<OrdemCompra> ordemCompra = ordemCompraService.pagarOrdemCompra(id);

        if(ordemCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(ordemCompra.get(), HttpStatus.OK);
        }
    }
    @GetMapping("/pago")
    public ResponseEntity<List<OrdemCompra>> obterOrdemCompraPaga() {
        return new ResponseEntity<>(ordemCompraService.obterOrdemCompraPaga(), HttpStatus.OK);
    }

    @GetMapping("/nao-pago")
    public ResponseEntity<List<OrdemCompra>> obterOrdemCompraNaoPaga() {
        return new ResponseEntity<>(ordemCompraService.obterOrdemCompraNaoPaga(), HttpStatus.OK);
    }


}
