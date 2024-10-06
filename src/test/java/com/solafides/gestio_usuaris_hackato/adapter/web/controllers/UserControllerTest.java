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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class UserControllerTest {

    private CreateNewUser createNewUserService;
    private UpdateUser updateUserService;
    private GetUser getUserService;
    private DeleteUser deleteUserService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        // Mock the use cases
        createNewUserService = mock(CreateNewUser.class);
        updateUserService = mock(UpdateUser.class);
        getUserService = mock(GetUser.class);
        deleteUserService = mock(DeleteUser.class);

        // Create the controller with the mocked use cases
        UserController userController = new UserController(
                createNewUserService,
                updateUserService,
                getUserService,
                deleteUserService
        );

        // Bind the controller's router function to WebTestClient
        webTestClient = WebTestClient.bindToRouterFunction(userController.userEndpoints()).build();
    }

    @Test
    void createNewUser_success() {
        // Arrange
        User user = new User(null, "John", "Doe", "john.doe@example.com");
        User createdUser = new User(1L, "John", "Doe", "john.doe@example.com");

        when(createNewUserService.createNewUser(user)).thenReturn(Mono.just(createdUser));

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .isEqualTo(createdUser);

        verify(createNewUserService, times(1)).createNewUser(user);
    }

    @Test
    void createNewUser_failure() {
        // Arrange
        User user = new User(null, "John", "Doe", "john.doe@example.com");

        when(createNewUserService.createNewUser(user))
                .thenReturn(Mono.error(new CreateNewUserException("Creation failed")));

        // Act & Assert
        webTestClient.post()
                .uri("/appActivitats/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(createNewUserService, times(1)).createNewUser(user);
    }

    @Test
    void updateUser_success() {
        // Arrange
        User user = new User(null, "Jane", "Doe", "jane.doe@example.com");
        User updatedUser = new User(1L, "Jane", "Doe", "jane.doe@example.com");

        when(updateUserService.updateUser(updatedUser)).thenReturn(Mono.just(updatedUser));

        // Act & Assert
        webTestClient.put()
                .uri("/appActivitats/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(updatedUser);

        verify(updateUserService, times(1)).updateUser(updatedUser);
    }

    @Test
    void updateUser_failure() {
        // Arrange
        User user = new User(null, "Jane", "Doe", "jane.doe@example.com");
        User updatedUser = new User(1L, "Jane", "Doe", "jane.doe@example.com");

        when(updateUserService.updateUser(updatedUser))
                .thenReturn(Mono.error(new UpdateUserException("Update failed")));

        // Act & Assert
        webTestClient.put()
                .uri("/appActivitats/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(updateUserService, times(1)).updateUser(updatedUser);
    }

    @Test
    void getUserDetails_success() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe@example.com");

        when(getUserService.getUser(1L)).thenReturn(Mono.just(user));

        // Act & Assert
        webTestClient.get()
                .uri("/appActivitats/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);

        verify(getUserService, times(1)).getUser(1L);
    }

    @Test
    void getUserDetails_failure() {
        // Arrange
        when(getUserService.getUser(1L))
                .thenReturn(Mono.error(new GetUserException("User not found")));

        // Act & Assert
        webTestClient.get()
                .uri("/appActivitats/users/1")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(getUserService, times(1)).getUser(1L);
    }

    @Test
    void deleteUser_success() {
        // Arrange
        when(deleteUserService.deleteUser(1L)).thenReturn(Mono.empty());

        // Act & Assert
        webTestClient.delete()
                .uri("/appActivitats/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(deleteUserService, times(1)).deleteUser(1L);
    }

    @Test
    void deleteUser_failure() {
        // Arrange
        when(deleteUserService.deleteUser(1L))
                .thenReturn(Mono.error(new DeleteUserException("Deletion failed")));

        // Act & Assert
        webTestClient.delete()
                .uri("/appActivitats/users/1")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(deleteUserService, times(1)).deleteUser(1L);
    }
}
