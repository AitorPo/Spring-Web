package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product {

    @Schema(description = "Clave identificativa del producto", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre comercial del producto", example = "Coca-Cola", required = true)
    @Column
    private String name;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Refrescos", required = true)
    @Column
    private String category;

    @Schema(description = "Descripción del producto", example = "Sabor inimitable")
    @Column
    private String description;

    @Schema(description = "PVP del producto", example = "1.25", defaultValue = "0.00")
    @Column
    private float price;

    @Schema(description = "Fecha de alta del producto", example = "04-04-2021")
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

}
