package com.mvabal.techtest.infraestructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvabal.techtest.application.dtos.ProductDto;
import com.mvabal.techtest.application.usecase.GetSimilarProductDetails;

@RestController
@RequestMapping("/product")
public class GetSimilarProductsWithDetailsController {

  private final GetSimilarProductDetails service;

  public GetSimilarProductsWithDetailsController(@Autowired final GetSimilarProductDetails service) {
    this.service = service;

  }

  @GetMapping("/{productId}/similar")
  public ResponseEntity<List<ProductDto>> getSimilarProducts(@PathVariable final String productId) {
    return ResponseEntity.ok(service.getSimilarProductsWithDetails(productId));
  }

}
