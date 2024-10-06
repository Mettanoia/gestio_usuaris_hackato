package com.solafides.gestio_usuaris_hackato.adapter.data.mappers;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.UserEntity;
import com.solafides.gestio_usuaris_hackato.domain.User;

public final class UserMapper {

    public static User mapToModel(UserEntity userEntity) {
        return new User(userEntity.id(), userEntity.name(), userEntity.surname(), userEntity.email());
    }

    public static UserEntity mapToEntity(User user) {
        return new UserEntity(user.id(), user.name(), user.surname(), user.email());
    }

}
