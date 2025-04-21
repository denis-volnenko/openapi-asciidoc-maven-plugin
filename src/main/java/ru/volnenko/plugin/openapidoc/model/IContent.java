package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Schema;

public interface IContent {

    Schema schema();

    Schema getSchema();

    void setSchema(Schema schema);

}
