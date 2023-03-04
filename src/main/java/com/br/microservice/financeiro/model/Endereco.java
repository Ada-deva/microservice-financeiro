package com.br.microservice.financeiro.model;
import com.br.microservice.financeiro.exception.InformacaoInvalidaException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@Table(name = "Endereco")
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    @Column(nullable = false)
    private String numero;
    private String bairro;

    @Column(nullable = false)
    private String cep;
    private String cidade;
    private String estado;

    public Endereco(Long id, String logradouro, String numero, String bairro, String cep, String cidade, String estado) throws InformacaoInvalidaException {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;

        if(cep.replaceAll("[\\.-]", "").isBlank()) {
            log.warn("---CEP vazio---");
            throw new InformacaoInvalidaException("CEP inválido!");
        } else {
            log.info("---CEP não é nulo---");
            this.cep = cep;
        }
        this.cidade = cidade;
        this.estado = estado;
    }
}
