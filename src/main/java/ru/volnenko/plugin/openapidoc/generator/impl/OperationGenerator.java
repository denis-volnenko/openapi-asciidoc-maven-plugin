package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IOperationGenerator;
import ru.volnenko.plugin.openapidoc.generator.IResponseGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Operation;
import ru.volnenko.plugin.openapidoc.model.impl.Response;

import java.util.Collections;

public final class OperationGenerator implements IOperationGenerator {

    @NonNull
    private final IResponseGenerator responseGenerator = new ResponseGenerator();

    @NonNull
    private Operation operation = new Operation();

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
        return stringBuilder;
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

}
