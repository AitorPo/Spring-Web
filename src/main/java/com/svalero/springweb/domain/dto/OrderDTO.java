package com.svalero.springweb.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Data Transfer Object", description = "Información visible por los/as clientes/as")
public class OrderDTO {

    @Schema(description = "Listado de los nombres de los productos adquiridos")
    private List<String> products;

    @Schema(description = "Precio, libre de impuestos, de los productos", example = "120")
    private float subtotal;

    @Schema(description = "Cantidad final a abonar una vez aplicados los impuestos", example = "140")
    private float price;

    @Schema(description = "Apellido del/la vendedor/a que ha realizado la operación", example = "Sánchez")
    private String vendorSurname;
}
