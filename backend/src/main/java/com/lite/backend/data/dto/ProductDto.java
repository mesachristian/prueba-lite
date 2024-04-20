package com.lite.backend.data.dto;

import com.lite.backend.data.entity.Company;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

public record ProductDto(
        String code,
        String name,
        String characteristics,
        String prices,
        String companyName) {
}
