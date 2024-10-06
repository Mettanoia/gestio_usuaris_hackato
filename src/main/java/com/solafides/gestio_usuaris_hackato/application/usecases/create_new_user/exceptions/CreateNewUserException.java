package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_user.exceptions;

public class CreateNewUserException extends RuntimeException{

    public CreateNewUserException(String s) {
        super("Error during the creation of the user: " + s);
    }

    public CreateNewUserException(Throwable e) {
        super(e);
    }

}
