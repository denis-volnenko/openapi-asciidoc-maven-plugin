package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IRoot;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Root implements IRoot {

    private String openapi;

    private String swagger;

    private Info info;

    private List<Server> servers;

    private Map<String, Map<String, Operation>> paths;

    private Components components;

    @Override
    public String openapi() {
        return openapi;
    }

    @Override
    public String swagger() {
        return swagger;
    }

    @Override
    public Info info() {
        return info;
    }

    @Override
    public List<Server> servers() {
        return servers;
    }

    @Override
    public Map<String, Map<String, Operation>> paths() {
        return paths;
    }

    @Override
    public Components components() {
        return components;
    }

}
