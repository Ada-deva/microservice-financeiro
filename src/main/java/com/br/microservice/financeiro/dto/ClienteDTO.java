package com.br.microservice.financeiro.dto;

import com.br.microservice.financeiro.exception.InformacaoInvalidaException;
import com.br.microservice.financeiro.model.Cliente;
import com.br.microservice.financeiro.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private long id;
    private String nome;
    private String celular;
    private String email;
    private String cpf;
    private Endereco endereco;
    private LocalDateTime dataCadastro;

    public ClienteDTO of(Cliente cliente) {
        return ClienteDTO
                .builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .celular(cliente.getCelular())
                .email(cliente.getEmail())
                .cpf(cliente.getCpf())
                .endereco(cliente.getEndereco())
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public Cliente toEntity(){
        return Cliente
                .builder()
                .id(id)
                .nome(nome)
                .celular(celular)
                .email(email)
                .cpf(cpf)
                .endereco(endereco)
                .dataCadastro(dataCadastro)
                .build();
    }


}
