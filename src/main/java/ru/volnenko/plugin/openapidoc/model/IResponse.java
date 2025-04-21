package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Content;

import java.util.Map;

public interface IResponse {
    String description();

    Map<String, Content> content();

    String getDescription();

    Map<String, Content> getContent();

    void setDescription(String description);

    void setContent(Map<String, Content> content);
}
