package com.lite.backend.data.dto;

import com.lite.backend.data.entity.Role;

public record TokenDto(String email, String accessToken, Role role) {
}
