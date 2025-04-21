package ru.volnenko.plugin.openapidoc.generator.impl;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.generator.IResponseGenerator;
import ru.volnenko.plugin.openapidoc.model.impl.Content;
import ru.volnenko.plugin.openapidoc.model.impl.Response;
import ru.volnenko.plugin.openapidoc.util.ContentUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

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

    @NonNull
    @Override
    public StringBuilder append(@NonNull final StringBuilder stringBuilder) {
        if (response.getContent() == null || response.getContent().isEmpty()) {
            return stringBuilder;
        }
        for (final String mediaType : response.getContent().keySet()) {
            final Content content = response.getContent().get(mediaType);
            stringBuilder.append("\n");
            stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
            stringBuilder.append("^|" + StringUtil.format(httpCode) + "\n");
            stringBuilder.append("^| \"" + StringUtil.format(mediaType) + "\" \n");
            stringBuilder.append("|" + String.format(response.getDescription()) + "\n");
            stringBuilder.append("^| " + ContentUtil.scheme(content) + "\n");
            stringBuilder.append("^|" + ContentUtil.format(content) + "\n");
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }
}
