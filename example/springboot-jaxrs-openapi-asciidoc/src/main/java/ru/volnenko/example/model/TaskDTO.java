package ru.volnenko.example.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * @author Denis Volnenko
 */

@Schema(description = "Задача")
public class TaskDTO {

    @Schema(description = "Название")
    private String name;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Дата создания")
    private Date created;

    @Schema(description = "Дата обновления")
    private Date updated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
