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
import ru.volnenko.plugin.openapidoc.model.Root;
import ru.volnenko.plugin.openapidoc.util.MapperUtil;
import ru.volnenko.plugin.openapidoc.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        System.out.println(root.getOpenapi());
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
            stringBuilder.append("== Представление данных\n");
            stringBuilder.append("\n");
        }
    }

}
