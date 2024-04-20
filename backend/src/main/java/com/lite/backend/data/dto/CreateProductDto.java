package com.lite.backend.data.dto;

import java.util.List;

public record CreateProductDto(
        String name,
        String characteristics,
        String prices,
        String companyNit
) { }
