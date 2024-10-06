package com.solafides.gestio_usuaris_hackato.application.usecases.get_user;

import com.solafides.gestio_usuaris_hackato.domain.User;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface GetUser {
    Mono<User> getUser(Long userId);
}
