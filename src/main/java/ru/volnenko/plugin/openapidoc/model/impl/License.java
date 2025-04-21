package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.ILicense;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class License implements ILicense {

    private String name;

    private String url;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String url() {
        return url;
    }

}
