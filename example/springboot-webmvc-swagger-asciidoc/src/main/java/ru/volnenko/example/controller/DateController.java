package ru.volnenko.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.DateDTO;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@RestController
@Api(tags = {"Дата"})
@RequestMapping("/api/v1/datetime")
public class DateController {

    @ApiOperation("Получение текущей даты")
    @GetMapping(value = "/current", produces = "application/json")
    public DateDTO date() {
        return new DateDTO(new Date());
    }

}
