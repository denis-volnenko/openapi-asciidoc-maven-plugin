package ru.volnenko.plugin.openapidoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import ru.volnenko.plugin.openapidoc.exception.UnsupportedFormatException;
import ru.volnenko.plugin.openapidoc.generator.*;
import ru.volnenko.plugin.openapidoc.generator.impl.*;
import ru.volnenko.plugin.openapidoc.model.impl.*;
import ru.volnenko.plugin.openapidoc.parser.RootParser;
import ru.volnenko.plugin.openapidoc.util.MapperUtil;

import java.io.File;
import java.util.*;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public final class Generator extends AbstractMojo {

    @Getter
    @Setter
    @Parameter(property = "serviceName")
    public String serviceName = "Сервис";

    @Getter
    @Setter
    @Parameter(property = "headerFirstText")
    private String headerFirstText = "REST API";

    @Getter
    @Setter
    @Parameter(property = "headerFirstEnabled")
    public boolean headerFirstEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "headerSecondEnabled")
    public boolean headerSecondEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "tableOfContentsEnabled")
    public boolean tableOfContentsEnabled = true;

    @Getter
    @Setter
    @Parameter(property = "outputPath")
    public String outputPath = "./doc";

    @Getter
    @Setter
    @Parameter(property = "outputFile")
    public String outputFile = "index.adoc";

    @Getter
    @Setter
    @Parameter(property = "outputJsonFile")
    public String outputJsonFile = "scheme.json";

    @Getter
    @Setter
    @Parameter(property = "outputJsonFileEnabled")
    public Boolean outputJsonFileEnabled = false;

    @Getter
    @Setter
    @Parameter(property = "outputJavaScriptFile")
    public String outputJavaScriptFile = "scheme.js";

    @Getter
    @Setter
    @Parameter(property = "outputJavaScriptFileEnabled")
    public Boolean outputJavaScriptFileEnabled = false;

    @Getter
    @Setter
    @Parameter(property = "outputYamlFile")
    public String outputYamlFile = "scheme.yaml";

    @Getter
    @Setter
    @Parameter(property = "outputYamlFileEnabled")
    public Boolean outputYamlFileEnabled = false;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Getter
    @Setter
    @Parameter(property = "paths")
    private List<String> paths = new ArrayList<>();

    @Getter
    @Setter
    @Parameter(property = "files")
    private List<String> files = new ArrayList<>();

    @Getter
    @NonNull
    private final RootParser rootParser = new RootParser();

    @NonNull
    private final StringBuilder stringBuilder = new StringBuilder();

    @NonNull
    private final IHeaderGenerator rootGenerator = new HeaderGenerator();

    @NonNull
    private final IOperationGenerator operationGenerator = new OperationGenerator();

    @NonNull
    private final IComponentsGenerator componentsGenerator = new ComponentsGenerator();

    @NonNull
    private ObjectMapper objectMapper(@NonNull final String file) {
        @NonNull final String name = file.toLowerCase(Locale.ROOT);
        if (name.endsWith(".json")) return MapperUtil.json();
        if (name.endsWith(".yaml")) return MapperUtil.yaml();
        if (name.endsWith(".yml")) return MapperUtil.yaml();
        throw new UnsupportedFormatException();
    }

    @SneakyThrows
    public void execute() {
        header();
        if (paths.isEmpty() && files.isEmpty()) {
            throw new RuntimeException("Error! Files or paths in plugin settings should not be empty...");
        }
        @NonNull final String json = rootParser.paths(paths).files(files).json();
        @NonNull final Root root = MapperUtil.json().readValue(json, Root.class);
        generate(root);
        save();
    }

    @NonNull
    @SneakyThrows
    private Generator saveDatabaseYAML(@NonNull final File path) {
        if (!outputYamlFileEnabled) return this;
        if (outputYamlFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputYamlFile);
        System.out.println(file);
        FileUtils.fileWrite(file, rootParser.yaml());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveDatabaseJSON(@NonNull final File path) {
        if (!outputJsonFileEnabled) return this;
        if (outputJsonFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputJsonFile);
        System.out.println(file);
        FileUtils.fileWrite(file, rootParser.json());
        return this;
    }

    @NonNull
    @SneakyThrows
    private Generator saveDatabaseJavaScript(@NonNull final File path) {
        if (!outputJavaScriptFileEnabled) return this;
        if (outputJavaScriptFile.isEmpty()) return this;
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputJavaScriptFile);
        System.out.println(file);
        FileUtils.fileWrite(file, "var scheme = " + rootParser.json());
        return this;
    }

    @SneakyThrows
    public void save() {
        if (outputPath == null || outputPath.isEmpty()) return;
        if (outputFile == null || outputFile.isEmpty()) return;
        @NonNull final File path = new File(outputPath);
        initOutputPath(path)
                .saveDatabaseYAML(path)
                .saveDatabaseJSON(path)
                .saveDatabaseJavaScript(path);
        @NonNull final File file = new File(path.getAbsolutePath() + "/" + outputFile);
        FileUtils.fileWrite(file, stringBuilder.toString());
    }

    @NonNull
    private Generator initOutputPath(@NonNull final File path) {
        path.mkdirs();
        return this;
    }

    @SneakyThrows
    public void parse(@NonNull final String file) {
        @NonNull final ObjectMapper objectMapper = objectMapper(file);
        @NonNull final Root root = objectMapper.readValue(new File(file), Root.class);
        generate(root);
    }

    @NonNull
    public String generate(final Root root) {
        if (root == null) return "";
        generate(root.getPaths());
        generate(root.getComponents());
        return stringBuilder.toString();
    }

    private void generate(final Map<String, Map<String, Operation>> paths) {
        if (paths == null || paths.isEmpty()) return;
        for (final String path : paths.keySet()) {
            if (path == null || path.isEmpty()) continue;
            generate(path, paths.get(path));
        }
    }

    private void generate(final String path, final Map<String, Operation> operations) {
        if (path == null) return;
        for (final String method : operations.keySet()) {
            if (method == null || method.isEmpty()) continue;
            generate(path, method, operations.get(method));
        }
    }

    private void generate(final Components components) {
        componentsGenerator
                .serviceName(serviceName)
                .components(components)
                .append(stringBuilder);
    }

    private void generate(final String path, final String method, final Operation operation) {
        operationGenerator
                .path(path)
                .method(method)
                .operation(operation)
                .serviceName(serviceName)
                .append(stringBuilder);
    }

    private void header() {
        rootGenerator
                .headerFirstText(headerFirstText)
                .headerFirstEnabled(headerFirstEnabled)
                .headerSecondEnabled(headerSecondEnabled)
                .tableOfContentsEnabled(tableOfContentsEnabled)
                .append(stringBuilder);
    }

}
