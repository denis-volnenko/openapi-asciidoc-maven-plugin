package ru.volnenko.plugin.openapidoc.util;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileUtil {

    @NonNull
    public static List<String> files(@NonNull final String folderPathString) {
        @NonNull final Path rootFolder = Paths.get(folderPathString);
        @NonNull final List<String> result = new ArrayList<>();
        try (@NonNull final Stream<Path> paths = Files.walk(rootFolder)) {
            final List<Path> allFiles = paths.filter(new Predicate<Path>() {
                @Override
                public boolean test(final Path path) {
                    return Files.isRegularFile(path);
                }
            }).collect(Collectors.toList());
            for (@NonNull final Path file : allFiles) {
                result.add(file.toAbsolutePath().toString());
            }
            return result;
        } catch (@NonNull final IOException e) {
            return Collections.emptyList();
        }
    }

}
