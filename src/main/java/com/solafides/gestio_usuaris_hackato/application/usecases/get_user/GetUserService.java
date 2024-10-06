package com.solafides.gestio_usuaris_hackato.application.usecases.get_user;

import com.solafides.gestio_usuaris_hackato.adapter.data.mappers.UserMapper;
import com.solafides.gestio_usuaris_hackato.adapter.data.repositories.UserRepository;
import com.solafides.gestio_usuaris_hackato.application.usecases.get_user.exceptions.GetUserException;
import com.solafides.gestio_usuaris_hackato.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Primary
@Service
@RequiredArgsConstructor
public final class GetUserService implements GetUser {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUser(Long userId) {

        return userRepository.findById(userId)

                // If the repository returns an empty mono then switch to error
                .switchIfEmpty(error(new GetUserException("The user repository returned an empty mono.")))

                // Map the returned entity to the model
                .map(UserMapper::mapToModel)

                // In case of any error, including the empty chain, map to CreateNewUserException
                .onErrorMap(GetUserException::new);

    }

}
