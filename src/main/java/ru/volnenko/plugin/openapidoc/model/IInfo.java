package ru.volnenko.plugin.openapidoc.model;

public interface IInfo {

    String title();

    String summary();

    String description();

    String version();

    String getTitle();

    String getSummary();

    String getDescription();

    String getVersion();

    void setTitle(String title);

    void setSummary(String summary);

    void setDescription(String description);

    void setVersion(String version);
}
