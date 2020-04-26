package com.phu.onlineshop.controller;

import com.phu.onlineshop.model.product.Product;
import com.phu.onlineshop.model.product.SearchMessage;
import com.phu.onlineshop.model.product.SearchMessage.Column;
import com.phu.onlineshop.model.product.SearchMessage.Operator;
import com.phu.onlineshop.response.APIResponse;
import com.phu.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public ResponseEntity<APIResponse<List<Product>>> findAll(@RequestBody final SearchMessage message)
    {
        final Column column = message.getColumn();
        final Operator operator = message.getOperator();
        final List<Operator> allowOperators = column.getAllowOperators();
        if (!allowOperators.contains(operator)) {
            final String errorMessage = String.format(
                    "Filtering by method \"%s\" is not allowed in column \"%s\". Supported operators %s",
                    operator.getName(),
                    column.getColumn(),
                    allowOperators.stream().map(allowOperator -> allowOperator.getName()).collect(Collectors.toList()));
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        if (message.getPage() < 0) {
            final String errorMessage = "Page should start from 0";
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        return new APIResponse<List<Product>>(HttpStatus.OK.value(), null, productService.findAll()).toResponseEntity();
    }
}
