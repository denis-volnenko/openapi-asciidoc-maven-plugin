package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Schema;

public interface IParameter {
    String in();

    String name();

    String description();

    Boolean required();

    Schema schema();

    String getIn();

    String getName();

    String getDescription();

    Boolean getRequired();

    Schema getSchema();

    void setIn(String in);

    void setName(String name);

    void setDescription(String description);

    void setRequired(Boolean required);

    void setSchema(Schema schema);
}
