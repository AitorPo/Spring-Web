package com.svalero.springweb.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.svalero.springweb.domain.Product;
import com.svalero.springweb.exception.ProductNotFoundException;
import com.svalero.springweb.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * @RequestParam es un parámetro de consulta (Query param) para la BD
     * @param category
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<Set<Product>> getProducts(@RequestParam(value = "category", defaultValue = "") String category){
        logger.info("Inicio de getProducts()");
        Set<Product> products = null;
        products = productService.findProducts(category);

        logger.info("Fin de getProducts()");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * @PathVariable es un parámetro que se recibe por la URL
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
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
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product addedProduct = productService.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    /**
     * @PathVariable es un parámetro que se recibe por la URL
     * @RequestBody es un parámetro que se recibe por el body de la página y transporta un objeto con información
     * @param id
     * @param newProduct
     * @return
     */
    @PutMapping("/products/{id}")
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
    @DeleteMapping("/products/{id}")
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
