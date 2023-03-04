package com.br.microservice.financeiro.repository;


import com.br.microservice.financeiro.model.OrdemCompra;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdemCompraRepository extends CrudRepository<OrdemCompra, Long> {

    List<OrdemCompra> findByDataVencimentoGreaterThanEqual(LocalDate today);
    List<OrdemCompra> findByDataVencimentoLessThanAndIsPagoFalse(LocalDate today);

    List<OrdemCompra> findByIsPagoTrue();

    List<OrdemCompra> findByIsPagoFalse();
}
