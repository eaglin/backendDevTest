package com.mvabal.techtest.application.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mvabal.techtest.domain.repository.ProductRepository;

@Service
public class GetSimilarProducts {

  private final ProductRepository productRepository;

  public GetSimilarProducts(@Qualifier("productRepositoryHttps") ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<String> getSimilarProducts(String productId) {
    return productRepository.getSimilarProducts(productId);
  }
}
