package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IContent;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Content implements IContent {

    private Schema schema;

    @Override
    public Schema schema() {
        return schema;
    }

}
