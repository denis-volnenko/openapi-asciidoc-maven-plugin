package ru.volnenko.plugin.openapidoc.model;

public interface IServer {

    String getUrl();

    String getDescription();

    void setUrl(String url);

    void setDescription(String description);

    String url();

    String description();
}
