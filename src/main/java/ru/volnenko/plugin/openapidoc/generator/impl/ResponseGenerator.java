package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IResponseGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Response;

public final class ResponseGenerator implements IResponseGenerator {

    @NonNull
    private String httpCode = "";

    @NonNull
    private Response response = new Response();

    private int index = 0;

    @Override
    public int index() {
        return index;
    }

    @Override
    @NonNull
    public Response response() {
        return response;
    }

    @Override
    @NonNull
    public String httpCode() {
        return httpCode;
    }

    @NonNull
    @Override
    public IResponseGenerator index(final int index) {
        this.index = index;
        return this;
    }

    @NonNull
    @Override
    public IResponseGenerator httpCode(@NonNull final String httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    @NonNull
    @Override
    public IResponseGenerator response(@NonNull final Response response) {
        this.response = response;
        return this;
    }

}
