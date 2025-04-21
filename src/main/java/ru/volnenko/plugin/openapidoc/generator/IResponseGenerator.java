package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Response;

public interface IResponseGenerator {

    int index();

    @NonNull
    Response response();

    @NonNull
    String httpCode();

    @NonNull
    IResponseGenerator index(int index);

    @NonNull
    IResponseGenerator httpCode(@NonNull String httpCode);

    @NonNull
    IResponseGenerator response(@NonNull Response response);

}
