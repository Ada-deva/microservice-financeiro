package com.br.microservice.financeiro.service;

import com.br.microservice.financeiro.dto.ComandaDTO;
import com.br.microservice.financeiro.exception.BadRequestException;
import com.br.microservice.financeiro.exception.InformacaoNaoEncontradaException;
import com.br.microservice.financeiro.model.Cliente;
import com.br.microservice.financeiro.model.Comanda;
import com.br.microservice.financeiro.repository.ClienteRepository;
import com.br.microservice.financeiro.repository.ComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Comanda> cadastrar(ComandaDTO comanda) throws InformacaoNaoEncontradaException {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(comanda.getCliente());
        Comanda novaComanda = comanda.toEntity();

        if (clienteEncontrado.isEmpty()) {
            throw new InformacaoNaoEncontradaException("Cliente não encontrado!");
        } else {
            clienteEncontrado.ifPresent(novaComanda::setCliente);
            novaComanda.setData(LocalDateTime.now());
            comandaRepository.save(novaComanda);
        }
        return Optional.of(novaComanda);
    }

    public List<Comanda> comandaList() {
        return (List<Comanda>) comandaRepository.findAll();
    }

    public Optional<Comanda> encontrarPorId(Long id) {
        return comandaRepository.findById(id);
    }

    public Optional<Comanda> atualizarComanda(ComandaDTO comanda, Long id) throws InformacaoNaoEncontradaException {
        Optional<Comanda> comandaEncontrada = comandaRepository.findById(id);
        if (comandaEncontrada.isPresent()) {
            if (comanda.getConta() != 0) {
                comandaEncontrada.get().setConta(comanda.getConta());
            }

            if (comanda.getTipoPagamento() != null) {
                comandaEncontrada.get().setTipoPagamento(comanda.getTipoPagamento());
            }

            if (comanda.getCliente() != 0) {
                Optional<Cliente> clienteEncontrado = clienteRepository.findById(comanda.getCliente());
                if (clienteEncontrado.isEmpty()) {
                    throw new InformacaoNaoEncontradaException("Cliente não encontrado!");
                } else {
                    comandaEncontrada.get().setCliente(clienteEncontrado.get());
                }
            }
            comandaRepository.save(comandaEncontrada.get());
        }

        return comandaEncontrada;
    }

    public Optional<Comanda> deletarComanda(Long id) {
        Optional<Comanda> comandaEncontrada = comandaRepository.findById(id);

        comandaEncontrada.ifPresent(comanda -> comandaRepository.delete(comanda));

        return comandaEncontrada;
    }

    public Optional<Comanda> pagarComanda(Long id) throws BadRequestException {
        Optional<Comanda> comandaEncontrada = comandaRepository.findById(id);
        if(comandaEncontrada.isPresent() && !comandaEncontrada.get().isPago()) {
            comandaEncontrada.get().setPago(true);
            return comandaEncontrada;
        } else {
             throw new BadRequestException("Comanda já está paga!");
            }
    }

}