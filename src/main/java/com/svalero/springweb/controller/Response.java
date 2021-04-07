package com.svalero.springweb.controller;

import com.svalero.springweb.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Respuesta genérica cuando lo que hay que devolver no es información
 * sino la confirmación que una operación se ha ejecutado correctamente o
 * bien un error porque algo no ha ocurrido como se esperaba
 */

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response {
    public static final int NO_ERROR = 0;
    public static final int NOT_FOUND = 101;

    public static final String NO_MESSAGE = "Operación realizada satisfactoriamente";

    private Error error;

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Error{
        private long errorCode;
        private String errorMessage;
        //private Product product;

        public Error(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }

    /*public static Response noErrorResponse(Product product) {
        return new Response(new Error(NO_ERROR, NO_MESSAGE, product));
    }*/
    public static Response noErrorResponse() {
        return new Response(new Error(NO_ERROR, NO_MESSAGE));
    }

    public static Response errorResponse(int errorCode, String errorMessage){
        return new Response(new Error(errorCode, errorMessage));
    }
}
