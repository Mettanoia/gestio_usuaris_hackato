package com.solafides.gestio_usuaris_hackato.adapter.data.mappers;

import com.solafides.gestio_usuaris_hackato.adapter.data.entities.TaskEntity;
import com.solafides.gestio_usuaris_hackato.domain.Task;

public final class TaskMapper {

    public static Task mapToModel(TaskEntity taskEntity) {
        return new Task(taskEntity.id(), taskEntity.name(), taskEntity.description(), taskEntity.maxCapacity());
    }

    public static TaskEntity mapToEntity(Task task) {
        return new TaskEntity(task.id(), task.name(), task.description(), task.maxCapacity());
    }

}
