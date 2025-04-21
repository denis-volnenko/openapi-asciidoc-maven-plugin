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

    public String name() {
        return name;
    }

    public String url() {
        return url;
    }

    public String email() {
        return email;
    }

}
