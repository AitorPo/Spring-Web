package com.svalero.springweb.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información visible por los/as clientes/as (Data Transfer Object)")
public class VendorDTO {

    @Schema(description = "Nombre del vendedor/a", example = "Aitor")
    private String name;

    @Schema(description = "Teléfono del vendedor/a", example = "123456798")
    private int phone;

    @Schema(description = "JWT del vendedor/a", example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJKV1QiLCJzdWIiOi...")
    private String token;
}
