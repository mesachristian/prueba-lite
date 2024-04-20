package com.lite.backend.data.dto;

import java.util.List;

public record CompanyProductsDto(
        String name,
        List<ProductDto> products
) {
}
