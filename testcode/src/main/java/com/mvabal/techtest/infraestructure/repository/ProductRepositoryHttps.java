package com.mvabal.techtest.infraestructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.mvabal.techtest.application.dtos.ProductDto;
import com.mvabal.techtest.domain.repository.ProductRepository;

@Service("productRepositoryHttps")
public class ProductRepositoryHttps implements ProductRepository {

  private final RestClient client;

  @Autowired
  public ProductRepositoryHttps(final RestClient client) {
    this.client = client;
  }

  @Override
  public Optional<ProductDto> getProductDetails(String productId) {
    var response = client.get().uri("/product/" + productId).retrieve();

    return Optional.ofNullable(response.body(ProductDto.class));
  }

  @Override
  public List<String> getSimilarProducts(String productId) {
    var response = client.get()
        .uri("/product/" + productId + "/similarids")
        .retrieve()
        .body(new ParameterizedTypeReference<List<String>>() {
        });

    return response != null ? response : List.of();
  }

  @Override
  public List<ProductDto> getSimilarProductsWithDetails(String productId) {
    List<String> similarIds = getSimilarProducts(productId);
    if (similarIds.isEmpty())
      return List.of();

    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Callable<Optional<ProductDto>>> tasks = similarIds.stream()
          .<Callable<Optional<ProductDto>>>map(id -> () -> getProductDetails(id))
          .toList();

      return executor.invokeAll(tasks).stream()
          .map(future -> {
            try {
              return future.resultNow();
            } catch (Exception e) {
              return Optional.<ProductDto>empty();
            }
          })
          .flatMap(Optional::stream)
          .toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return List.of();
    }
  }

}
