package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@Entity(name = "order_details")
public class OrderDetail {

    //@Schema(description = "Identificador de la factura", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Schema(description = "Cantidad de cada producto", example = "1", required = true)
    @Column
    private int quantity;

    //@Schema(description = "Cantidad a abonar por el total de la factura", example = "200.50", defaultValue = "0.00")
    @Column
    @Min(value = 0)
    private float price;

    //@Schema(description = "Identificador de cada producto que contiene la facutra", example = "1", required = true)
    @ManyToOne
    // JoinColumn SIEMPRE va en el lado N de la relación
    @JoinColumn(name = "product_id")
    private Product product;

    //@Schema(description = "Identificador de cada pedido que contiene la facutra", example = "1", required = true)
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
