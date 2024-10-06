package com.solafides.gestio_usuaris_hackato.adapter.data.repositories;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.Enrollment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EnrollmentRepository extends ReactiveCrudRepository<Enrollment, Long> {
}
