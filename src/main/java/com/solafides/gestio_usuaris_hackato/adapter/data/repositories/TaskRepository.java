package com.solafides.gestio_usuaris_hackato.adapter.data.repositories;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.TaskEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Long> {
}
