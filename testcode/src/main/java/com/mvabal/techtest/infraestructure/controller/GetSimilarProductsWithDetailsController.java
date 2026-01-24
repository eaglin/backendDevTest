package com.mvabal.techtest.infraestructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvabal.techtest.application.usecase.GetSimilarProductDetails;
import com.mvabal.techtest.infraestructure.dto.ProductDto;
import com.mvabal.techtest.infraestructure.mapper.ProductMapper;

@RestController
@RequestMapping("/product")
public class GetSimilarProductsWithDetailsController {

  private final GetSimilarProductDetails service;

  public GetSimilarProductsWithDetailsController(@Autowired final GetSimilarProductDetails service) {
    this.service = service;
  }

  @GetMapping("/{productId}/similar")
  public ResponseEntity<List<ProductDto>> getSimilarProducts(@PathVariable final String productId) {
    List<ProductDto> products = service.getSimilarProductsWithDetails(productId).stream()
        .map(ProductMapper::toDto)
        .toList();
    return ResponseEntity.ok(products);
  }
}
