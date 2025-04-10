package ru.volnenko.example.tm.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Задача")
public final class TaskDTO {

    @Schema(description = "Название")
    private String name;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Дата создания")
    private Date created;

    @Schema(description = "Дата обновления")
    private Date updated;

    @Schema(description = "Версия")
    private Integer version;

}
