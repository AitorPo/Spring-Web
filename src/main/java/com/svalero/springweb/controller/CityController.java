package com.svalero.springweb.controller;

import com.svalero.springweb.domain.City;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.exception.CityNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.service.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Cities", description = "Listado de ciudades")
public class CityController {

    Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @Operation(summary = "Listado de todas las ciudades de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ciudades", content = @Content(schema = @Schema(implementation = City.class)))
    })
    @GetMapping(value = "/cities", produces = "application/json")
    public ResponseEntity<Set<City>> getCities(){
        logger.info("Inicio de getCities()");
        Set<City> cities = null;
        cities = cityService.findAll();
        logger.info("Fin de getCities()");

        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la información de una ciudad a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada", content = @Content(schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/cities/{id}", produces = "application/json")
    public ResponseEntity<City> getCity(@PathVariable("id") long id){
        City city = cityService.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Operation(summary = "Añade una ciudad a la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ciudad añadida correctamente", content = @Content(schema = @Schema(implementation = City.class)))
    })
    @PostMapping(value = "/cities", produces = "application/json", consumes = "application/json")
    public ResponseEntity<City> addCity(@RequestBody City city){
        City newCity = cityService.addCity(city);
        return new ResponseEntity<>(newCity, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza los datos de una ciudad a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada correctamente", content = @Content(schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/cities/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<City> updateCity(@PathVariable("id") long id, @RequestBody City newCity){
        City city = cityService.modifyCity(id, newCity);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una ciudad de la BD a través de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad eliminada correctamente", content = @Content(schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/cities/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteCity(@PathVariable("id") long id){
        cityService.deleteCity(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Actualiza campos determinados de una ciudad a partir de su id. Se pueden 'parchear' varios campos a la vez")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad 'parcheada' correctamente", content = @Content(schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/cities/{id}")
    public ResponseEntity patchCity(@PathVariable("id") long id, @RequestBody Map<Object, Object> fields){
        City city = cityService.getCity(id);
        if (city == null){
            return handlerException(new CityNotFoundException(id));
        }

        fields.forEach((k, v) ->{
            Field field = ReflectionUtils.findField(City.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, city, v);
        });
        cityService.modifyCity(id, city);
        return new ResponseEntity(city, HttpStatus.OK);
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(CityNotFoundException cnfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, cnfe.getMessage());
        logger.error(cnfe.getMessage(), cnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
