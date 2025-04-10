package ru.volnenko.example.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.volnenko.example.example.model.DateDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

/**
 * @author Denis Volnenko
 */

@Path("/datetime")
@Produces("application/json")
@Tag(name = "DateController", description = "Операции с датой и временем")
public class DateController {

    @GET
    @Path("/current")
    @Operation(
            summary = "Текущая дата и время",
            description = "Получение с сервера текущей даты и времени"
    )
    public DateDTO getDate() {
        return new DateDTO(new Date());
    }

}
