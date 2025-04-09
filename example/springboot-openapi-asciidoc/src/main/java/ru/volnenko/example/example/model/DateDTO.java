package ru.volnenko.example.example.model;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

public class DateDTO {

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
