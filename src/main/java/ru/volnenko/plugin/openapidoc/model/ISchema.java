package ru.volnenko.plugin.openapidoc.model;

public interface ISchema {

    void setExample(Object example);

    void setItems(ru.volnenko.plugin.openapidoc.model.impl.Schema items);

    void setProperties(java.util.Map<String, ru.volnenko.plugin.openapidoc.model.impl.Schema> properties);

    void setRequired(java.util.List<String> required);

    boolean referenced();
}
