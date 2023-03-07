package com.br.microservice.financeiro.model;

import com.br.microservice.financeiro.enums.TipoFornecedor;
import com.br.microservice.financeiro.enums.TipoSeguimento;
import com.br.microservice.financeiro.validation.ClienteGroupSequenceProvider;
import com.br.microservice.financeiro.validation.PessoaFisica;
import com.br.microservice.financeiro.validation.PessoaJuridica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;


import javax.persistence.*;


@Data
@Entity
@Table(name = "Fornecedor")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@GroupSequenceProvider(value = ClienteGroupSequenceProvider.class)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Column(unique = true, nullable = false)
    @CPF(groups = PessoaFisica.class)
    @CNPJ(groups = PessoaJuridica.class)
    private String cpfOuCnpj;

    private TipoSeguimento seguimento = TipoSeguimento.OUTRO;
    private TipoFornecedor tipoFornecedor;
    private String identificador;

}
