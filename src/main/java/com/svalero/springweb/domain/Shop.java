package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@Entity(name = "shops")
public class Shop {

    @Schema(description = "Número identificativo de cada tienda", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de cada tienda", example = "El Corte Inglés")
    @Column
    private String name;

    @Schema(description = "Superficie de la tienda en metros cuadrados", example = "1000")
    @Column
    private float meters;

    @Schema(description = "Indicativo de si la tienda dispone de Párking o no", example = "true", defaultValue = "0")
    @Column
    private boolean parking;

    @Schema(description = "Número de plazas de aparcamiento", example = "210", defaultValue = "0")
    @Column(name = "parking_places")
    @Min(value = 0)
    private int parkingPlaces;

    @Schema(description = "Fecha de inauguración de la tienda", example = "05-04-2021")
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "Dirección de la tienda", example = "C/Colón 21, Valencia")
    @Column
    private String adress;

    @Schema(description = "Identificativo de la ciudad en la que se encuentra la tienda", example = "1")
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
