package com.solafides.gestio_usuaris_hackato.adapter.web.requests;

import com.solafides.gestio_usuaris_hackato.domain.Task;

import java.util.List;

public record ImportTasksRequest(List<Task> tasks) {
}
