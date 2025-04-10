package ru.volnenko.example.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.TaskDTO;

import java.util.Collections;
import java.util.List;

@RestController
@Api(tags = {"Задача"})
@RequestMapping("/api/v1/task")
public class TaskController {

    @GetMapping(value = "/all", produces = "application/json")
    public List<TaskDTO> selectAllTask() {
        return Collections.emptyList();
    }

}
