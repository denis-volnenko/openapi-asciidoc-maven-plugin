package ru.volnenko.example.tm.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Класс для передачи даты и времени")
public final class DateDTO {

    @Schema(description = "Дата и время")
    private Date date;

}
