package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task;

import com.solafides.gestio_usuaris_hackato.domain.Task;
import reactor.core.publisher.Mono;

public interface CreateNewTask {
    Mono<Task> createNewTask(Task task);
}
