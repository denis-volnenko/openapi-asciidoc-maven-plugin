package ru.volnenko.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Задача")
public class TaskDTO {

    @ApiModelProperty("Название")
    private String name;

    @ApiModelProperty("Описание")
    private String description;

    @ApiModelProperty("Версия")
    private Integer version;

    @ApiModelProperty("Дата создания")
    private Date created;

    @ApiModelProperty("Дата обновления")
    private Date updated;

}
