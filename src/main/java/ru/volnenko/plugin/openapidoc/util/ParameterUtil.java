package ru.volnenko.plugin.openapidoc.util;

import lombok.NonNull;
import ru.volnenko.plugin.openapidoc.model.impl.Parameter;

public final class ParameterUtil {

    @NonNull
    public static String format(final Parameter parameter) {
        if (parameter == null) return "--";
        if (parameter.getSchema() == null) return "--";
        if (parameter.getSchema().getFormat() == null) return "--";
        return parameter.getSchema().getFormat();
    }

}
