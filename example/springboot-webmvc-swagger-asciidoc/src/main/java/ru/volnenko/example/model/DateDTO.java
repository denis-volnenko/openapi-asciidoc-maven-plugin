package ru.volnenko.example.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@ApiModel(description = "Класс для передачи даты и времени")
public class DateDTO {

    @ApiModelProperty("Дата/Время")
    private Date date;

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
