package ru.volnenko.plugin.openapidoc.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.volnenko.plugin.openapidoc.model.IContact;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Contact implements IContact {

    private String name;

    private String url;

    private String email;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public String email() {
        return email;
    }

}
