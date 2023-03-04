package com.br.microservice.financeiro.dto;

import com.br.microservice.financeiro.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

    public EnderecoDTO of(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }


    public Endereco toEntity() {
        Endereco endereco = new Endereco();

        endereco.setId(id);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setCep(cep);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);

        return endereco;
    }
}
