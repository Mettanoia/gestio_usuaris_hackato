package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task;

import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.TaskMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.exceptions.CreateNewUserException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Primary
@Service
@RequiredArgsConstructor
public final class CreateNewTaskService implements CreateNewTask {

    private final TaskRepository taskRepository;

    @Override
    public Mono<Task> createNewTask(Task task) {

        // Save the new user represented by the user model
        return taskRepository.save(TaskMapper.mapToEntity(task))

                // If the repository returns an empty mono then switch to error
                .switchIfEmpty(error(new CreateNewUserException("The user repository returned an empty mono.")))

                // Map the returned entity to the model
                .map(TaskMapper::mapToModel)

                // In case of any error, including the empty chain, map to CreateNewUserException
                .onErrorMap(CreateNewUserException::new);

    }

}
