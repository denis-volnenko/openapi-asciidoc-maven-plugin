package ru.volnenko.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.DateDTO;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@RestController
@RequestMapping("/api/v1/datetime")
public class DateController {

    @GetMapping(value = "/current", produces = "application/json")
    public DateDTO date() {
        return new DateDTO(new Date());
    }

}
