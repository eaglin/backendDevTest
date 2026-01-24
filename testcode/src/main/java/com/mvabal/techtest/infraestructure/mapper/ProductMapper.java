package com.mvabal.techtest.infraestructure.mapper;

import com.mvabal.techtest.domain.model.Product;
import com.mvabal.techtest.infraestructure.dto.ProductDto;

public final class ProductMapper {

  private ProductMapper() {
  }

  public static Product toDomain(ProductDto dto) {
    if (dto == null) {
      return null;
    }
    return new Product(dto.id(), dto.name(), dto.price(), dto.availability());
  }

  public static ProductDto toDto(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductDto(product.id(), product.name(), product.price(), product.availability());
  }
}
