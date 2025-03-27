# maven-openapi-asciidoc-plugin


```xml
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
```