package com.svalero.springweb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.svalero.springweb.deserializer.VendorJsonDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "orders")
public class Order {

    @Schema(description = "Identificador de cada pedido", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Código alfanumérico generado aleatoriamente en cada pedido", example = "asd5-asd4...")
    @Column
    private String number;

    @Schema(description = "Fecha de realización del pedido", example = "04-04-2021")
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    @Schema(description = "Precio del pedido", example = "125.05", defaultValue = "0.00")
    @Column
    @Min(value = 0)
    private float price;

    @Schema(description = "Estado del pedido: enviado o no en caso de que sea a domicilio", example = "false", defaultValue = "0")
    @Column
    private boolean shipped;

    @Schema(description = "Identificador del vendedor/a que ha expedido la factura", example = "1", required = true)
    @JsonDeserialize(using = VendorJsonDeserializer.class)
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    // nombre del atributo que se quiere referenciar. En este caso "order" hace referencia al atributor Order order de la clase Order
    // mappedBy SIEMPRE va situado en el lado 1 de la relación
    @Schema(description = "Conjunto de datos referentes a los productos comprados")
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> details;

    public Order() { details = new ArrayList<>(); }

    public void addDetail(OrderDetail orderDetail) { details.add(orderDetail); }
}
