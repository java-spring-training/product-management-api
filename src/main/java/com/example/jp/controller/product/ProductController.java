package com.example.jp.controller.product;

import com.example.jp.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
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

    private String getErrorMessage(final BindingResult bindingResult) {

        List<String> errMessages = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> errMessages.add(error.getDefaultMessage()));
        return String.join("\r\n", errMessages);
    }
}
