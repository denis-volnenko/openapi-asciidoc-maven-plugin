package ru.volnenko.plugin.openapidoc.model;

import java.util.List;

public class Schema {

    private String type;

    private String format;

    private String reference;

    private String defaultValue;

    private Boolean uniqueItems;

    private Boolean writeOnly;

    private Boolean readOnly;

    private List<String> enums;

    private Schema items;

}
