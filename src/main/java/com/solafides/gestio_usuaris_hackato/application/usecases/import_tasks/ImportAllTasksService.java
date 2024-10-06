package com.solafides.gestio_usuaris_hackato.application.usecases.import_tasks;

import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.TaskMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.adapter.web.requests.ImportTasksRequest;
import com.solafides.gestio_usuaris_hackato.application.usecases.import_tasks.exceptions.ImportallTasksException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Primary
@Service
@RequiredArgsConstructor
final class ImportAllTasksService implements ImportAllTasks {

    private final TaskRepository taskRepository;

    @Override
    public Mono<Void> importAllTasks(ImportTasksRequest importTasksRequest) {

        return taskRepository.saveAll(importTasksRequest.tasks().stream().map(TaskMapper::mapToEntity).toList())

                .then()

                .onErrorMap(ImportallTasksException::new);


    }

}
