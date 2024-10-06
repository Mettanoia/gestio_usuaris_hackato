package com.solafides.gestio_usuaris_hackato.adapter.data.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users_tasks")
public record Enrollment(@Id Long id, Long userId, Long taskId) {
}
