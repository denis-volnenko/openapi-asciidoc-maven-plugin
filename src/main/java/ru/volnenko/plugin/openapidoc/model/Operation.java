package ru.volnenko.plugin.openapidoc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation {

    private String operationId;

    private RequestBody requestBody;

    private List<Parameter> parameters;

    private Map<String, Response> responses;


}
