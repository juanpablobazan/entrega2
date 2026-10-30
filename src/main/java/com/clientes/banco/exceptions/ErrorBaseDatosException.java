package com.clientes.banco.exceptions;

public class ErrorBaseDatosException extends RuntimeException {

    public ErrorBaseDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
