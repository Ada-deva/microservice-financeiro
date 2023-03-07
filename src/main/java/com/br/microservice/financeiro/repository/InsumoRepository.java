package com.br.microservice.financeiro.repository;


import com.br.microservice.financeiro.model.Insumo;
import org.springframework.data.repository.CrudRepository;

public interface InsumoRepository extends CrudRepository<Insumo, Long> {
}
