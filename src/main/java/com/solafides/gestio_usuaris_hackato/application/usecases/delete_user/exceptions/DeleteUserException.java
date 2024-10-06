package com.solafides.gestio_usuaris_hackato.application.usecases.delete_user.exceptions;

public class DeleteUserException extends RuntimeException{

    public DeleteUserException(String s) {
        super("Error during the deletion of the user: " + s);
    }

    public DeleteUserException(Throwable e) {
        super(e);
    }

}
