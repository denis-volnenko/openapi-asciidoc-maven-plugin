package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IOperationGenerator;
import ru.volnenko.plugin.openapidoc.generator.IParametersGenerator;
import ru.volnenko.plugin.openapidoc.generator.IResponseGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Content;
import ru.volnenko.plugin.openapidoc.model.impl.Operation;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;
import ru.volnenko.plugin.openapidoc.model.impl.Response;
import ru.volnenko.plugin.openapidoc.util.ContentUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

import java.util.Collections;
import java.util.List;

public final class OperationGenerator implements IOperationGenerator {

    @NonNull
    private final IParametersGenerator parametersGenerator = new ParametersGenerator();

    @NonNull
    private final IResponseGenerator responseGenerator = new ResponseGenerator();

    private Operation operation;

    private String path;

    private String method;

    private String serviceName;

    @Override
    public String path() {
        return path;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String serviceName() {
        return serviceName;
    }

    @Override
    @NonNull
    public IOperationGenerator path(final String path) {
        this.path = path;
        return this;
    }

    @Override
    @NonNull
    public IOperationGenerator method(final String method) {
        this.method = method;
        return this;
    }

    @Override
    @NonNull
    public IOperationGenerator serviceName(final String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Override
    @NonNull
    public Operation operation() {
        return operation;
    }

    @Override
    @NonNull
    public IOperationGenerator operation(@NonNull final Operation operation) {
        this.operation = operation;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        if (path == null || path.isEmpty()) return stringBuilder;
        if (method == null || method.isEmpty()) return stringBuilder;
        if (operation == null) return stringBuilder;
        stringBuilder.append("=== Ресурс " + operation.tags() + " " + method.toUpperCase() + " \"" + path + "\" \n");
        stringBuilder.append("==== Общие сведения\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"20,80\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Физ. название*:\n");
        stringBuilder.append("|" + StringUtil.format(operation.getOperationId()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Лог. название*:\n");
        stringBuilder.append("|" + StringUtil.format(operation.getSummary()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Сервис*:\n");
        stringBuilder.append("|" + StringUtil.format(serviceName) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*HTTP-метод*:\n");
        stringBuilder.append("|" + StringUtil.format(method.toUpperCase()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*HTTP-адрес*:\n");
        stringBuilder.append("|" + StringUtil.format(path) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");

        if (operation.getParameters() == null) operation.setParameters(Collections.emptyList());
        generate(stringBuilder, operation.getParameters());

        {
            stringBuilder.append("==== Описание запроса \n");

            stringBuilder.append("\n");
            stringBuilder.append("[cols=\"0,20,50,20,10\"]\n");
            stringBuilder.append("|===\n");

            stringBuilder.append("\n");
            stringBuilder.append("^|*№*\n");
            stringBuilder.append("^|*Медиа тип*\n");
            stringBuilder.append("^|*Тип данных*\n");
            stringBuilder.append("^|*Формат*\n");
            stringBuilder.append("^|*Обязательный*\n");
            stringBuilder.append("\n");

            boolean exists = true;
            if (operation.getRequestBody() == null) exists = false;

            if (operation.getRequestBody() != null) {
                if (operation.getRequestBody().getContent() == null) {
                    exists = false;
                } else {
                    if (operation.getRequestBody().getContent().isEmpty()) {
                        exists = false;
                    }
                }
            }

            if (!exists) {
                stringBuilder.append("\n");
                stringBuilder.append("5+^| Отсутствует \n");
                stringBuilder.append("\n");
            }

            if (operation.getRequestBody() != null) {
                if (operation.getRequestBody().getContent() != null) {
                    int index = 1;
                    for (final String mediaType : operation.getRequestBody().getContent().keySet()) {
                        final Content content = operation.getRequestBody().getContent().get(mediaType);
                        if (content == null) continue;

                        stringBuilder.append("\n");
                        stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
                        stringBuilder.append("^|" + StringUtil.format(mediaType) + "\n");
                        stringBuilder.append("^| " + ContentUtil.scheme(content) + "\n");
                        stringBuilder.append("^|" + ContentUtil.format(content) + "\n");
                        stringBuilder.append("^|" + StringUtil.format(operation.getRequestBody().getRequired()) + "\n");
                        stringBuilder.append("\n");
                    }
                }
            }

            stringBuilder.append("\n");
            stringBuilder.append("|===\n");
            stringBuilder.append("\n");
        }

        generate(stringBuilder, operation);
        return stringBuilder;
    }

    private void generate(
            @NonNull final StringBuilder stringBuilder,
            @NonNull final Operation operation
    ) {
        stringBuilder.append("==== Описание ответов \n");

        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,15,20,50,30,20\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("^|*HTTP-код*\n");
        stringBuilder.append("^|*Медиа тип*\n");
        stringBuilder.append("|*Описание*\n");
        stringBuilder.append("^|*Тип данных*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("\n");

        if (operation.getResponses() == null) operation.setResponses(Collections.emptyMap());

        int index = 1;
        for (final String httpCode : operation.getResponses().keySet()) {
            Response response = operation.getResponses().get(httpCode);
            if (response.getContent() == null || response.getContent().isEmpty()) {
                continue;
            }
            generate(stringBuilder, httpCode, response, index);
            index++;
        }

        if (index == 1) {
            stringBuilder.append("\n");
            stringBuilder.append("6+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
    }

    private void generate(
            @NonNull final StringBuilder stringBuilder,
            @NonNull final String httpCode,
            @NonNull final Response response,
            int index
    ) {
        responseGenerator
                .response(response)
                .httpCode(httpCode)
                .index(index)
                .append(stringBuilder);
    }

    private void generate(
            @NonNull final StringBuilder stringBuilder,
            @NonNull final List<Parameter> parameters
    ) {
        parametersGenerator
                .parameters(parameters)
                .append(stringBuilder);
    }

}
