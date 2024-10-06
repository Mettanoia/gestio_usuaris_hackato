package com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.TaskEntity;
import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.TaskMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.TaskRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.exceptions.GetTaskException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

class GetTaskServiceTest {

    private TaskRepository taskRepository;
    private GetTask getTaskService;
    private Task task;
    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        getTaskService = new GetTaskService(taskRepository);
        task = new Task(1L, "Task Name", "This is the task description.", 30);
        taskEntity = new TaskEntity(1L, "Task Name", "This is the task description.", 30);
    }

    @Test
    void getTask_happyPath() {

        when(taskRepository.findById(anyLong())).thenReturn(just(taskEntity));

        Mono<Task> result = getTaskService.getTask(1L);

        StepVerifier.create(result)
                .expectNextMatches(task -> task.name().equals("Task Name") && task.maxCapacity() == 30)
                .verifyComplete();

        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void getTask_emptyMono() {

        when(taskRepository.findById(anyLong())).thenReturn(empty());

        Mono<Task> result = getTaskService.getTask(1L);

        StepVerifier.create(result)
                .expectError(GetTaskException.class)
                .verify();

        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void getTask_databaseException() {

        when(taskRepository.findById(anyLong())).thenReturn(error(new RuntimeException("Database error")));

        Mono<Task> result = getTaskService.getTask(1L);

        StepVerifier.create(result)
                .expectError(GetTaskException.class)
                .verify();

        verify(taskRepository, times(1)).findById(anyLong());
    }

}
