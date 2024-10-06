package com.solafides.gestio_usuaris_hackato.adapter.web.controllers;

import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task.CreateNewTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task.expections.CreateNewTaskException;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.EnrollUserInTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.exceptions.EnrollmentException;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.GetTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.exceptions.GetTaskException;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import com.solafides.gestio_usuaris_hackato.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class TaskControllerTest {

    private CreateNewTask createNewTaskService;
    private GetTask getTaskService;
    private EnrollUserInTask enrollUserInTaskService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        // Mock the use cases
        createNewTaskService = mock(CreateNewTask.class);
        getTaskService = mock(GetTask.class);
        enrollUserInTaskService = mock(EnrollUserInTask.class);

        // Create the controller with the mocked use cases
        TaskController taskController = new TaskController(
                createNewTaskService,
                getTaskService,
                enrollUserInTaskService,
                null,
                null
        );

        // Bind the controller's router function to WebTestClient
        webTestClient = WebTestClient.bindToRouterFunction(taskController.taskEndpoints()).build();
    }

    @Test
    void createNewTask_success() {
        // Arrange
        Task task = new Task(null, "Task Name", "Task Description", 30);
        Task createdTask = new Task(1L, "Task Name", "Task Description", 30);

        when(createNewTaskService.createNewTask(task)).thenReturn(Mono.just(createdTask));

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/task")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Task.class)
                .isEqualTo(createdTask);

        verify(createNewTaskService, times(1)).createNewTask(task);
    }

    @Test
    void createNewTask_failure() {
        // Arrange
        Task task = new Task(null, "Task Name", "Task Description", 30);

        when(createNewTaskService.createNewTask(task))
                .thenReturn(Mono.error(new CreateNewTaskException("Creation failed")));

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/task")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(createNewTaskService, times(1)).createNewTask(task);
    }

    @Test
    void getTaskDetails_success() {
        // Arrange
        Task task = new Task(1L, "Task Name", "Task Description", 30);

        when(getTaskService.getTask(1L)).thenReturn(Mono.just(task));

        // Act & Assert
        webTestClient.get()
                .uri("/appActivitats/task/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .isEqualTo(task);

        verify(getTaskService, times(1)).getTask(1L);
    }

    @Test
    void getTaskDetails_failure() {
        // Arrange
        when(getTaskService.getTask(1L))
                .thenReturn(Mono.error(new GetTaskException("Task not found")));

        // Act & Assert
        webTestClient.get()
                .uri("/appActivitats/task/1")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(getTaskService, times(1)).getTask(1L);
    }

    @Test
    void enrollUserInTask_success() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe@example.com");

        when(enrollUserInTaskService.enrollUserInTask(user.id(), 1L)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/task/1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("User successfully enrolled.");

        verify(enrollUserInTaskService, times(1)).enrollUserInTask(user.id(), 1L);
    }

    @Test
    void enrollUserInTask_failure() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe@example.com");

        when(enrollUserInTaskService.enrollUserInTask(user.id(), 1L))
                .thenReturn(Mono.error(new EnrollmentException("Enrollment failed")));

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/task/1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(enrollUserInTaskService, times(1)).enrollUserInTask(user.id(), 1L);
    }
}
