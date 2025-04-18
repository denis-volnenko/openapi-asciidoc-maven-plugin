package ru.volnenko.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.volnenko.example.model.ProjectDTO;
import ru.volnenko.example.model.ResultRecord;
import ru.volnenko.example.model.TaskDTO;

import java.util.Collections;
import java.util.List;

@RestController
@Api(tags = {"Задача"})
@RequestMapping("/api/v1/task")
public class TaskController {

    @ApiOperation("Получение всех задач")
    @GetMapping(value = "/all", produces = "application/json")
    public List<TaskDTO> selectAllTask() {
        return Collections.emptyList();
    }

    @ApiOperation("Получение задачи по Id")
    @GetMapping(value = "/one/{id}", produces = "application/json")
    public TaskDTO selectOneTask(
            @ApiParam("Идентификатор")
            @PathVariable("id") String id
    ) {
        return new TaskDTO();
    }

    @ApiOperation("Создание новой задачи")
    @PostMapping(value = "/one/{id}", produces = "application/json", consumes = "application/json")
    public ResultRecord createOneTask(
            @ApiParam("Идентификатор")
            @PathVariable("id") String id,
            @RequestBody TaskDTO taskDTO
    ) {
        return new ResultRecord();
    }

    @ApiOperation("Создание новых задач")
    @PostMapping(value = "/all", produces = "application/json", consumes = "application/json")
    public ResultRecord createAllTask(
            @RequestBody List<TaskDTO> tasks
    ) {
        return new ResultRecord();
    }

}
