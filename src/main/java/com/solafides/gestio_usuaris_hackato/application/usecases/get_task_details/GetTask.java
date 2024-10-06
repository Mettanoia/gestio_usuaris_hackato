package com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details;

import com.solafides.gestio_usuaris_hackato.domain.Task;
import reactor.core.publisher.Mono;

public interface GetTask {
    Mono<Task> getTask(Long userId);
}
