package com.br.microservice.financeiro.response.message;

import lombok.Data;

@Data
public class ResponseMessage {
    private  String naoEncontrado;
    private final String cpfInvalido;
    private  String naoCadastrado;


    public ResponseMessage(String classname) {
        this.naoEncontrado = classname + " não encontrado!";
        this.cpfInvalido = "Não foi informado um CPF válido!";
        this.naoCadastrado = classname + " não cadastrado";
    }
}
