package ru.volnenko.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.volnenko.example.model.DateDTO;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@RestController
public class DateController {

    @GetMapping("/date")
    public DateDTO date() {
        return new DateDTO(new Date());
    }

}
