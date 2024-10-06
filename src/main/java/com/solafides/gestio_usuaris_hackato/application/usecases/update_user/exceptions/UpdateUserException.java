package com.solafides.gestio_usuaris_hackato.application.usecases.update_user.exceptions;

public class UpdateUserException extends RuntimeException{

    public UpdateUserException(String s) {
        super("Error during the update of the user: " + s);
    }

    public UpdateUserException(Throwable e) {
        super(e);
    }

}
