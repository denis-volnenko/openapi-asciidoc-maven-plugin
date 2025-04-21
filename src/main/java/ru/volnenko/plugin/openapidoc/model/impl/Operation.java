package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IOperation;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Operation implements IOperation {

    private String operationId;

    private String summary;

    private RequestBody requestBody;

    private List<Parameter> parameters;

    private List<String> tags;

    private Map<String, Response> responses;

    @Override
    public String operationId() {
        return operationId;
    }

    @Override
    public String summary() {
        return summary;
    }

    @Override
    public RequestBody requestBody() {
        return requestBody;
    }

    @Override
    public List<Parameter> parameters() {
        return parameters;
    }

    @Override
    public Map<String, Response> responses() {
        return responses;
    }

    @Override
    @NonNull
    public String tags() {
        if (tags == null) return "";
        @NonNull String result = "";
        for (String tag : tags) {
            if (tag == null || tag.isEmpty()) continue;
            if (!result.isEmpty()) result += ", ";
            result += tag;
        }
        return "(" + result + ")";
    }


}
