package com.br.microservice.financeiro.controller;


import com.br.microservice.financeiro.dto.FornecedorDTO;
import com.br.microservice.financeiro.model.Comanda;
import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.response.message.ResponseMessage;
import com.br.microservice.financeiro.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedor")
@RequiredArgsConstructor
@RestControllerAdvice
public class FornecedorController {

    private final FornecedorService fornecedorService;

    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(Fornecedor.class.getSimpleName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Fornecedor> cadastrarFornecedor(@RequestBody FornecedorDTO fornecedor)  {

        Optional<Fornecedor> novoFornecedor = fornecedorService.cadastrar(fornecedor);
        if(novoFornecedor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        } else {
            Fornecedor response = novoFornecedor.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> obterListaFornecedores() {
        return new ResponseEntity<>(fornecedorService.fornecedorList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> encontrarPorId(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorService.encontrarPorId(id);

        if(fornecedor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@RequestBody FornecedorDTO fornecedor, @PathVariable Long id) {

        Optional<Fornecedor> fornecedorAtualizado = fornecedorService.atualizarFornecedor(fornecedor, id);

        if(fornecedorAtualizado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(fornecedorAtualizado.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Fornecedor> deletarFornecedor(@PathVariable Long id) {

        Optional<Fornecedor> fornecedorDeletado = fornecedorService.deletarFornecedor(id);

        if(fornecedorDeletado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(fornecedorDeletado.get(), HttpStatus.OK);
        }

    }
}
