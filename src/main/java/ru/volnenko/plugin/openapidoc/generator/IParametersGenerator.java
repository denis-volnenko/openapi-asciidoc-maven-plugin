package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;

import java.util.List;

public interface IParametersGenerator extends IGenerator {

    @NonNull
    IParametersGenerator parameters(@NonNull List<Parameter> parameters);

}
