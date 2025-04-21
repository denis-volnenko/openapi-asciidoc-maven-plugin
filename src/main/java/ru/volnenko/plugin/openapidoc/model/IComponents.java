package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Schema;

import java.util.Map;

public interface IComponents {

    Map<String, Schema> getSchemas();

    void setSchemas(Map<String, Schema> schemas);

    Map<String, Schema> schemas();

}
