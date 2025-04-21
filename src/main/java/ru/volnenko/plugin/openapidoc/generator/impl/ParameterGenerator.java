package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IParameterGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;
import ru.volnenko.plugin.openapidoc.util.ParameterUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

public final class ParameterGenerator implements IParameterGenerator {

    private int index;

    @NonNull
    public Parameter parameter = new Parameter();

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        stringBuilder.append("\n");
        stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
        stringBuilder.append("|" + StringUtil.format(parameter.getName()) + "\n");
        stringBuilder.append("|" + StringUtil.format(parameter.getDescription()) + "\n");
        stringBuilder.append("^|" + parameter.getSchema().toString() + "\n");
        stringBuilder.append("^|" + ParameterUtil.format(parameter) + "\n");
        stringBuilder.append("^|" + StringUtil.format(parameter.getIn()) + "\n");
        stringBuilder.append("^|" + StringUtil.format(parameter.getRequired()) + "\n");
        stringBuilder.append("\n");
        return stringBuilder;
    }

    @NonNull
    @Override
    public IParameterGenerator index(int index) {
        this.index = index;
        return this;
    }

    @Override
    @NonNull
    public IParameterGenerator parameter(@NonNull final Parameter parameter) {
        this.parameter = parameter;
        return this;
    }

    @Override
    public int index() {
        return index;
    }

}
