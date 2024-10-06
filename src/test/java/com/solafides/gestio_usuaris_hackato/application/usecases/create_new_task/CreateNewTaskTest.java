package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.TaskEntity;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.exceptions.CreateNewUserException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

class CreateNewTaskServiceTest {

    private TaskRepository taskRepository;
    private CreateNewTask createNewTaskService;
    private Task task;
    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        createNewTaskService = new CreateNewTaskService(taskRepository);
        task = new Task(null, "Task Name", "This is the task description.", 30);
        taskEntity = new TaskEntity(1L, "Task Name", "This is the task description.", 30);
    }

    @Test
    void createNewTask_happyPath() {

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(just(taskEntity));

        Mono<Task> result = createNewTaskService.createNewTask(task);

        StepVerifier.create(result)
                .expectNextMatches(task -> task.name().equals("Task Name") && task.maxCapacity() == 30)
                .verifyComplete();

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void createNewTask_emptyMono() {

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(empty());

        Mono<Task> result = createNewTaskService.createNewTask(task);

        StepVerifier.create(result)
                .expectError(CreateNewUserException.class)
                .verify();

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void createNewTask_databaseException() {

        when(taskRepository.save(any(TaskEntity.class))).thenReturn(error(new RuntimeException("Database error")));

        Mono<Task> result = createNewTaskService.createNewTask(task);

        StepVerifier.create(result)
                .expectError(CreateNewUserException.class)
                .verify();

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }
}
