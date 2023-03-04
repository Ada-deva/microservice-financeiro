package com.br.microservice.financeiro.controller;

import com.br.microservice.financeiro.dto.ComandaDTO;
import com.br.microservice.financeiro.exception.BadRequestException;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;
import com.br.microservice.financeiro.model.Comanda;
import com.br.microservice.financeiro.response.message.ResponseMessage;
import com.br.microservice.financeiro.service.ComandaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/comanda")
@RequiredArgsConstructor
@RestControllerAdvice
public class ComandaController {

    private final ComandaService comandaService;

    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(Comanda.class.getSimpleName());
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Comanda> cadastrarComanda(@RequestBody ComandaDTO comanda) throws InformacaoNaoEncontradaException {

        Optional<Comanda> novaComanda = comandaService.cadastrar(comanda);
        if(novaComanda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        } else {
            Comanda response = novaComanda.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }


    @GetMapping
    public ResponseEntity<List<Comanda>> obterListaComandas() {
        return new ResponseEntity<>(comandaService.comandaList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comanda> encontrarPorId(@PathVariable Long id) {
        Optional<Comanda> comanda = comandaService.encontrarPorId(id);

        if(comanda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(comanda.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comanda> atualizarComanda(@RequestBody ComandaDTO comanda, @PathVariable Long id) throws InformacaoNaoEncontradaException {

        Optional<Comanda> comandaAtualizada = comandaService.atualizarComanda(comanda, id);

        if(comandaAtualizada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(comandaAtualizada.get(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comanda> deletarComanda(@PathVariable Long id) {

        Optional<Comanda> comandaDeletada = comandaService.deletarComanda(id);

        if(comandaDeletada.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(comandaDeletada.get(), HttpStatus.OK);
        }

    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<Comanda> pagarComanda(@PathVariable Long id) throws BadRequestException {
        Optional<Comanda> comandaPaga = comandaService.pagarComanda(id);

        if(comandaPaga.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(comandaPaga.get(), HttpStatus.OK);
        }

    }
}
