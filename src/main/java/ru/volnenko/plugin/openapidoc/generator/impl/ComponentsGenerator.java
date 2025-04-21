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

    @NonNull
    @Override
    public IComponentsGenerator components(final Components components) {
        this.components = components;
        return this;
    }

    private void generate(
            @NonNull final StringBuilder stringBuilder,
            final String model,
            final Schema schema
    ) {
        schemaGenerator
                .model(model)
                .schema(schema)
                .append(stringBuilder);
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        if (components == null) return stringBuilder;
        if (components.getSchemas() == null) return stringBuilder;
        if (components.getSchemas().isEmpty()) return stringBuilder;
        for (final String model : components.getSchemas().keySet()) {
            generate(stringBuilder, model, components.getSchemas().get(model));
        }
        return stringBuilder;
    }

}
