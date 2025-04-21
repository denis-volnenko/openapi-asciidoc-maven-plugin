package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Operation;

public interface IOperationGenerator extends IGenerator {

    @NonNull
    Operation operation();

    @NonNull
    IOperationGenerator operation(@NonNull Operation operation);

}
