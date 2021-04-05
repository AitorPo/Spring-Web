package com.svalero.springweb.controller;

import com.svalero.springweb.domain.Product;
import com.svalero.springweb.exception.ProductNotFoundException;
import com.svalero.springweb.service.ProductService;
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
@Tag(name = "Products", description = "Listado de productos")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * @RequestParam es un parámetro de consulta (Query param) para la BD
     * @param category
     * @return
     */
    @Operation(summary = "Lista todos los productos de la BD según los parámetros que se le pasen a la URL." +
            "findProducts() gestionará dichos parámetros e invocará a unos métodos u otros que devolverán cada uno" +
            "una response distinta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lisa de productos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    })
    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<Set<Product>> getProducts(@RequestParam(value = "category", defaultValue = "") String category,
                                                    @RequestParam(value = "name", defaultValue = "") String name,
                                                    @RequestParam(value = "price", defaultValue = "") Float price){
        logger.info("Inicio de getProducts()");
        Set<Product> products = null;
        products = productService.findProducts(category, name, price);

        logger.info("Fin de getProducts()");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * @PathVariable es un parámetro que se recibe por la URL
     * @param id
     * @return
     */
    @Operation(summary = "Consulta de un producto específico a través de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto econtrado", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id){
        Product product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * @RequestBody es un parámetro que se recibe por el body de la página y transporta un objeto con información
     * @param product
     * @return
     */
    @Operation(summary = "Añade un nuevo producto a la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto añadido", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PostMapping(value = "/products", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    /**
     * @PathVariable es un parámetro que se recibe por la URL
     * @RequestBody es un parámetro que se recibe por el body de la página y transporta un objeto con información
     * @param id
     * @param newProduct
     * @return
     */
    @Operation(summary = "Actualiza la información de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto modificado correctamente", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @PutMapping(value = "/products/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product newProduct){
        Product product = productService.modifyProduct(id, newProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PatchMapping(value = "/products/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable("id") long id, @RequestBody Product patchedProduct){
        Product product = productService.modifyProduct(id, patchedProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*@PatchMapping(value = "/products/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Product> patchProduct(@PathVariable("id") long id, @RequestBody JsonPatch patch){
        try {
            Product product = productService.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
            Product patchedProduct = applyPatchToProduct(patch, product);
            productService.modifyProduct(id, patchedProduct);
            return new ResponseEntity<>(patchedProduct, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private Product applyPatchToProduct(JsonPatch patch, Product targetProduct) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = null;
        assert false;
        JsonNode patched = patch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }
*/
    @Operation(summary = "Elimina un producto existente de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @DeleteMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    /**
     * Método de controla el error 404 Not Found
     * Gestión de excepciones para cuando el producto solicitado no existe
     * @param pnfe
     * @return
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handlerException(ProductNotFoundException pnfe){
        Response response = Response.errorResponse(Response.NOT_FOUND, pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
