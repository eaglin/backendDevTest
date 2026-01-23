package com.mvabal.techtest.domain.repository;

import java.util.List;
import java.util.Optional;

import com.mvabal.techtest.application.dtos.ProductDto;

public interface ProductRepository {
  public Optional<ProductDto> getProductDetails(String productId);

  public List<String> getSimilarProducts(String productId);

  public List<ProductDto> getSimilarProductsWithDetails(String productId);

}
