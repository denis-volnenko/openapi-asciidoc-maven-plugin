package ru.volnenko.plugin.openapidoc.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hemantsonu20.json.JsonMerge;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.volnenko.plugin.openapidoc.exception.SwaggerSchemeNotSupportedException;
import ru.volnenko.plugin.openapidoc.exception.UnsupportedFormatException;
import ru.volnenko.plugin.openapidoc.model.impl.Root;
import ru.volnenko.plugin.openapidoc.util.FileUtil;
import ru.volnenko.plugin.openapidoc.util.MapperUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class RootParser implements IRootParser {

    @NonNull
    private List<String> paths = Collections.emptyList();

    @NonNull
    private List<String> files = Collections.emptyList();

    @NonNull
    public RootParser paths(@NonNull final List<String> paths) {
        this.paths = paths;
        return this;
    }

    @NonNull
    public RootParser files(@NonNull final List<String> files) {
        this.files = files;
        return this;
    }

    @NonNull
    @SneakyThrows
    private JsonNode map(@NonNull final String file) {
        @NonNull final ObjectMapper objectMapper = objectMapper(file);
        return objectMapper.readTree(new File(file));
    }

    @NonNull
    private ObjectMapper objectMapper(@NonNull final String file) {
        @NonNull final String name = file.toLowerCase(Locale.ROOT);
        if (name.endsWith(".json")) return MapperUtil.json();
        if (name.endsWith(".yaml")) return MapperUtil.yaml();
        if (name.endsWith(".yml")) return MapperUtil.yaml();
        throw new UnsupportedFormatException();
    }

    @Override
    @NonNull
    @SneakyThrows
    public Root parse(@NonNull final String file) {
        @NonNull final ObjectMapper objectMapper = objectMapper(file);
        @NonNull final Root root = objectMapper.readValue(new File(file), Root.class);
        if (root.getSwagger() != null && !root.getSwagger().isEmpty()) {
            throw new SwaggerSchemeNotSupportedException();
        }
        return root;
    }

    @NonNull
    private List<String> files() {
        @NonNull final List<String> result = new ArrayList<>();
        for (final String path: paths) {
            if (path == null || path.isEmpty()) continue;
            for (final String file : FileUtil.files(path)) {
                if (file == null || file.isEmpty()) continue;
                result.add(file);
            }
        }
        for (final String file : files) {
            if (file == null || file.isEmpty()) continue;
            result.add(file);
        }
        return result;
    }

    @NonNull
    @Override
    @SneakyThrows
    public List<JsonNode> all() {
        @NonNull final List<JsonNode> result = new ArrayList<>();
        for (final String file : files()) {
            if (file == null || file.isEmpty()) continue;
            result.add(map(file));
        }
        return result;
    }

    @NonNull
    @Override
    public JsonNode jsonNode() {
        @NonNull final List<JsonNode> jsonNodes = all();
        @NonNull JsonNode mergedNodes = jsonNodes.get(0);
        for (int i = 1; i < jsonNodes.size(); i++) {
            mergedNodes = JsonMerge.merge(mergedNodes, jsonNodes.get(i));
        }
        return mergedNodes;
    }

    @NonNull
    @Override
    @SneakyThrows
    public String json() {
        return MapperUtil.json().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode());
    }

    @NonNull
    @Override
    @SneakyThrows
    public String yaml() {
        return MapperUtil.yaml().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode());
    }

    @NonNull
    @Override
    public List<Root> parse() {
        @NonNull final List<Root> result = new ArrayList<>();
        for (final String file : files()) {
            if (file == null || file.isEmpty()) continue;
            result.add(parse(file));
        }
        return result;
    }

}
