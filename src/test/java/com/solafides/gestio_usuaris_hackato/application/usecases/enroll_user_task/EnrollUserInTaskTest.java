package com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.Enrollment;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.EnrollmentRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.exceptions.EnrollmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

class EnrollUserInTaskServiceTest {

    private EnrollmentRepository enrollmentRepository;
    private EnrollUserInTask enrollUserInTaskService;

    @BeforeEach
    void setUp() {
        enrollmentRepository = mock(EnrollmentRepository.class);
        enrollUserInTaskService = new EnrollUserInTaskService(enrollmentRepository);
    }

    @Test
    void enrollUserInTask_happyPath() {

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(just(new Enrollment(1L, 1L, 1L)));

        Mono<Void> result = enrollUserInTaskService.enrollUserInTask(1L, 1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void enrollUserInTask_emptyMono() {

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(empty());

        Mono<Void> result = enrollUserInTaskService.enrollUserInTask(1L, 1L);

        StepVerifier.create(result)
                .expectError(EnrollmentException.class)
                .verify();

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void enrollUserInTask_databaseException() {

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(error(new RuntimeException("Database error")));

        Mono<Void> result = enrollUserInTaskService.enrollUserInTask(1L, 1L);

        StepVerifier.create(result)
                .expectError(EnrollmentException.class)
                .verify();

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

}
