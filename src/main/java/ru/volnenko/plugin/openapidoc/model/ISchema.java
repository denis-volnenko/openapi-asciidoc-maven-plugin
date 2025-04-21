package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Schema;

import java.util.List;
import java.util.Map;

public interface ISchema {

    String description();

    String title();

    String type();

    String format();

    String reference();

    String defaultValue();

    Boolean uniqueItems();

    Boolean writeOnly();

    Boolean readOnly();

    List<String> enums();

    Object example();

    Schema items();

    Map<String, Schema> properties();

    List<String> required();

    String getDescription();

    String getTitle();

    String getType();

    String getFormat();

    String getReference();

    String getDefaultValue();

    Boolean getUniqueItems();

    Boolean getWriteOnly();

    Boolean getReadOnly();

    List<String> getEnums();

    Object getExample();

    Schema getItems();

    Map<String, Schema> getProperties();

    List<String> getRequired();

    void setDescription(String description);

    void setTitle(String title);

    void setType(String type);

    void setFormat(String format);

    void setReference(String reference);

    void setDefaultValue(String defaultValue);

    void setUniqueItems(Boolean uniqueItems);

    void setWriteOnly(Boolean writeOnly);

    void setReadOnly(Boolean readOnly);

    void setEnums(List<String> enums);

    boolean referenced();
}
