package com.br.microservice.financeiro.exception;

public class InformacaoNaoEncontradaException extends Exception{

    public InformacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
