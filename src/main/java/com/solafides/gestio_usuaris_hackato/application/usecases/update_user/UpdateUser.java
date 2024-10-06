package com.solafides.gestio_usuaris_hackato.application.usecases.update_user;

import com.solafides.gestio_usuaris_hackato.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateUser {
    Mono<User> updateUser(User user);
}
