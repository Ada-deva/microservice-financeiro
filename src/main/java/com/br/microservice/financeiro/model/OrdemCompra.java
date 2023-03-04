package com.br.microservice.financeiro.model;

import com.br.microservice.financeiro.enums.TipoItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OrdemCompra")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrdemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private TipoItem item;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private boolean isPago = false;

    private LocalDateTime dataPagamento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

}
