package com.svalero.springweb.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Schema(description = "Notificación al usuario/a sobre la operación realizada. Encapsula a la clase Error")
public class Response {
    public static final int OK = 200;
    public static final int NOT_FOUND = 404;

    public static final String MESSAGE = "Operación realizada satisfactoriamente";

    private Error error;

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(description = "Creación del mensaje de notificación para el usuario/a tras realizar una operación")
    static class Error{

        @Schema(description = "Código HTTP obtenido de la operación realizada", example = "404")
        private long errorCode;

        @Schema(description = "Mensaje obtenido tras realizar la operación")
        private String errorMessage;

        public Error(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }

    public static Response noErrorResponse() {
        return new Response(new Error(OK, MESSAGE));
    }

    public static Response errorResponse(int errorCode, String errorMessage){
        return new Response(new Error(errorCode, errorMessage));
    }
}
