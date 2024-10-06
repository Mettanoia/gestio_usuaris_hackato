package com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.Enrollment;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.EnrollmentRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.exceptions.EnrollmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Primary
@Service
@RequiredArgsConstructor
public final class EnrollUserInTaskService implements EnrollUserInTask {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Mono<Void> enrollUserInTask(Long userId, Long taskId) {

        return enrollmentRepository.save(new Enrollment(null, userId, taskId))

                // Switch to error reactive chain if the repository returns empty
                .switchIfEmpty(error(new EnrollmentException("Error enrolling user with ID "+ userId + "in task with ID " + taskId + ": The repository returned an empty mono.")))

                // Return Mono<Void> if everything went as expected
                .then()

                // Generic mapping of errors before propagating
                .onErrorMap(EnrollmentException::new);

    }

}
