package com.br.microservice.financeiro.repository;

import com.br.microservice.financeiro.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
}

