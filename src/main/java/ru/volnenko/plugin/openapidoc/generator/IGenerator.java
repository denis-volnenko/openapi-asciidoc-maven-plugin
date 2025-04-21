package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;

public interface IGenerator {

    @NonNull
    StringBuilder append(@NonNull StringBuilder stringBuilder);

}
