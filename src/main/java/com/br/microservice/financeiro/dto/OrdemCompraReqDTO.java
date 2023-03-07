package com.br.microservice.financeiro.dto;

import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.model.Insumo;
import com.br.microservice.financeiro.model.OrdemCompra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdemCompraReqDTO {

    private long id;

    private LocalDateTime dataCriacao;

    private int quantidadeTotal;
    private double valorTotal;
    private LocalDate dataVencimento;
    private boolean isPago;
    private LocalDateTime dataPagamento;
    private List<Insumo> listaInsumos;
    private Fornecedor fornecedor;
    private String identificador;

    public OrdemCompraReqDTO of(OrdemCompra ordemCompra) {
        return OrdemCompraReqDTO.builder()
                .id(ordemCompra.getId())
                .dataCriacao(getDataCriacao())
                .quantidadeTotal(ordemCompra.getQuantidadeTotal())
                .valorTotal(ordemCompra.getValorTotal())
                .dataVencimento(ordemCompra.getDataVencimento())
                .isPago(ordemCompra.isPago())
                .dataPagamento(ordemCompra.getDataPagamento())
                .listaInsumos(ordemCompra.getListaInsumos())
                .fornecedor(ordemCompra.getFornecedor())
                .identificador(ordemCompra.getIdentificador())
                .build();
    }

    public OrdemCompra toEntity() {
        return OrdemCompra.builder()
                .id(id)
                .dataCriacao(dataCriacao)
                .quantidadeTotal(quantidadeTotal)
                .valorTotal(valorTotal)
                .dataVencimento(dataVencimento)
                .isPago(isPago)
                .dataPagamento(dataPagamento)
                .listaInsumos(listaInsumos)
                .identificador(identificador)
                .build();
    }
}
