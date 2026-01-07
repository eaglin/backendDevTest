package com.mvabal.techtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvabal.techtest.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductsController {

  private ProductService productService;

  public ProductsController(@Autowired ProductService productService) {
    this.productService = productService;

  }

  @GetMapping("/{productId}/similar")
  public List<Product> getSimilarProducts(@PathVariable String productId) {
    return productService.getSimilarProductsWithDetails(productId);
  }

  @GetMapping("")
  public String isWorking() {
    return "Hello World";
  }

}
