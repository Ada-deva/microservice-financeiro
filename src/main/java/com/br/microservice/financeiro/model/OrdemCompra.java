package com.br.microservice.financeiro.model;

import com.br.microservice.financeiro.enums.TipoItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private int quantidadeTotal;

    @Column(nullable = false)
    private double valorTotal;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private boolean isPago = false;

    private LocalDateTime dataPagamento;

    @OneToMany
    private List<Insumo> listaInsumos;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
    private String identificador;



}
