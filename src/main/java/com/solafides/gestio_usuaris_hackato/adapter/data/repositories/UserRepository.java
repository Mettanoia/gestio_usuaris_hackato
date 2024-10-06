package com.solafides.gestio_usuaris_hackato.adapter.data.repositories;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
