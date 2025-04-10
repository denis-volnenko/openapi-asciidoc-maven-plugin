package ru.volnenko.example.tm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.volnenko.example.tm.model.ProjectDTO;
import ru.volnenko.example.tm.model.ResultDTO;

import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;

@Path("/api/v1/project")
@Produces("application/json")
@Tag(name = "ProjectController", description = "Управление проектами")
public final class ProjectController {

    @GET
    @Path("/one/{id}")
    @Operation(
            summary = "Получение проекта",
            description = "Получение проекта по ID"
    )
    public ProjectDTO selectOneProject(
            @Parameter(description = "Идентификатор")
            @PathParam("id") String id
    ) {
        return new ProjectDTO();
    }

    @GET
    @Path("/all")
    @Operation(
            summary = "Получение всех проектов",
            description = "Получение всех проектов"
    )
    public List<ProjectDTO> selectAllProject() {
        return Collections.emptyList();
    }

    @DELETE
    @Path("/one/{id}")
    public ResultDTO deleteOne(
            @Parameter(description = "Идентификатор")
            @PathParam("id") String id
    ) {
        return new ResultDTO();
    }
}
