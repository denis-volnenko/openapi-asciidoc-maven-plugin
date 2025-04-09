package ru.volnenko.example.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
