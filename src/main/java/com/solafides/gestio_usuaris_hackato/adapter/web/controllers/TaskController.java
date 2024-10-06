package com.solafides.gestio_usuaris_hackato.adapter.web.controllers;

import com.solafides.gestio_usuaris_hackato.adapter.web.requests.ImportTasksRequest;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task.CreateNewTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task.expections.CreateNewTaskException;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.EnrollUserInTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.exceptions.EnrollmentException;
import com.solafides.gestio_usuaris_hackato.application.usecases.export_tasks.ExportAllTasks;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.GetTask;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.exceptions.GetTaskException;
import com.solafides.gestio_usuaris_hackato.application.usecases.import_tasks.ImportAllTasks;
import com.solafides.gestio_usuaris_hackato.domain.Task;
import com.solafides.gestio_usuaris_hackato.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static reactor.core.publisher.Mono.defer;
import static reactor.core.publisher.Mono.just;

@Configuration
@RequiredArgsConstructor
class TaskController {

    private final CreateNewTask createNewTaskService;
    private final GetTask getTaskService;
    private final EnrollUserInTask enrollUserInTaskService;
    private final ExportAllTasks exportAllTasksService;
    private final ImportAllTasks importAllTasksService;

    @Bean
    public RouterFunction<ServerResponse> taskEndpoints() {

        return route(POST("/appActivitats/task"), this::createNewTask)
                .andRoute(GET("/appActivitats/task/{id}"), this::getTaskDetails)
                .andRoute(POST("/appActivitats/task/{id}/enroll"), this::enrollUserInTask)
                .andRoute(GET("appActivitats/tasks"), this::exportAllTasks)
                .andRoute(POST("appActivitats/tasks"), this::importAllTasks);


    }

    private Mono<ServerResponse> importAllTasks(ServerRequest request) {

        return request.bodyToMono(ImportTasksRequest.class)

                .flatMap(importAllTasksService::importAllTasks)

                .then(ok().build())

                // Generic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(e.getMessage(), String.class));

    }

    private Mono<ServerResponse> exportAllTasks(ServerRequest request) {

        return exportAllTasksService.exportAllTasks()

                .collectList()

                .flatMap(tasks -> ok().body(just(tasks), Task.class))

                // Generic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(e.getMessage(), String.class));

    }

    private Mono<ServerResponse> enrollUserInTask(ServerRequest request) {

        return request.bodyToMono(User.class)

                .flatMap(user -> enrollUserInTaskService.enrollUserInTask(user.id(), Long.valueOf(request.pathVariable("id"))))

                .then(defer(() -> ok().body(just("User successfully enrolled."), String.class)))

                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(e, EnrollmentException.class));

    }

    private Mono<ServerResponse> getTaskDetails(ServerRequest request) {

        return getTaskService.getTask(Long.valueOf(request.pathVariable("id")))

                // Basic updated response
                // TODO maybe we should format the user details better. Kyrie Eleison.
                .flatMap(task -> ok().body(just(task), Task.class))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just(e), GetTaskException.class));

    }

    private Mono<ServerResponse> createNewTask(ServerRequest request) {

        return request.bodyToMono(Task.class)

                // Call create new user use case
                .flatMap(createNewTaskService::createNewTask)

                // Basic created response
                .flatMap(task -> status(CREATED).body(just(task), Task.class))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just(e), CreateNewTaskException.class));

    }

}
