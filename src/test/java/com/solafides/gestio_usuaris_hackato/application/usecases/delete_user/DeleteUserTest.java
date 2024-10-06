package com.solafides.gestio_usuaris_hackato.application.usecases.delete_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.delete_user.exceptions.DeleteUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.empty;

class DeleteUserServiceTest {

    private UserRepository userRepository;
    private DeleteUser deleteUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        deleteUserService = new DeleteUserService(userRepository);
    }

    @Test
    void deleteUser_happyPath() {

        when(userRepository.deleteById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> result = deleteUserService.deleteUser(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteUser_databaseException() {

        when(userRepository.deleteById(anyLong())).thenReturn(error(new RuntimeException("Database error")));

        Mono<Void> result = deleteUserService.deleteUser(1L);

        StepVerifier.create(result)
                .expectError(DeleteUserException.class)
                .verify();

        verify(userRepository, times(1)).deleteById(anyLong());
    }

}
