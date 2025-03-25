package ru.volnenko.plugin.openapidoc.util;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.Content;

public class ContentUtil {

    @NonNull
    public static String scheme(final Content content) {
        if (content == null) return "--";
        if (content.getSchema() == null) return "--";
        return content.getSchema().toString();
    }

    @NonNull
    public static String format(final Content content) {
        if (content == null) return "--";
        if (content.getSchema() == null) return "--";
        if (content.getSchema().getFormat() == null) return "--";
        return content.getSchema().getFormat();
    }

}
