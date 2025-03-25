package ru.volnenko.plugin.openapidoc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schema {

    private String description;

    private String type;

    private String format;

    @JsonProperty("$ref")
    private String reference;

    @JsonProperty("default")
    private String defaultValue;

    private Boolean uniqueItems;

    private Boolean writeOnly;

    private Boolean readOnly;

    private List<String> enums;

    private Schema items;

    private Map<String, Schema> properties;

    @Override
    public String toString() {
        if (reference != null && !reference.isEmpty()) {
            return reference;
        }
        return StringUtil.format(type);
    }
}
