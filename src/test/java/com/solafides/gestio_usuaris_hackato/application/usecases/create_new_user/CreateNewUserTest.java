package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.UserEntity;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.exceptions.CreateNewUserException;
import com.solafides.gestio_usuaris_hackato.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

class CreateNewUserTest {

    private UserRepository userRepository;
    private CreateNewUser createNewUser;
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {

        userRepository = mock(UserRepository.class);
        createNewUser = new CreateNewUserService(userRepository);
        user = new User(null,"John", "Doe", "john.doe@example.com");
        userEntity = new UserEntity(1L, "John", "Doe", "john.doe@example.com");

    }

    @Test
    void createNewUser_happyPath() {

        when(userRepository.save(any(UserEntity.class))).thenReturn(just(userEntity));

        Mono<User> result = createNewUser.createNewUser(user);


        StepVerifier.create(result)
                .expectNextMatches(user -> user.name().equals("John") && user.surname().equals("Doe"))
                .verifyComplete();

        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

    @Test
    void createNewUser_emptyMono() {

        when(userRepository.save(any(UserEntity.class))).thenReturn(empty());

        Mono<User> result = createNewUser.createNewUser(user);

        StepVerifier.create(result)
                .expectError(CreateNewUserException.class)
                .verify();

        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

    @Test
    void createNewUser_databaseException() {

        when(userRepository.save(any(UserEntity.class))).thenReturn(error(new RuntimeException("Database error")));

        Mono<User> result = createNewUser.createNewUser(user);

        StepVerifier.create(result)
                .expectError(CreateNewUserException.class)
                .verify();

        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

}
