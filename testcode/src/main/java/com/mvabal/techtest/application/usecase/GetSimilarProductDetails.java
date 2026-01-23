
package com.mvabal.techtest.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mvabal.techtest.application.dtos.ProductDto;
import com.mvabal.techtest.domain.repository.ProductRepository;

@Service
public class GetSimilarProductDetails {

  private final ProductRepository productRepository;

  public GetSimilarProductDetails(@Qualifier("productRepositoryHttps") ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<ProductDto> getSimilarProductsWithDetails(String productId) {
    return productRepository.getSimilarProductsWithDetails(productId);
  }
}
