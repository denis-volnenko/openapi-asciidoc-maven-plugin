# maven-openapi-asciidoc-plugin

## Repository config

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project ...>

    <repositories>
        <repository>
            <id>maven.volnenko.ru</id>
            <name>maven.volnenko.ru</name>
            <url>https://maven.volnenko.ru/repository</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>

</project>
```

## Plugin Configuration
```xml
<build>
    <plugins>
        <plugin>
            <groupId>ru.volnenko.maven.plugin</groupId>
            <artifactId>maven-openapi-asciidoc-plugin</artifactId>
            <version>1.0.0</version>
            <configuration>
                <serviceName>Pet Store</serviceName>
                <headerFirstEnabled>true</headerFirstEnabled>
                <headerSecondEnabled>true</headerSecondEnabled>
                <tableOfContentsEnabled>true</tableOfContentsEnabled>
                <outputPath>${project.basedir}/example/</outputPath>
                <outputFile>index.adoc</outputFile>
                <files>
                    <file>${project.basedir}/example/openapi.yaml</file>
                </files>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Run Plugin

```bash
mvn openapi-asciidoc:generate
```