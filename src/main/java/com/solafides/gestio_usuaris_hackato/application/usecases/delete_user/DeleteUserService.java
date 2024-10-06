package com.solafides.gestio_usuaris_hackato.application.usecases.delete_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.delete_user.exceptions.DeleteUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Primary
@Service
@RequiredArgsConstructor
final class DeleteUserService implements DeleteUser {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> deleteUser(Long userId) {

        return userRepository.deleteById(userId)

                // In case of any error, including the empty chain, map to CreateNewUserException
                .onErrorMap(DeleteUserException::new);

    }

}
