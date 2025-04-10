package ru.volnenko.example.tm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.volnenko.example.tm.model.ProjectDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

@Path("/api/v1/project")
@Produces("application/json")
@Tag(name = "ProjectController", description = "Управление проектами")
public final class ProjectController {

    @GET
    @Path("/all")
    @Operation(
            summary = "Получение всех проектов",
            description = "Получение всех проектов"
    )
    public List<ProjectDTO> selectAllProject() {
        return Collections.emptyList();
    }

}
