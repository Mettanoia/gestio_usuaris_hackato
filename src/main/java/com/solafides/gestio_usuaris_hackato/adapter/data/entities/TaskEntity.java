package com.solafides.gestio_usuaris_hackato.adapter.data.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tasks")
public record TaskEntity(@Id Long id, String name, String description, Integer maxCapacity) {
}
