package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.svalero.springweb.deserializer.ShopJsonDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "vendors")
@Schema(description = "Información relativa a cada vendedor/a")
public class Vendor {

    @Schema(description = "Identificador único de cada vendedor/a", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Estado civil del vendedor/a", example = "false", defaultValue = "0")
    @Column
    private boolean married;

    @Schema(description = "Fecha de contratación del vendedor/a", example = "04-04-2021")
    @Column(name = "hired_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate hiredAt;

    @Schema(description = "Nombre del vendedor/a", example = "Aitor")
    @Column
    private String name;

    @Schema(description = "Apellido del vendedor/a", example = "Poquet")
    @Column
    private String surname;

    @Schema(description = "Correo electrónico del vendedor/a", example = "asd@asd.com")
    @Column
    private String email;

    @Schema(description = "Número de contacto del vendedor/a", example = "123456789")
    @Column
    private int phone;


    @Schema()
    @JsonDeserialize(using = ShopJsonDeserializer.class)
    @ManyToOne
    @JoinColumn(name = "shop_id")   // Hace referencia al campo "shop_id" de la tabla "vendors"
    private Shop shop;
}
