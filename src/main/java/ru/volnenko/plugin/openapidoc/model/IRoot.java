package ru.volnenko.plugin.openapidoc.model;

import ru.volnenko.plugin.openapidoc.model.impl.Components;
import ru.volnenko.plugin.openapidoc.model.impl.Info;
import ru.volnenko.plugin.openapidoc.model.impl.Operation;
import ru.volnenko.plugin.openapidoc.model.impl.Server;

import java.util.List;
import java.util.Map;

public interface IRoot {

    String getOpenapi();

    String getSwagger();

    Info getInfo();

    List<Server> getServers();

    Map<String, Map<String, Operation>> getPaths();

    Components getComponents();

    void setOpenapi(String openapi);

    void setSwagger(String swagger);

    void setInfo(Info info);

    void setServers(List<Server> servers);

    void setPaths(Map<String, Map<String, Operation>> paths);

    void setComponents(Components components);

    String openapi();

    String swagger();

    Info info();

    List<Server> servers();

    Map<String, Map<String, Operation>> paths();

    Components components();

}
