package ru.volnenko.example.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Результат выполнения")
public class ResultRecord {

    @ApiModelProperty("Флаг успешного выполнения")
    private Boolean success = true;

}
