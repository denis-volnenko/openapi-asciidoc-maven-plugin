package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IRequestBody;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class RequestBody implements IRequestBody {

    private Boolean required;

    private Map<String, Content> content;

    public Boolean required() {
        return required;
    }

    public Map<String, Content> content() {
        return content;
    }

}
