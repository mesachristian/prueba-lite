package com.lite.backend.data.dto;

import com.lite.backend.data.entity.Role;

public record SignUpDto(String email, String password, Role role) {
}
