package com.svalero.springweb.controller;



import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.domain.dto.OrderDTO;
import com.svalero.springweb.exception.OrderNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.service.order.OrderService;
import com.svalero.springweb.service.vendor.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "Orders", description = "Listado de pedidos")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private VendorService vendorService;


    @Operation(summary = "Obtiene el listado de los pedidos de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pedidos", content =  @Content(schema = @Schema(implementation = Order.class)))
    })
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<Set<Order>> getOrders(){
        logger.info("Inicio de getOrders()");
        Set<Order> orders = null;
        orders = orderService.findAll();
        logger.info("Fin de getOrders()");

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Uno de los endpoints extra. Obtiene el promedio de ventas de un vendedor/a por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve un JSON con la cantidad promedio de venta", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "JSON con error: Ese/a vendedor/a no ha realizado ningún pedido", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "JSON con error de vendedor/a no encontrado/a", content = @Content(schema = @Schema(implementation = Vendor.class)))
    })
    @GetMapping(value = "/orders/details/average", produces = "application/json")
    public ResponseEntity getAveragePrice (@RequestParam(value = "vendor_id", defaultValue = "0") long vendorId){
        vendorService.findById(vendorId).orElseThrow(() -> new VendorNotFoundException(vendorId));
        orderService.getOrderByVendor(vendorId).orElseThrow(() -> new VendorNotFoundException("Ese/a vendedor/a no ha realizado ningún pedido"));
        float result = orderService.getAveragePrice(vendorId);

        Map<String, Float> averagePrice = new HashMap<>();
        averagePrice.put("average_price", result);

        return new ResponseEntity(averagePrice, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los datos de un pedido mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve el JSON del pedido", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(schema = @Schema(implementation = Order.class))),
    })
    @GetMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id){
        Order order = orderService.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Permite añadir un pedido a la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Añade un pedido a la BD. Devolverá el JSON del pedido", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Vendedor/a no encontrado/a", content = @Content(schema = @Schema(implementation = Vendor.class)))
    })
    @PostMapping(value = "/orders", produces = "application/json", consumes = "application/json")
    public ResponseEntity addOrder(@RequestBody OrderDTO orderDTO) {
        Vendor vendor = vendorService.getVendor(orderDTO.getVendorId());
        if (vendor == null) return handlerException(new VendorNotFoundException("Vendedor/a no encontrado/a"));

        Order order = orderService.addOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Operation(summary = "Actualiza los datos de un pedido mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualiza el pedido. Devuelve el JSON actualziado", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(schema = @Schema(implementation = Order.class))),
    })
    @PutMapping(value = "/orders/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity updateOrder(@PathVariable("id") long id, @RequestBody Order newOrder){
        if (newOrder.getVendor() == null)
            return handlerException(new VendorNotFoundException("Vendedor/a no encontrado/a"));
        Order order = orderService.modifyOrder(id, newOrder);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un pedido de la BD mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimina el pedido de la BD. Devuelve mensaje informativo", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(schema = @Schema(implementation = Order.class))),
    })
    @DeleteMapping(value = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteOrder(@PathVariable("id") long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza campos determinados de un pedido a a partir de su id. Se pueden 'parchear' varios campos a la vez")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido 'parcheado' correctamente", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(schema = @Schema(implementation = Order.class)))
    })
    @PatchMapping(value = "/orders/{id}")
    public ResponseEntity patchOrder(@PathVariable("id") long id, @RequestBody Map<Object, Object> fields){
        Order order = orderService.getOrder(id);
        fields.forEach((k, v) ->{
            Field field = ReflectionUtils.findField(Order.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, order, v);
        });
        orderService.modifyOrder(id, order);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @ExceptionHandler(VendorNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(VendorNotFoundException vnfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, vnfe.getMessage());
        logger.error(vnfe.getMessage(), vnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(OrderNotFoundException onfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, onfe.getMessage());
        logger.error(onfe.getMessage(), onfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
