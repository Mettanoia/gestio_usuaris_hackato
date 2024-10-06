package com.solafides.gestio_usuaris_hackato.adapter.web.controllers;

import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.CreateNewUser;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.exceptions.CreateNewUserException;
import com.solafides.gestio_usuaris_hackato.application.usecases.delete_user.DeleteUser;
import com.solafides.gestio_usuaris_hackato.application.usecases.delete_user.exceptions.DeleteUserException;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.GetUser;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.exceptions.GetUserException;
import com.solafides.gestio_usuaris_hackato.application.usecases.update_user.UpdateUser;
import com.solafides.gestio_usuaris_hackato.application.usecases.update_user.exceptions.UpdateUserException;
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
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static reactor.core.publisher.Mono.just;

@Configuration
@RequiredArgsConstructor
class UserController {

    private final CreateNewUser createNewUserService;
    private final UpdateUser updateUserService;
    private final GetUser getUserService;
    private final DeleteUser deleteUserService;

    @Bean
    public RouterFunction<ServerResponse> userEndpoints() {

        return route(POST("/appActivitats/user"), this::createNewUser)
                .andRoute(PUT("/appActivitats/users/{id}"), this::updateUser)
                .andRoute(DELETE("/appActivitats/users/{id}"), this::deleteUser)
                .andRoute(GET("/appActivitats/users/{id}"), this::getUserDetails);

    }

    private Mono<ServerResponse> deleteUser(ServerRequest request) {

        return deleteUserService.deleteUser(Long.valueOf(request.pathVariable("id")))

                .then(Mono.defer(() -> ok().build()))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just(e), DeleteUserException.class));

    }

    private Mono<ServerResponse> getUserDetails(ServerRequest request) {

        return getUserService.getUser(Long.valueOf(request.pathVariable("id")))

                // Basic updated response
                // TODO maybe we should format the user details better. Kyrie Eleison.
                .flatMap(user -> ok().body(just(user), User.class))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just(e), GetUserException.class));

    }

    private Mono<ServerResponse> updateUser(ServerRequest request) {

        return request.bodyToMono(User.class)

                // Add the id to the request model
                .map(user -> new User(Long.valueOf(request.pathVariable("id")), user.name(), user.surname(), user.email()))

                // Call update user use case
                .flatMap(updateUserService::updateUser)

                // Basic updated response
                .flatMap(user -> ok().body(just(user), User.class))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just(e), UpdateUserException.class));

    }

    private Mono<ServerResponse> createNewUser(ServerRequest request) {

        return request.bodyToMono(User.class)

                // Call create new user use case
                .flatMap(createNewUserService::createNewUser)

                // Basic created response
                .flatMap(user -> status(CREATED).body(just(user), User.class))

                // Basic error handling
                .onErrorResume(e -> status(INTERNAL_SERVER_ERROR).body(just("User Creation failed: " + e.getMessage()), String.class));

    }

}
