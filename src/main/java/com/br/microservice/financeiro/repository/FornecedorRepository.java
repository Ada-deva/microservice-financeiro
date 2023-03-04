package com.br.microservice.financeiro.repository;

import com.br.microservice.financeiro.model.Fornecedor;
import org.springframework.data.repository.CrudRepository;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {
}
