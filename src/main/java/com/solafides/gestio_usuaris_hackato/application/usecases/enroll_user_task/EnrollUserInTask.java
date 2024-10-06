package com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task;

import reactor.core.publisher.Mono;

public interface EnrollUserInTask {
    Mono<Void> enrollUserInTask(Long userId, Long taskId);
}
