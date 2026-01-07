package com.mvabal.techtest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.mvabal.techtest.controller.Product;

@Service
public class ProductService {
  private RestClient client;

  public ProductService() {
    client = RestClient.builder().baseUrl("http://localhost:3001/")
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("Accept", "application/json").build();

  }

  public List<Product> getSimilarProductsWithDetails(String productId) {
    List<String> similarIds = getSimilarProducts(productId);
    if (similarIds.isEmpty())
      return new ArrayList<>();

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Future<Product>> futures = similarIds.stream()
          .map(id -> executor.submit(() -> getProductDetails(id)))
          .toList();

      return futures.stream()
          .map(future -> {
            try {
              return future.get();
            } catch (Exception e) {
              return null;
            }
          })
          .filter(Objects::nonNull)
          .toList();
    }
  }

  public List<String> getSimilarProducts(String productId) {

    var response = client.get().uri("/product/" + productId + "/similarids").retrieve();
    List<String> similarIds = response.body(new ParameterizedTypeReference<List<String>>() {
    });
    return similarIds;
  }

  public Product getProductDetails(String productId) {
    var response = client.get().uri("/product/" + productId).retrieve();
    Product product = response.body(Product.class);
    return product;
  }

}
