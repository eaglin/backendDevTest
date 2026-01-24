package com.mvabal.techtest.domain.repository;

import java.util.List;
import java.util.Optional;

import com.mvabal.techtest.domain.model.Product;

public interface ProductRepository {
  Optional<Product> getProductDetails(String productId);

  List<String> getSimilarProducts(String productId);

  List<Product> getSimilarProductsWithDetails(String productId);
}
