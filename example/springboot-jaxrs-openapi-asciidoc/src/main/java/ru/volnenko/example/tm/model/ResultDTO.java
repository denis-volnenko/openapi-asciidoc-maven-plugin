package ru.volnenko.example.tm.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Результат выполнения операции")
public final class ResultDTO {

    @Schema(description = "Флаг успешного выполнения")
    private Boolean success = true;

}
