package com.solafides.gestio_usuaris_hackato.application.usecases.update_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.UserEntity;
import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.UserMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.update_user.exceptions.UpdateUserException;
import com.solafides.gestio_usuaris_hackato.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateUserTest {

    private UserRepository userRepository;
    private UpdateUserService updateUserService;

    @BeforeEach
    void setUp() {

        userRepository = mock(UserRepository.class);
        updateUserService = new UpdateUserService(userRepository);

    }

    @Test
    void testUpdateUserSuccess() {

        User user = new User(1L,"John", "Doe", "john.doe@example.com");
        UserEntity userEntity = UserMapper.mapToEntity(user);

        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));

        Mono<User> result = updateUserService.updateUser(user);

        User returnedUser = result.block();
        assertNotNull(returnedUser, "Returned user should not be null");
        assertEquals(user.name(), returnedUser.name(), "Name should match");
        assertEquals(user.surname(), returnedUser.surname(), "Surname should match");
        assertEquals(user.email(), returnedUser.email(), "Email should match");

    }

    @Test
    void testUpdateUserEmptyMono() {

        User user = new User(2L,"Jane", "Doe", "jane.doe@example.com");

        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.empty());

        Mono<User> result = updateUserService.updateUser(user);

        UpdateUserException exception = assertThrows(UpdateUserException.class, result::block);

    }

    @Test
    void testUpdateUserGenericError() {

        User user = new User(3L,"Alice", "Smith", "alice.smith@example.com");

        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.error(new RuntimeException("Database error")));

        Mono<User> result = updateUserService.updateUser(user);

        UpdateUserException exception = assertThrows(UpdateUserException.class, result::block);

    }

}
