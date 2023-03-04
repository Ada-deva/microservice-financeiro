package com.br.microservice.financeiro.service;

import com.br.microservice.financeiro.dto.FornecedorDTO;
import com.br.microservice.financeiro.model.Comanda;
import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Optional<Fornecedor> cadastrar(FornecedorDTO fornecedor) {
        Fornecedor novoFornecedor = fornecedor.toEntity();
        fornecedorRepository.save(novoFornecedor);

        return Optional.of(novoFornecedor);
    }

    public List<Fornecedor> fornecedorList() {
        return (List<Fornecedor>) fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> encontrarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public Optional<Fornecedor> atualizarFornecedor(FornecedorDTO fornecedor, Long id) {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(id);

        if(fornecedorEncontrado.isPresent()) {
            if(fornecedor.getNome() != null){
                fornecedorEncontrado.get().setNome(fornecedor.getNome());
            }

            if(fornecedor.getCpfOuCnpj() != null && fornecedor.getTipoFornecedor() != null) {
                fornecedorEncontrado.get().setCpfOuCnpj(fornecedor.getCpfOuCnpj());
                fornecedorEncontrado.get().setTipoFornecedor(fornecedor.getTipoFornecedor());
            }

            if(fornecedor.getSeguimento() != null) {
                fornecedorEncontrado.get().setSeguimento(fornecedor.getSeguimento());
            }

            fornecedorRepository.save(fornecedorEncontrado.get());
        }
        return fornecedorEncontrado;
    }

    public Optional<Fornecedor> deletarFornecedor(Long id) {
        Optional<Fornecedor> fornecedorEncontrado = fornecedorRepository.findById(id);
        fornecedorEncontrado.ifPresent(fornecedor -> fornecedorRepository.delete(fornecedor));

        return fornecedorEncontrado;
    }


}
