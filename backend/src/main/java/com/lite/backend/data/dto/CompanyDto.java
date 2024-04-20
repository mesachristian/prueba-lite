package com.lite.backend.data.dto;

import java.util.List;

public record CompanyDto(
        String nit,
        String name,
        String address,
        String phone) {}
