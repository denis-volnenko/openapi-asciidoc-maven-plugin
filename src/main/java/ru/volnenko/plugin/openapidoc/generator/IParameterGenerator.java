package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;

public interface IParameterGenerator extends IGenerator {

    @NonNull
    IParameterGenerator index(int index);

    @NonNull
    IParameterGenerator parameter(@NonNull Parameter parameter);

    int index();

}
