package com.solafides.gestio_usuaris_hackato.application.usecases.export_tasks;

import com.solafides.gestio_usuaris_hackato.domain.Task;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface ExportAllTasks {
    Flux<Task> exportAllTasks();
}
