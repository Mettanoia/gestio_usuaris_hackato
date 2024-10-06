package com.solafides.gestio_usuaris_hackato.application.usecases.get_user.exceptions;

public class GetUserException extends RuntimeException{

    public GetUserException(String s) {
        super("Error during the retrieval of the user: " + s);
    }

    public GetUserException(Throwable e) {
        super(e);
    }

}
