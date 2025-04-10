package ru.volnenko.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    private String name;

    private String description;

    private Integer version;

    private Date created;

    private Date updated;

}
