package com.mvabal.techtest.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mvabal.techtest.application.usecase.GetProductDetailsService;
import com.mvabal.techtest.infraestructure.dto.ProductDto;
import com.mvabal.techtest.infraestructure.mapper.ProductMapper;

@RestController
public class GetProductDetailsController {

  private final GetProductDetailsService service;

  public GetProductDetailsController(GetProductDetailsService service) {
    this.service = service;
  }

  @GetMapping("/product/{productId}")
  public ResponseEntity<ProductDto> getProductDetails(@PathVariable final String productId) {
    return service.getProductDetails(productId)
        .map(ProductMapper::toDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
