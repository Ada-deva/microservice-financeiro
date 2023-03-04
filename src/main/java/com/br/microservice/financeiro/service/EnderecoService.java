package com.br.microservice.financeiro.service;

import com.br.microservice.financeiro.client.ViaCEPApiCLient;
import com.br.microservice.financeiro.dto.EnderecoDTO;
import com.br.microservice.financeiro.exception.InformacaoInvalidaException;
import com.br.microservice.financeiro.model.Endereco;
import com.br.microservice.financeiro.model.ViaCEPApi;
import com.br.microservice.financeiro.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCEPApiCLient viaCEPApiCLient;
    public Optional<Endereco> cadastrar(Endereco endereco) throws InformacaoInvalidaException {
        ViaCEPApi enderecoEncontrado =  viaCEPApiCLient.getViaCEPApi(endereco.getCep());
        if(enderecoEncontrado == null) {
            throw new InformacaoInvalidaException("CEP não encontrado!");
        }
        if(endereco.getLogradouro() == null
                || endereco.getLogradouro().isBlank()
                || endereco.getLogradouro().isEmpty()) {
            endereco.setLogradouro(enderecoEncontrado.getLogradouro());
        }

        if(endereco.getBairro() == null
                || endereco.getBairro().isBlank()
                || endereco.getBairro().isEmpty()) {
            endereco.setBairro(enderecoEncontrado.getBairro());
        }

        if(endereco.getCidade() == null
                || endereco.getCidade().isBlank()
                || endereco.getCidade().isEmpty()) {
            endereco.setCidade(enderecoEncontrado.getLocalidade());
        }

        if(endereco.getEstado() == null
                || endereco.getEstado().isBlank()
                || endereco.getEstado().isEmpty()) {
            endereco.setEstado(enderecoEncontrado.getUf());
            }

        enderecoRepository.save(endereco);
        return Optional.of(endereco);
    }

    public List<Endereco> enderecoList() {
        return (List<Endereco>) enderecoRepository.findAll();
    }

    public Optional<Endereco> encontrarPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public Optional<Endereco> atualizarEndereco(EnderecoDTO endereco, Long id)  {
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(id);

        if (enderecoEncontrado.isPresent()) {
            if (endereco.getLogradouro() != null) {
                enderecoEncontrado.get().setLogradouro(endereco.getLogradouro());
            }

            if (endereco.getNumero() != null) {
                enderecoEncontrado.get().setNumero(endereco.getNumero());
            }

            if (endereco.getBairro() != null) {
                enderecoEncontrado.get().setBairro(endereco.getBairro());
            }

            if (endereco.getCidade() != null) {
                enderecoEncontrado.get().setCidade(endereco.getCidade());
            }

            if (endereco.getEstado() != null) {
                enderecoEncontrado.get().setEstado(endereco.getEstado());
            }

            if(endereco.getCep()!= null) {
                enderecoEncontrado.get().setCep(endereco.getCep());

            }
            enderecoRepository.save(enderecoEncontrado.get());
           }

        return enderecoEncontrado;
    }

    public Optional<Endereco> deletarEndereco(Long id) {
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(id);

        enderecoEncontrado.ifPresent(endereco -> enderecoRepository.delete(endereco));

        return enderecoEncontrado;
    }
}
