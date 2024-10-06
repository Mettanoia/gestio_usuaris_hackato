package com.solafides.gestio_usuaris_hackato.application.usecases.import_tasks;

import com.solafides.gestio_usuaris_hackato.adapter.web.requests.ImportTasksRequest;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ImportAllTasks {
    Mono<Void> importAllTasks(ImportTasksRequest importTasksRequest);
}
