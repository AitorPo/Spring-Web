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
@Schema(description = "Información visible por los/as clientes/as (Data Transfer Object)")
public class OrderDTO {

    @Schema(description = "Listado de los nombres de los productos adquiridos")
    private List<String> products;

    @Schema(description = "Precio, libre de impuestos, de los productos", example = "120")
    private float subtotal;

    @Schema(description = "Cantidad final a abonar una vez aplicados los impuestos", example = "140")
    private float price;

    @Schema(description = "Identificador del vendedor/a que ha emitida la factura. No se usa el nombre u apellido por privacidad", example = "1")
    private long vendorId;

    @Schema(description = "Nombre de la tienda en la que se ha generado el pedido", example = "El Corte Inglés")
    private String shopName;
}
