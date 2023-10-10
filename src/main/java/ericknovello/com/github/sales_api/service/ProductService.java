package ericknovello.com.github.sales_api.service;

import ericknovello.com.github.sales_api.model.Product;
import ericknovello.com.github.sales_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "Product not found";

    public Product includeProduct(Product product) {
        return productRepository.save(product);
    }

    public void updateProduct(Integer id, Product product) {
        productRepository.findById(id)
                .map(productFound -> {
                    product.setId(productFound.getId());
                    productRepository.save(product);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public void deleteProduct(Integer id) {
        productRepository.findById(id)
                .map(productFound -> {
                    productRepository.delete(productFound);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> filterProduct(Product product) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> productFilter = Example.of(product, exampleMatcher);

        return productRepository.findAll(productFilter);
    }

}