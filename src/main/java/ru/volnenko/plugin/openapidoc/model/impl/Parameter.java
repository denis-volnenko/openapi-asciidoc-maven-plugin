package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IParameter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Parameter implements IParameter {

    private String in;

    private String name;

    private String description;

    private Boolean required;

    private Schema schema;

    @Override
    public String in() {
        return in;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Boolean required() {
        return required;
    }

    @Override
    public Schema schema() {
        return schema;
    }

}
