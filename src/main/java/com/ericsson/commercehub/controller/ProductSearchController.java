package com.ericsson.commercehub.controller;

import com.ericsson.commercehub.dto.Product;
import com.ericsson.commercehub.service.ProductSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductSearchController {

    private final Logger logger = LoggerFactory.getLogger(ProductSearchController.class);

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("/search/{productName}")
    public List<Product>  searchProduct(@PathVariable String productName) {

        logger.debug("Inside searchProduct() starts");

        List<Product> productList =  productSearchService.findProduct(productName);

        logger.debug("Inside searchProduct() ends");

        return productList;
    }
}

