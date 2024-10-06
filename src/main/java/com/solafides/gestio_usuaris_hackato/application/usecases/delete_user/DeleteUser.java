package com.solafides.gestio_usuaris_hackato.application.usecases.delete_user;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteUser {
    Mono<Void> deleteUser(Long userId);
}
