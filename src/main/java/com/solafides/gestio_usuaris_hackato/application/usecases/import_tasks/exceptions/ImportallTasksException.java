package com.solafides.gestio_usuaris_hackato.application.usecases.import_tasks.exceptions;

public class ImportallTasksException extends RuntimeException{

    public ImportallTasksException(String s) {
        super("Error during the creation of the task: " + s);
    }

    public ImportallTasksException(Throwable e) {
        super(e);
    }

}
