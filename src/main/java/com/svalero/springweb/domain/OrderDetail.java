package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.svalero.springweb.deserializer.ProductJsonDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "order_details")
public class OrderDetail {

    @Schema(description = "Identificador de la factura", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Cantidad de cada producto", example = "1", required = true)
    @Column
    private int quantity;

    @Schema(description = "Cantidad a abonar por el total de la factura", example = "200.50", defaultValue = "0.00")
    @Column
    @Min(value = 0)
    private float price;

    @Schema(description = "Pagado con targeta o no", example = "false", defaultValue = "0")
    @Column(name = "by_card")
    private boolean byCard;

    @Schema(description = "Fecha de emisión de la factura", example = "04-04-2021")
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "Nombre de la tienda de emisión de la factura obtenida de la tienda del vendor/a",
            example = "El Corte Inglés", defaultValue = "Online")
    @Column
    private String establishment;

    @Schema()
    @JsonDeserialize(using = ProductJsonDeserializer.class)
    @ManyToOne
    // JoinColumn SIEMPRE va en el lado N de la relación
    @JoinColumn(name = "product_id")
    private Product product;

    @Schema(description = "Identificador del pedido al que corresponde cada detalle", example = "1", required = true)
    @ManyToOne
    @JoinColumn(name = "order_id")
    // Evita anidamientos en la response de la API
    @JsonBackReference
    private Order order;
}
