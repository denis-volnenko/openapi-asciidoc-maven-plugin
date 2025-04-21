package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Operation {

    private String operationId;

    private String summary;

    private RequestBody requestBody;

    private List<Parameter> parameters;

    private List<String> tags;

    private Map<String, Response> responses;

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
