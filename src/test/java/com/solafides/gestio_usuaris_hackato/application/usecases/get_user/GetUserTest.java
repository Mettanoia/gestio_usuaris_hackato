package com.solafides.gestio_usuaris_hackato.application.usecases.get_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.UserEntity;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.exceptions.GetUserException;
import com.solafides.gestio_usuaris_hackato.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

class GetUserServiceTest {

    private UserRepository userRepository;
    private GetUser getUserService;
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getUserService = new GetUserService(userRepository);
        user = new User(1L, "John", "Doe", "john.doe@example.com");
        userEntity = new UserEntity(1L, "John", "Doe", "john.doe@example.com");
    }

    @Test
    void getUser_happyPath() {

        when(userRepository.findById(anyLong())).thenReturn(just(userEntity));

        Mono<User> result = getUserService.getUser(1L);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.name().equals("John") && user.surname().equals("Doe"))
                .verifyComplete();

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUser_emptyMono() {

        when(userRepository.findById(anyLong())).thenReturn(empty());

        Mono<User> result = getUserService.getUser(1L);

        StepVerifier.create(result)
                .expectError(GetUserException.class)
                .verify();

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUser_databaseException() {

        when(userRepository.findById(anyLong())).thenReturn(error(new RuntimeException("Database error")));

        Mono<User> result = getUserService.getUser(1L);

        StepVerifier.create(result)
                .expectError(GetUserException.class)
                .verify();

        verify(userRepository, times(1)).findById(anyLong());
    }
}
