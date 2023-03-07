package com.br.microservice.financeiro.model;

import com.br.microservice.financeiro.enums.TipoItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@RequiredArgsConstructor
@Table(name = "Insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String marca;
    private String unidade;
    private double valor;
    private int quantidade;
    @Column(nullable = false)
    private TipoItem item;

}
