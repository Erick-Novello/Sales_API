package ericknovello.com.github.sales_api.controller;

import ericknovello.com.github.sales_api.model.Product;
import ericknovello.com.github.sales_api.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Product API")
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;


    @PostMapping
    @ApiOperation(value = "Insert a new Product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product save with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User"),
            @ApiResponse(code = 403, message = "User without access right"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    private Product includeProduct(@RequestBody @Valid Product product) {
        return productService.includeProduct(product);
    }

    @PutMapping("{/id}")
    @ApiOperation(value = "Update product information")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product update with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("{/id}")
    @ApiOperation(value = "Delete Product by Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product deleted with successfully"),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 401, message = "Unauthorized User "),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("{/id}")
    @ApiOperation(value = "Find client by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product found with successfully"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Product findProductById(@PathVariable Integer id) {
        return productService.findProductById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find all Products")
    @ApiResponses({
            @ApiResponse(code = 403, message = "User without access right")
    })
    public List<Product> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> filterProduct(Product product) {
        return productService.filterProduct(product);
    }

}