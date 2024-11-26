package com.trabalhodeback.bibliotecadigital.dto;

import com.trabalhodeback.bibliotecadigital.model.UserRole;

public record RegisterDTO(String email, String password, String nome, UserRole role) {
}
