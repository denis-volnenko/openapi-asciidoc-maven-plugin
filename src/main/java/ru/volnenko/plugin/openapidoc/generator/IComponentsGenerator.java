package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Components;

public interface IComponentsGenerator extends IGenerator {

    @NonNull
    IComponentsGenerator components(Components components);

    @NonNull
    IComponentsGenerator serviceName(String serviceName);

}
