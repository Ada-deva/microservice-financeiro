package com.br.microservice.financeiro.controller;

import com.br.microservice.financeiro.dto.ClienteDTO;
import com.br.microservice.financeiro.exception.InformacaoInvalidaException;

import com.br.microservice.financeiro.model.Cliente;
import com.br.microservice.financeiro.response.message.ResponseMessage;
import com.br.microservice.financeiro.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class ClienteController {

    private final ClienteService clienteService;
    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(Cliente.class.getSimpleName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InformacaoInvalidaException.class)
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody ClienteDTO cliente) throws InformacaoInvalidaException
            , IOException {
        Optional<Cliente> novoCliente = clienteService.cadastrar(cliente.toEntity());
        if(novoCliente.isPresent()) {

            Cliente response = novoCliente.get();

            return new ResponseEntity<>(cliente.of(response), HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        }

    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterListaClientes() {
        return new ResponseEntity<>(clienteService.clienteList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> encontrarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.encontrarPorId(id);

        if(cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
        public ResponseEntity<ClienteDTO> atualizarCliente(@RequestBody ClienteDTO cliente, @PathVariable Long id) throws InformacaoInvalidaException {

        Optional<Cliente> clienteAtualizado = clienteService.atualizarCliente(cliente, id);

        if(clienteAtualizado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(cliente.of(clienteAtualizado.get()), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deletarCliente(@PathVariable Long id) {

        Optional<Cliente> clienteDeletado = clienteService.deletarCliente(id);

        if(clienteDeletado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(clienteDeletado.get(), HttpStatus.OK);
        }

    }


}
