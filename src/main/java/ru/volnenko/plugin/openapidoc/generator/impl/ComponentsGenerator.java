package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IComponentsGenerator;
import ru.volnenko.plugin.openapidoc.generator.ISchemaGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Components;
import ru.volnenko.plugin.openapidoc.model.impl.Schema;

public final class ComponentsGenerator implements IComponentsGenerator {

    @NonNull
    private final ISchemaGenerator schemaGenerator = new SchemaGenerator();

    private Components components;

    private String serviceName;

    @NonNull
    @Override
    public IComponentsGenerator components(final Components components) {
        this.components = components;
        return this;
    }

    @NonNull
    @Override
    public IComponentsGenerator serviceName(final String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    private void generate(
            @NonNull final StringBuilder stringBuilder,
            final String model,
            final String serviceName,
            final Schema schema
    ) {
        schemaGenerator
                .model(model)
                .schema(schema)
                .serviceName(serviceName)
                .append(stringBuilder);
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        if (components == null) return stringBuilder;
        if (components.getSchemas() == null) return stringBuilder;
        if (components.getSchemas().isEmpty()) return stringBuilder;
        for (final String model : components.getSchemas().keySet()) {
            generate(stringBuilder, model, serviceName, components.getSchemas().get(model));
        }
        return stringBuilder;
    }

}
