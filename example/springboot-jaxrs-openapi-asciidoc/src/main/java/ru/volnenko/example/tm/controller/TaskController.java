package ru.volnenko.example.tm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.volnenko.example.tm.model.TaskDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

@Path("/api/v1/task")
@Produces("application/json")
@Tag(name = "TaskController", description = "Управление задачами")
public final class TaskController {

    @GET
    @Path("/one/{id}")
    @Operation(
            summary = "Получение задачи",
            description = "Получение задачи по Id"
    )
    public TaskDTO selectOneTask(
            @Parameter(name = "id", description = "Идентификатор")
            @PathParam("id") String id
    ) {
        return new TaskDTO();
    }

    @GET
    @Path("/all")
    @Operation(
            summary = "Получение всех задач",
            description = "Получение всех задач"
    )
    public List<TaskDTO> selectAllTask() {
        return Collections.emptyList();
    }

}
