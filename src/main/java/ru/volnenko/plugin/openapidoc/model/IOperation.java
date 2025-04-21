package ru.volnenko.plugin.openapidoc.model;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;
import ru.volnenko.plugin.openapidoc.model.impl.RequestBody;
import ru.volnenko.plugin.openapidoc.model.impl.Response;

import java.util.List;
import java.util.Map;

public interface IOperation {

    String operationId();

    String summary();

    RequestBody requestBody();

    List<Parameter> parameters();

    Map<String, Response> responses();

    @NonNull String tags();

    String getOperationId();

    String getSummary();

    ru.volnenko.plugin.openapidoc.model.impl.RequestBody getRequestBody();

    java.util.List<ru.volnenko.plugin.openapidoc.model.impl.Parameter> getParameters();

    java.util.List<String> getTags();

    java.util.Map<String, ru.volnenko.plugin.openapidoc.model.impl.Response> getResponses();

    void setOperationId(String operationId);

    void setSummary(String summary);

    void setRequestBody(ru.volnenko.plugin.openapidoc.model.impl.RequestBody requestBody);

    void setParameters(java.util.List<ru.volnenko.plugin.openapidoc.model.impl.Parameter> parameters);

    void setTags(java.util.List<String> tags);

    void setResponses(java.util.Map<String, ru.volnenko.plugin.openapidoc.model.impl.Response> responses);

}
