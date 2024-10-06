package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user;

import com.solafides.gestio_usuaris_hackato.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@FunctionalInterface
public interface CreateNewUser {

    Mono<User> createNewUser(User user);

}
