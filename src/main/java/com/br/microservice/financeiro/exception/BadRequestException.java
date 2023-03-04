package com.br.microservice.financeiro.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String mensagem) {
        super(mensagem);
    }

}
