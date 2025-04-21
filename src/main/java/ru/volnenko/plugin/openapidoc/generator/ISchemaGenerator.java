package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Schema;

public interface ISchemaGenerator extends IGenerator {

    @NonNull
    ISchemaGenerator serviceName(String serviceName);

    @NonNull
    ISchemaGenerator schema(Schema schema);

    @NonNull
    ISchemaGenerator model(String model);

}
