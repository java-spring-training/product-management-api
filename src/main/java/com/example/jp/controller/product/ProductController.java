package com.example.jp.controller.product;

import com.example.jp.domain.Product;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class ProductController {

    private ProductSearchResponseFactory productFactory;

    @Autowired
    public ProductController(final ProductSearchResponseFactory productFactory) {
        this.productFactory = productFactory;
    }

    @GetMapping("/product/search")
    public ProductSearchResponse searchProduct(final @Valid ProductSearchRequest request,
                                               final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidParameterException(getErrorMessage(bindingResult));
        }

        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product(1, "Toyota Camry", "Car", 1000, "Red", false, 2020, null),
                new Product(2, "BMW X5", "Car", 500, "Green", true, 2019, new Date()),
                new Product(3, "Honda SH", "Motorcycle", 3000, "White", false, 2020, null),
                new Product(4, "Yamaha Exciter", "Motorcycle", 200, "Blue", true, 2015, new Date())));

        return productFactory.createProductSearchResponse(products);
    }

    @PostMapping("/product/add")
    public SuccessResponse addProduct(final @Valid @RequestBody ProductAddRequest request,
                                      final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidParameterException(getErrorMessage(bindingResult));
        }
        return new SuccessResponse(HttpStatus.OK.value(), "SUCCESS");
    }

    @PutMapping("/product/update")
    public SuccessResponse updateProduct(final @Valid @RequestBody ProductAddRequest request,
                                      final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidParameterException(getErrorMessage(bindingResult));
        }
        return new SuccessResponse(HttpStatus.OK.value(), "SUCCESS");
    }

    @DeleteMapping("/product/delete/{id}")
    public SuccessResponse deleteProduct(
            @Min(value = 1, message = "id must be greater than 0") final @PathVariable int id) {

        return new SuccessResponse(HttpStatus.OK.value(), "SUCCESS");
    }

    private String getErrorMessage(final BindingResult bindingResult) {

        List<String> errMessages = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> errMessages.add(error.getDefaultMessage()));
        return String.join("\r\n", errMessages);
    }
}
