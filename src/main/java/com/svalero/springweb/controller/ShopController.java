package com.svalero.springweb.controller;

import com.svalero.springweb.domain.City;
import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.exception.CityNotFoundException;
import com.svalero.springweb.exception.ShopNotFoundException;
import com.svalero.springweb.service.shop.ShopService;
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
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


@RestController
@Tag(name = "Shops", description = "Listado de tiendas")
public class ShopController {

    private final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    @Operation(summary = "Listado de todas las tiendas de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tiendas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Shop.class))))
    })
    @GetMapping(value = "/shops", produces = "application/json")
    public ResponseEntity<Set<Shop>> getShops() {
        logger.info("Inicio de getShops()");
        Set<Shop> shops = null;
        shops = shopService.findAll();
        logger.info("Fin de getShops()");

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los datos de una tienda a través de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda encontrada", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada", content = @Content(schema = @Schema(implementation = Shop.class)))
    })
    @GetMapping(value = "/shops/{id}", produces = "application/json")
    public ResponseEntity<Shop> getShop(@PathVariable("id") long id){
        Shop shop = shopService.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @Operation(summary = "Añade una tienda a la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tienda añadida", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = City.class)))
    })
    @PostMapping(value = "/shops", produces = "application/json", consumes = "application/json")
    public ResponseEntity addShop(@RequestBody Shop shop){
        if (shop.getCity() == null){
            return handlerShopException(new CityNotFoundException("Ciudad no encontrada"));
        }
        Shop newShop = shopService.addShop(shop);
        return new ResponseEntity(newShop, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza los datos de una tienda a través de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda actualizada", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = City.class)))
    })
    @PutMapping(value = "/shops/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity updateShop(@PathVariable("id") long id, @RequestBody Shop newShop){
        if (newShop.getCity() == null){
            return handlerShopException(new CityNotFoundException("Ciudad no encontrada"));
        }
        Shop shop = shopService.modifyShop(id, newShop);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @Operation(summary = "Borra una tienda de la BD a través de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda eliminada correctamente", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada", content = @Content(schema = @Schema(implementation = Shop.class)))
    })
    @DeleteMapping(value = "/shops/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Response> deleteShop(@PathVariable long id){
        shopService.deleteShop(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza campos determinados de una tienda a partir de su id. Se pueden 'parchear' varios campos a la vez")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda 'parcheada' correctamente", content = @Content(schema = @Schema(implementation = Shop.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada", content = @Content(schema = @Schema(implementation = Shop.class)))
    })
    @PatchMapping(value = "/shops/{id}")
    public ResponseEntity patchShop(@PathVariable("id") long id, @RequestBody Map<Object, Object> fields){
        Shop shop = shopService.getShop(id);
        if (shop == null){
            return handlerException(new ShopNotFoundException(id));
        }

        fields.forEach((k, v) ->{
            Field field = ReflectionUtils.findField(Shop.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, shop, v);
        });
        shopService.modifyShop(id, shop);
        return new ResponseEntity(shop, HttpStatus.OK);
    }

    @ExceptionHandler(ShopNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(ShopNotFoundException snfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, snfe.getMessage());
        logger.error(snfe.getMessage(), snfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerShopException(CityNotFoundException cnfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, cnfe.getMessage());
        logger.error(cnfe.getMessage(), cnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}