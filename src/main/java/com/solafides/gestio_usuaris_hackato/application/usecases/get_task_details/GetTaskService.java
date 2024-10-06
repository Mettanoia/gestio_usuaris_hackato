package com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details;

import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.TaskMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.UserMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.exceptions.GetTaskException;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.GetUser;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.exceptions.GetUserException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import com.solafides.gestio_usuaris_hackato.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Primary
@Service
@RequiredArgsConstructor
public final class GetTaskService implements GetTask {

    private final TaskRepository taskRepository;

    @Override
    public Mono<Task> getTask(Long userId) {

        return taskRepository.findById(userId)

                // If the repository returns an empty mono then switch to error
                .switchIfEmpty(error(new GetTaskException("The task repository returned an empty mono.")))

                // Map the returned entity to the model
                .map(TaskMapper::mapToModel)

                // In case of any error, including the empty chain, map to CreateNewUserException
                .onErrorMap(GetTaskException::new);

    }

}
