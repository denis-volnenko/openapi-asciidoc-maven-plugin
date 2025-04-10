package ru.volnenko.example.example.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@Schema(description = "Класс для передачи даты и времени")
public class DateDTO {

    @Schema(description = "Дата и время")
    private Date date;

    public DateDTO() {
    }

    public DateDTO(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
