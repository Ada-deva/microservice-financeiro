package com.br.microservice.financeiro.dto;

import com.br.microservice.financeiro.enums.TipoFornecedor;
import com.br.microservice.financeiro.enums.TipoSeguimento;
import com.br.microservice.financeiro.model.Fornecedor;
import com.br.microservice.financeiro.model.OrdemCompra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {

    private long id;
    private String nome;
    private String cpfOuCnpj;
    private TipoSeguimento seguimento;
    private TipoFornecedor tipoFornecedor;
    private String identificador;


    public FornecedorDTO of(Fornecedor fornecedor) {
            return FornecedorDTO.builder()
                    .id(fornecedor.getId())
                    .nome(fornecedor.getNome())
                    .cpfOuCnpj(fornecedor.getCpfOuCnpj())
                    .seguimento(fornecedor.getSeguimento())
                    .tipoFornecedor(fornecedor.getTipoFornecedor())
                    .identificador(fornecedor.getIdentificador())
                    .build();
    }

    public Fornecedor toEntity() {
        return Fornecedor.builder()
                .id(id)
                .nome(nome)
                .cpfOuCnpj(cpfOuCnpj)
                .seguimento(seguimento)
                .tipoFornecedor(tipoFornecedor)
                .identificador(identificador)
                .build();
    }
}
