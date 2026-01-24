package com.mvabal.techtest.infraestructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.mvabal.techtest.domain.model.Product;
import com.mvabal.techtest.domain.repository.ProductRepository;
import com.mvabal.techtest.infraestructure.dto.ProductDto;
import com.mvabal.techtest.infraestructure.mapper.ProductMapper;

@Service("productRepositoryHttps")
public class ProductRepositoryHttps implements ProductRepository {

  private final RestClient client;

  @Autowired
  public ProductRepositoryHttps(final RestClient client) {
    this.client = client;
  }

  @Override
  public Optional<Product> getProductDetails(String productId) {
    var response = client.get().uri("/product/" + productId).retrieve();
    ProductDto dto = response.body(ProductDto.class);

    return Optional.ofNullable(ProductMapper.toDomain(dto));
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
  @Cacheable("similarProductsWithDetails")
  public List<Product> getSimilarProductsWithDetails(String productId) {
    List<String> similarIds = getSimilarProducts(productId);
    if (similarIds.isEmpty())
      return List.of();

    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      List<Callable<Optional<Product>>> tasks = similarIds.stream()
          .<Callable<Optional<Product>>>map(id -> () -> getProductDetails(id))
          .toList();

      return executor.invokeAll(tasks).stream()
          .map(future -> {
            try {
              return future.resultNow();
            } catch (Exception e) {
              return Optional.<Product>empty();
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
