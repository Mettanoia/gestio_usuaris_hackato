package com.solafides.gestio_usuaris_hackato.application.usecases.get_task_details.exceptions;

public class GetTaskException extends RuntimeException{

    public GetTaskException(String s) {
        super("Error during the retrieval of the task: " + s);
    }

    public GetTaskException(Throwable e) {
        super(e);
    }

}
