package com.solafides.gestio_usuaris_hackato.adapter.data.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
public record UserEntity(@Id Long id, String name, String surname, String email) {
}
