package com.svalero.springweb.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "cities")
public class City {

    @Schema(description = "Identificativo de la ciudad", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de la ciudad", example = "Valencia")
    @Column
    private String name;
}
