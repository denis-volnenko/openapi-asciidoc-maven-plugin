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
public final class Schema {

    private String description;

    private String title;

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

    private Object example;

    private Schema items;

    private Map<String, Schema> properties;

    private List<String> required;

    public boolean referenced() {
        String localReference = reference;
        String localType = type;

        if ("array".equalsIgnoreCase(type)) {
            if (items == null) return false;
            localReference = items.getReference();
            localType = items.getType();
        }

        return localReference != null && !localReference.isEmpty();
    }

    @Override
    public String toString() {
        String localReference = reference;
        String localType = type;

        String dataType = "";
        if ("array".equalsIgnoreCase(type)) {
            if (items == null) return "array";
            localReference = items.getReference();
            localType = items.getType();
        }

        if (localReference != null && !localReference.isEmpty()) {
            String[] items = localReference.split("/");
            dataType = items[items.length - 1];
        } else {
            dataType = StringUtil.format(localType);
        }

        if (referenced()) {
            dataType = "<<" + dataType + "," + dataType + ">>";
        }

        if ("array".equalsIgnoreCase(type)) {
            dataType += "[]";
        }
        return dataType;
    }
}
