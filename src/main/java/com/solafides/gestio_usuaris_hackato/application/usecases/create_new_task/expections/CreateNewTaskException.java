package com.solafides.gestio_usuaris_hackato.application.usecases.create_new_task.expections;

public class CreateNewTaskException extends RuntimeException{

    public CreateNewTaskException(String s) {
        super("Error during the creation of the task: " + s);
    }

    public CreateNewTaskException(Throwable e) {
        super(e);
    }

}
