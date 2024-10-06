package com.solafides.gestio_usuaris_hackato.application.usecases.export_tasks;

import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.TaskMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.export_tasks.exceptions.ExportallTasksException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Primary
@Service
@RequiredArgsConstructor
final class ExportAllTasksService implements ExportAllTasks {

    private final TaskRepository taskRepository;

    @Override
    public Flux<Task> exportAllTasks() {

        // We allow the possibility of the flux to be empty
        return taskRepository.findAll()

                .map(TaskMapper::mapToModel)

                .onErrorMap(ExportallTasksException::new);

    }

}
