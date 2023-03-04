package com.br.microservice.financeiro.exception;

public class InformacaoInvalidaException extends Exception{

    public InformacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
