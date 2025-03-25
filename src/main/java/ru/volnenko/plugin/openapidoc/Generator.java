package ru.volnenko.plugin.openapidoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;
import ru.volnenko.plugin.openapidoc.exception.UnsupportedFormatException;
import ru.volnenko.plugin.openapidoc.model.Content;
import ru.volnenko.plugin.openapidoc.model.Operation;
import ru.volnenko.plugin.openapidoc.model.Response;
import ru.volnenko.plugin.openapidoc.model.Root;
import ru.volnenko.plugin.openapidoc.util.ContentUtil;
import ru.volnenko.plugin.openapidoc.util.MapperUtil;
import ru.volnenko.plugin.openapidoc.util.ParameterUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

import java.io.File;
import java.util.*;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class Generator extends AbstractMojo {

    @Getter
    @Setter
    @Parameter(property = "serviceName")
    public String serviceName = "Сервис";

    @Getter
    @Setter
    @Parameter(property = "dataBaseInfo")
    public String dataBaseInfo = "";

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

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Getter
    @Setter
    @Parameter(property = "files")
    private List<String> files = new ArrayList<>();

    @NonNull
    private final StringBuilder stringBuilder = new StringBuilder();

    @NonNull
    private ObjectMapper objectMapper(@NonNull final String file) {
        @NonNull final String name = file.toLowerCase(Locale.ROOT);
        if (name.endsWith(".json")) return MapperUtil.json();
        if (name.endsWith(".yaml")) return MapperUtil.yaml();
        if (name.endsWith(".yml")) return MapperUtil.yaml();
        throw new UnsupportedFormatException();
    }

    @SneakyThrows
    public void execute() throws MojoExecutionException, MojoFailureException {
        header();
        for (final String file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            parse(file);
        }
        save();
    }

    @SneakyThrows
    public void save() {
        if (outputPath == null || outputPath.isEmpty()) return;
        File path = new File(outputPath);
        path.mkdirs();

        if (outputFile == null || outputFile.isEmpty()) return;
        File file = new File(path.getAbsolutePath() + "/" + outputFile);
        FileUtils.fileWrite(file, stringBuilder.toString());
    }

    @SneakyThrows
    public void parse(@NonNull final String file) {
        @NonNull final ObjectMapper objectMapper = objectMapper(file);
        @NonNull final Root root = objectMapper.readValue(new File(file), Root.class);
        generate(root);
    }

    public void generate(@NonNull final Root root) {
        generate(root.getPaths());
        System.out.println(root.getOpenapi());
    }

    private void generate(Map<String, Map<String, Operation>> paths) {
        for (String path: paths.keySet()) {
            generate(path, paths.get(path));
        }
    }

    private void generate(String path,  Map<String, Operation> operations) {
        for (String method: operations.keySet()) {
            generate(path, method, operations.get(method));
        }
    }

    private void generate(String path, String method, Operation operation) {
        stringBuilder.append("=== "+ method.toUpperCase() + " \"" + path + "\" \n");
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
        generate(operation.getParameters().toArray(new ru.volnenko.plugin.openapidoc.model.Parameter[0]));

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
                    for (final String mediaType: operation.getRequestBody().getContent().keySet()) {
                        final Content content = operation.getRequestBody().getContent().get(mediaType);
                        if (content == null) continue;

                        stringBuilder.append("\n");
                        stringBuilder.append("^|"+StringUtil.format(index)+". \n");
                        stringBuilder.append("^|" + StringUtil.format(mediaType) + "\n");
                        stringBuilder.append("^| " + ContentUtil.scheme(content) + "\n");
                        stringBuilder.append("^|"+ ContentUtil.format(content)+"\n");
                        stringBuilder.append("^|"+StringUtil.format(operation.getRequestBody().getRequired()) +"\n");
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
        stringBuilder.append("==== Описание ответов \n");

        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,15,20,50,30,20\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("^|*HTTP-код*\n");
        stringBuilder.append("^|*Медиа тип*\n");
        stringBuilder.append("|*Описание*\n");
        stringBuilder.append("^|*Тип данных*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("\n");

        if (operation.getResponses() == null) operation.setResponses(Collections.emptyMap());

        int index = 1;
        for (final String httpCode: operation.getResponses().keySet()) {
            Response response = operation.getResponses().get(httpCode);
            if (response.getContent() == null || response.getContent().isEmpty()) {
                continue;
            }
            generate(httpCode, response, index);
            index++;
        }

        if (index == 1) {
            stringBuilder.append("\n");
            stringBuilder.append("6+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
    }

    private void generate(@NonNull final String httpCode, final Response response, int index) {
        if (response.getContent() == null || response.getContent().isEmpty()) {
            return;
        }
        for (final String mediaType: response.getContent().keySet()) {
            final Content content = response.getContent().get(mediaType);
            stringBuilder.append("\n");
            stringBuilder.append("^|" + StringUtil.format(index) + ". \n");
            stringBuilder.append("^|" + StringUtil.format(httpCode) + "\n");
            stringBuilder.append("^| \"" + StringUtil.format(mediaType) + "\" \n");
            stringBuilder.append("|\n");
            stringBuilder.append("^| " + ContentUtil.scheme(content) + "\n");
            stringBuilder.append("^|"+ ContentUtil.format(content)+"\n");
            stringBuilder.append("\n");
        }
    }

    private void generate(@NonNull final ru.volnenko.plugin.openapidoc.model.Parameter[] parameters) {
        stringBuilder.append("==== Описание параметров \n");
        int index = 1;
        stringBuilder.append("\n");
        stringBuilder.append("[cols=\"0,20,20,10,10,10,10\"]\n");
        stringBuilder.append("|===\n");

        stringBuilder.append("\n");
        stringBuilder.append("^|*№*\n");
        stringBuilder.append("|*Физ. название*\n");
        stringBuilder.append("|*Лог. название*\n");
        stringBuilder.append("^|*Тип*\n");
        stringBuilder.append("^|*Формат*\n");
        stringBuilder.append("^|*Вид*\n");
        stringBuilder.append("^|*Обязательный*\n");
        stringBuilder.append("\n");

        if (parameters.length == 0) {
            stringBuilder.append("\n");
            stringBuilder.append("7+^| Отсутствует \n");
            stringBuilder.append("\n");
        }

        for (ru.volnenko.plugin.openapidoc.model.Parameter parameter: parameters) {
            generate(parameter, index);
            index++;
        }

        stringBuilder.append("\n");
        stringBuilder.append("|===\n");
        stringBuilder.append("\n");
    }

    private void generate(ru.volnenko.plugin.openapidoc.model.Parameter parameter, int index) {
        stringBuilder.append("\n");
        stringBuilder.append("^|"+StringUtil.format(index) + ". \n");
        stringBuilder.append("|"+StringUtil.format(parameter.getName())+"\n");
        stringBuilder.append("|"+StringUtil.format(parameter.getDescription())+"\n");
        stringBuilder.append("^|"+parameter.getSchema().toString() + "\n");
        stringBuilder.append("^|"+ ParameterUtil.format(parameter)+"\n");
        stringBuilder.append("^|"+StringUtil.format(parameter.getIn())+"\n");
        stringBuilder.append("^|"+StringUtil.format(parameter.getRequired()) +"\n");
        stringBuilder.append("\n");
    }

    private void header() {
        if (headerFirstEnabled) {
            stringBuilder.append("= " + StringUtil.format(serviceName) + "\n");
            if (tableOfContentsEnabled) {
                stringBuilder.append(":toc-title: Оглавление\n");
                stringBuilder.append(":toc:\n");
            }
            stringBuilder.append("\n");
        }
        if (headerSecondEnabled) {
            stringBuilder.append("== Представление веб-сервисов \n");
            stringBuilder.append("\n");
        }
    }

}
