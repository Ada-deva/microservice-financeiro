package com.br.microservice.financeiro.repository;

import com.br.microservice.financeiro.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
