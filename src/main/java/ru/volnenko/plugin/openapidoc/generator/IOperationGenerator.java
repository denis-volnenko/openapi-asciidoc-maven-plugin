package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Operation;

public interface IOperationGenerator extends IGenerator {

    String path();

    String method();

    String serviceName();

    @NonNull
    IOperationGenerator path(String path);

    @NonNull
    IOperationGenerator method(String method);

    @NonNull
    IOperationGenerator serviceName(String serviceName);

    @NonNull
    Operation operation();

    @NonNull
    IOperationGenerator operation(@NonNull Operation operation);

}
