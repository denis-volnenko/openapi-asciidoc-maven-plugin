package ru.volnenko.plugin.openapidoc.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Root;

import java.util.List;

public interface IRootParser {

    @NonNull
    Root parse(@NonNull String file);

    @NonNull
    List<JsonNode> all();

    @NonNull
    JsonNode jsonNode();

    @NonNull
    String json();

    @NonNull
    String yaml();

    @NonNull List<Root> parse();

}
