package com.svalero.springweb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase para comunicarse con el cliente sin tener que enviar todos los datos del modelo de la BD
 * Son los datos que se pedirán tanto de entrada como de salida de la información
 * Es una clase de tránsido (Data Transfer Object) por lo que no tiene correspondencia con la BD
 */
@Data
@NoArgsConstructor
public class OrderDTO {

    private List<String> products;
    private float subtotal;
    private float price;
}
