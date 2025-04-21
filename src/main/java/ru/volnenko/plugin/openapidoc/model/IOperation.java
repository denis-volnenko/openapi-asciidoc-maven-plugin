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

    RequestBody getRequestBody();

    List<Parameter> getParameters();

    List<String> getTags();

    Map<String, Response> getResponses();

    void setOperationId(String operationId);

    void setSummary(String summary);

    void setRequestBody(RequestBody requestBody);

    void setParameters(List<Parameter> parameters);

    void setTags(List<String> tags);

    void setResponses(Map<String, Response> responses);

}
