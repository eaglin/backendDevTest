package com.mvabal.techtest.application.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mvabal.techtest.application.dtos.ProductDto;
import com.mvabal.techtest.domain.repository.ProductRepository;

@Service
public class GetProductDetailsService {

  private final ProductRepository productRepository;

  public GetProductDetailsService(@Qualifier("productRepositoryHttps") ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Optional<ProductDto> getProductDetails(String productId) {
    return productRepository.getProductDetails(productId);
  }
}
