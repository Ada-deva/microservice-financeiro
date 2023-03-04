package com.br.microservice.financeiro.dto;

import com.br.microservice.financeiro.enums.TipoItem;
import com.br.microservice.financeiro.model.Comanda;
import com.br.microservice.financeiro.model.OrdemCompra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemCompraDTO {

    private long id;
    private TipoItem item;
    private int quantidade;
    private double valor;
    private LocalDate dataVencimento;
    private boolean isPago;
    private LocalDateTime dataPagamento;
    private long fornecedor;

    public OrdemCompraDTO of(OrdemCompra ordemCompra) {
        return OrdemCompraDTO.builder()
                .id(ordemCompra.getId())
                .item(ordemCompra.getItem())
                .quantidade(ordemCompra.getQuantidade())
                .valor(ordemCompra.getValor())
                .dataVencimento(ordemCompra.getDataVencimento())
                .isPago(ordemCompra.isPago())
                .dataPagamento(ordemCompra.getDataPagamento())
                .fornecedor(ordemCompra.getFornecedor().getId())
                .build();
    }

    public OrdemCompra toEntity() {
        return OrdemCompra.builder()
                .id(id)
                .item(item)
                .quantidade(quantidade)
                .valor(valor)
                .dataVencimento(dataVencimento)
                .isPago(isPago)
                .dataPagamento(dataPagamento)
                .build();
    }
}
