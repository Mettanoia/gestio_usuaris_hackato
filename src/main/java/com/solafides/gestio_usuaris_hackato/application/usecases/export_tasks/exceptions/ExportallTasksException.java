package com.solafides.gestio_usuaris_hackato.application.usecases.export_tasks.exceptions;

public class ExportallTasksException extends RuntimeException{

    public ExportallTasksException(String s) {
        super("Error during the creation of the task: " + s);
    }

    public ExportallTasksException(Throwable e) {
        super(e);
    }

}
