package ru.volnenko.plugin.openapidoc.model;

import java.util.List;
import java.util.Map;

public class Root {

    private String openapi;

    private Info info;

    private List<Server> servers;

    private Map<String, Path> paths;

}
