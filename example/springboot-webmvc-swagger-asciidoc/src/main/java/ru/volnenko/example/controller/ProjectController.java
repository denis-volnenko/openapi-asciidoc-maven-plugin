package ru.volnenko.example.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.ProjectDTO;

import java.util.Collections;
import java.util.List;

@RestController
@Api(tags = {"Проект"})
@RequestMapping("/api/v1/project")
public class ProjectController {

    @GetMapping(value = "/all", produces = "application/json")
    public List<ProjectDTO> selectAllProject() {
        return Collections.emptyList();
    }

}
