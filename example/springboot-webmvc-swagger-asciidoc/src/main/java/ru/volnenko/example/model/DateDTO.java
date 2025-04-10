package ru.volnenko.example.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Класс для передачи даты и времени")
public class DateDTO {

    @ApiModelProperty("Дата/Время")
    private Date date;

}
