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
import ru.volnenko.plugin.openapidoc.util.ContentUtil;
import ru.volnenko.plugin.openapidoc.util.MapperUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

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
    @Parameter(property = "files")
    private List<String> files = new ArrayList<>();

    @Getter
    @NonNull
    private final RootParser rootParser = new RootParser();

    @NonNull
    private final StringBuilder stringBuilder = new StringBuilder();

    @NonNull
    private final IRootGenerator rootGenerator = new RootGenerator();

    @NonNull
    private final IParameterGenerator parameterGenerator = new ParameterGenerator();

    @NonNull
    private final IResponseGenerator responseGenerator = new ResponseGenerator();

    @NonNull
    private final IParametersGenerator parametersGenerator = new ParametersGenerator();

    @NonNull
    private final IOperationGenerator operationGenerator = new OperationGenerator();

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
        @NonNull final String json = rootParser.files(files).json();
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

    private void generate(final Components components) {
        if (components == null) return;
        if (components.getSchemas() == null) return;
        if (components.getSchemas().isEmpty()) return;
        int index = 1;
        for (final String model : components.getSchemas().keySet()) {
            generate(model, components.getSchemas().get(model), index);
            index++;
        }
    }

    public void generate(final String model, final Schema schema, final int indexm) {
        stringBuilder.append("=== Модель данных \"" + model + "\"" + " [[" + model + "]]" + "\n");
        stringBuilder.append("\n");

        stringBuilder.append("==== Общие сведения\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"20,80\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Физ. название*:\n");
        stringBuilder.append("|" + StringUtil.format(model) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Лог. название*:\n");
        stringBuilder.append("|" + StringUtil.format(schema.getDescription()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Тип данных*:\n");
        stringBuilder.append("|" + StringUtil.format(schema.getType()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Сервис*:\n");
        stringBuilder.append("|" + StringUtil.format(serviceName) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");

        stringBuilder.append("==== Описание полей \n");

        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,20,20,20,20,10,10\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("|*Физ. название*\n");
        stringBuilder.append("|*Лог. название*\n");
        stringBuilder.append("|*Описание*\n");
        stringBuilder.append("^|*Тип данных*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("^|*Обязательный*\n");
        stringBuilder.append("\n");

        boolean exists = true;
        Map<String, Schema> properties = schema.getProperties();
        if ("array".equalsIgnoreCase(schema.getType())) {
            if (schema.getItems() != null) {
                properties = schema.getItems().getProperties();
            }
        }
        if (properties == null) exists = false;
        if (properties != null && properties.isEmpty()) exists = false;

        if (!exists) {
            stringBuilder.append("\n");
            stringBuilder.append("7+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        if (exists) {
            int index = 1;
            for (final String field : properties.keySet()) {
                final Schema property = properties.get(field);

                stringBuilder.append("\n");
                stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
                stringBuilder.append("|" + StringUtil.format(field) + "\n");
                stringBuilder.append("|" + StringUtil.format(property.getTitle()) + "\n");
                stringBuilder.append("|" + StringUtil.format(property.getDescription()) + "\n");
                stringBuilder.append("^| " + ContentUtil.scheme(property) + "\n");
                stringBuilder.append("^|" + ContentUtil.format(property) + "\n");

                if (schema.getRequired() == null) {
                    stringBuilder.append("^|--\n");
                } else {
                    if (schema.getRequired().contains(field)) {
                        stringBuilder.append("^|✓\n");
                    } else {
                        stringBuilder.append("^|--\n");
                    }
                }
                index++;
            }
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
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

    private void generate(String path, String method, Operation operation) {
        if (path == null || path.isEmpty()) return;
        if (method == null || method.isEmpty()) return;
        if (operation == null) return;
        stringBuilder.append("=== Ресурс " + operation.tags() + " " + method.toUpperCase() + " \"" + path + "\" \n");
        stringBuilder.append("==== Общие сведения\n");
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"20,80\"]\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Физ. название*:\n");
        stringBuilder.append("|" + StringUtil.format(operation.getOperationId()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Лог. название*:\n");
        stringBuilder.append("|" + StringUtil.format(operation.getSummary()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*Сервис*:\n");
        stringBuilder.append("|" + StringUtil.format(serviceName) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*HTTP-метод*:\n");
        stringBuilder.append("|" + StringUtil.format(method.toUpperCase()) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|*HTTP-адрес*:\n");
        stringBuilder.append("|" + StringUtil.format(path) + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");

        if (operation.getParameters() == null) operation.setParameters(Collections.emptyList());
        generate(operation.getParameters());

        {
            stringBuilder.append("==== Описание запроса \n");

            stringBuilder.append("\n");
            stringBuilder.append("[cols=\"0,20,50,20,10\"]\n");
            stringBuilder.append("|===\n");

            stringBuilder.append("\n");
            stringBuilder.append("^|*№*\n");
            stringBuilder.append("^|*Медиа тип*\n");
            stringBuilder.append("^|*Тип данных*\n");
            stringBuilder.append("^|*Формат*\n");
            stringBuilder.append("^|*Обязательный*\n");
            stringBuilder.append("\n");

            boolean exists = true;
            if (operation.getRequestBody() == null) exists = false;

            if (operation.getRequestBody() != null) {
                if (operation.getRequestBody().getContent() == null) {
                    exists = false;
                } else {
                    if (operation.getRequestBody().getContent().isEmpty()) {
                        exists = false;
                    }
                }
            }

            if (!exists) {
                stringBuilder.append("\n");
                stringBuilder.append("5+^| Отсутствует \n");
                stringBuilder.append("\n");
            }

            if (operation.getRequestBody() != null) {
                if (operation.getRequestBody().getContent() != null) {
                    int index = 1;
                    for (final String mediaType : operation.getRequestBody().getContent().keySet()) {
                        final Content content = operation.getRequestBody().getContent().get(mediaType);
                        if (content == null) continue;

                        stringBuilder.append("\n");
                        stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
                        stringBuilder.append("^|" + StringUtil.format(mediaType) + "\n");
                        stringBuilder.append("^| " + ContentUtil.scheme(content) + "\n");
                        stringBuilder.append("^|" + ContentUtil.format(content) + "\n");
                        stringBuilder.append("^|" + StringUtil.format(operation.getRequestBody().getRequired()) + "\n");
                        stringBuilder.append("\n");
                    }
                }
            }

            stringBuilder.append("\n");
            stringBuilder.append("|===\n");
            stringBuilder.append("\n");
        }

        generate(operation);
    }

    private void generate(@NonNull final Operation operation) {
        operationGenerator
                .operation(operation)
                .append(stringBuilder);
    }


    private void generate(@NonNull final List<ru.volnenko.plugin.openapidoc.model.impl.Parameter> parameters) {
        parametersGenerator
                .parameters(parameters)
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
