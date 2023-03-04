package com.br.microservice.financeiro.repository;

import com.br.microservice.financeiro.model.Comanda;
import org.springframework.data.repository.CrudRepository;

public interface ComandaRepository extends CrudRepository<Comanda, Long> {
}
