package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IParameterGenerator;
import ru.volnenko.plugin.openapidoc.generator.IParametersGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;

import java.util.Collections;
import java.util.List;

public final class ParametersGenerator implements IParametersGenerator {

    @NonNull
    private List<Parameter> parameters = Collections.emptyList();

    @NonNull
    private final IParameterGenerator parameterGenerator = new ParameterGenerator();

    @NonNull
    @Override
    public IParametersGenerator parameters(@NonNull final List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        stringBuilder.append("==== Описание параметров \n");
        int index = 1;
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,20,20,10,10,10,10\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("|*Физ. название*\n");
        stringBuilder.append("|*Лог. название*\n");
        stringBuilder.append("^|*Тип*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("^|*Вид*\n");
        stringBuilder.append("^|*Обязательный*\n");
        stringBuilder.append("\n");

        if (parameters.size() == 0) {
            stringBuilder.append("\n");
            stringBuilder.append("7+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        for (@NonNull final Parameter parameter : parameters) {
            generate(stringBuilder, parameter, index);
            index++;
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");

        return stringBuilder;
    }

    private void generate(@NonNull final StringBuilder stringBuilder, @NonNull final Parameter parameter, int index) {
        parameterGenerator.index(index).parameter(parameter).append(stringBuilder);
    }

}

