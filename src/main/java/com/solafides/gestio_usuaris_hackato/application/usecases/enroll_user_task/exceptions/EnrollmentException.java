package com.solafides.gestio_usuaris_hackato.application.usecases.enroll_user_task.exceptions;

public class EnrollmentException extends RuntimeException{

    public EnrollmentException(String s) {
        super(s);
    }

    public EnrollmentException(Throwable e) {
        super(e);
    }

}
