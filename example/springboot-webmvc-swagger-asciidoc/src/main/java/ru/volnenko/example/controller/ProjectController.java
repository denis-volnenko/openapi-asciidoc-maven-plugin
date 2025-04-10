package ru.volnenko.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.ProjectDTO;

import java.util.Collections;
import java.util.List;

@RestController
@Api(tags = {"Проект"})
@RequestMapping("/api/v1/project")
public class ProjectController {

    @ApiOperation("Получение всех проектов")
    @GetMapping(value = "/all", produces = "application/json")
    public List<ProjectDTO> selectAllProject() {
        return Collections.emptyList();
    }

    @ApiOperation("Получение проекта по Id")
    @GetMapping(value = "/one/{id}")
    public ProjectDTO selectOneProject(
//            @ApiParam(name = "Идентификатор")
            @PathVariable("id") String id
    ) {
        return new ProjectDTO();
    }

}
