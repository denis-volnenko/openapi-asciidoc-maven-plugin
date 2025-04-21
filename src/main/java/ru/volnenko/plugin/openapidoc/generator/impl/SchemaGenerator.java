package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.ISchemaGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Schema;
import ru.volnenko.plugin.openapidoc.util.ContentUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

import java.util.Map;

public final class SchemaGenerator implements ISchemaGenerator {

    private String model;

    private Schema schema;

    private String serviceName;

    @Override
    @NonNull
    public ISchemaGenerator serviceName(final String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Override
    @NonNull
    public ISchemaGenerator schema(final Schema schema) {
        this.schema = schema;
        return this;
    }

    @Override
    @NonNull
    public ISchemaGenerator model(final String model) {
        this.model = model;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        stringBuilder.append("=== Модель данных \"" + model + "\"" + " [[" + model + "]]" + "\n");
        stringBuilder.append("\n");

        stringBuilder.append("==== Общие сведения\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"20,80\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Физ. название*:\n");
        stringBuilder.append("|" + StringUtil.format(model) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Лог. название*:\n");
        stringBuilder.append("|" + StringUtil.format(schema.getDescription()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Тип данных*:\n");
        stringBuilder.append("|" + StringUtil.format(schema.getType()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Сервис*:\n");
        stringBuilder.append("|" + StringUtil.format(serviceName) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");

        stringBuilder.append("==== Описание полей \n");

        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,20,20,20,20,10,10\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("|*Физ. название*\n");
        stringBuilder.append("|*Лог. название*\n");
        stringBuilder.append("|*Описание*\n");
        stringBuilder.append("^|*Тип данных*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("^|*Обязательный*\n");
        stringBuilder.append("\n");

        boolean exists = true;
        Map<String, Schema> properties = schema.getProperties();
        if ("array".equalsIgnoreCase(schema.getType())) {
            if (schema.getItems() != null) {
                properties = schema.getItems().getProperties();
            }
        }
        if (properties == null) exists = false;
        if (properties != null && properties.isEmpty()) exists = false;

        if (!exists) {
            stringBuilder.append("\n");
            stringBuilder.append("7+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        if (exists) {
            int index = 1;
            for (final String field : properties.keySet()) {
                final Schema property = properties.get(field);

                stringBuilder.append("\n");
                stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
                stringBuilder.append("|" + StringUtil.format(field) + "\n");
                stringBuilder.append("|" + StringUtil.format(property.getTitle()) + "\n");
                stringBuilder.append("|" + StringUtil.format(property.getDescription()) + "\n");
                stringBuilder.append("^| " + ContentUtil.scheme(property) + "\n");
                stringBuilder.append("^|" + ContentUtil.format(property) + "\n");

                if (schema.getRequired() == null) {
                    stringBuilder.append("^|--\n");
                } else {
                    if (schema.getRequired().contains(field)) {
                        stringBuilder.append("^|✓\n");
                    } else {
                        stringBuilder.append("^|--\n");
                    }
                }
                index++;
            }
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        return stringBuilder;
    }

}
