package com.svalero.springweb.controller;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.exception.ShopNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.service.vendor.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Tag(name = "Vendors", description = "Listado de vendedores/as")
public class VendorController {

    private final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @Operation(summary = "Lista todos los vendeodres/as de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vendedores/as",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vendor.class))))
    })
    @GetMapping(value = "/vendors", produces = "application/json")
    public ResponseEntity<Set<Vendor>> getVendors(){
        logger.info("Inicio de getVendors()");
        Set<Vendor> vendors = null;
        vendors = vendorService.findAll();
        logger.info("Fin de getVendors()");
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un vendedor/a a través de un id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor/a encontrado", content = @Content(schema = @Schema(implementation = Vendor.class))),
            @ApiResponse(responseCode = "404", description = "Vendedor/a no encontrado/a", content = @Content(schema = @Schema(implementation = Vendor.class)))
    })
    @GetMapping(value = "/vendors/{id}", produces = "application/json")
    public ResponseEntity<Vendor> getVendor(@PathVariable("id") long id){
        Vendor vendor = vendorService.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));

        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @Operation(summary = "Añade un vendedor/a a la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendedor/a añadido/a", content = @Content(schema = @Schema(implementation = Vendor.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada", content = @Content(schema = @Schema(implementation = Shop.class)))
    })
    @PostMapping(value = "/vendors", produces = "application/json", consumes = "application/json")
    public ResponseEntity addVendor(@RequestBody Vendor vendor){
        if (vendor.getShop() == null) {
            return handlerShopException(new ShopNotFoundException("Tienda no encontrada"));
        }
        Vendor newVendor = vendorService.addVendor(vendor);
        return new ResponseEntity(newVendor, HttpStatus.OK);
    }

    @Operation(summary = "Actualiza la información de un vendedor/a")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor actualizado/a correctamente", content = @Content(schema = @Schema(implementation = Vendor.class))),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado/a", content = @Content(schema = @Schema(implementation = Vendor.class)))
    })
    @PutMapping(value = "/vendors/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Vendor> updateVendor(@PathVariable("id") long id, @RequestBody Vendor newVendor){
        Vendor vendor = vendorService.modifyVendor(id, newVendor);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un vendedor/a de la BD a través del id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor eliminado/a correctamente", content = @Content(schema = @Schema(implementation = Vendor.class))),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado/a", content = @Content(schema = @Schema(implementation = Vendor.class)))
    })
    @DeleteMapping(value = "/vendors/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteVendor(@PathVariable long id){
        vendorService.deleteVendor(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @PatchMapping(value = "/vendors/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Vendor> patchVendor(@PathVariable("id") long id, @RequestBody Vendor newVendor){
        Vendor vendor = vendorService.modifyVendor(id, newVendor);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    /**
     * Método de controla el error 404 Not Found
     * Gestión de excepciones para cuando el vendedor solicitado no existe
     * @param vnfe
     * @return
     */
    @ExceptionHandler(VendorNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(VendorNotFoundException vnfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, vnfe.getMessage());
        logger.error(vnfe.getMessage(), vnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShopNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerShopException(ShopNotFoundException snfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, snfe.getMessage());
        logger.error(snfe.getMessage(), snfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
