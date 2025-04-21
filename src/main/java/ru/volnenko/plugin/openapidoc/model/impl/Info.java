package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IInfo;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Info implements IInfo {

    private String title;

    private String summary;

    private String description;

    private String version;

    @Override
    public String title() {
        return title;
    }

    @Override
    public String summary() {
        return summary;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String version() {
        return version;
    }

}
