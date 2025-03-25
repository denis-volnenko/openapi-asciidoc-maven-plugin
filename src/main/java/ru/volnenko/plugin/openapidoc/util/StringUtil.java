package ru.volnenko.plugin.openapidoc.util;

import lombok.NonNull;

public final class StringUtil {

    @NonNull
    public static final String EMPTY = "--";

    public static boolean exists(final String value) {
        if (value == null) return false;
        return !value.isEmpty();
    }

    @NonNull
    public static String format(final Integer value) {
        if (value == null) return "";
        return String.format("%02d", value);
    }

    @NonNull
    public static String format(final boolean value) {
        if (!value) return EMPTY;
        return "✓";
    }

    @NonNull
    public static String format(final Boolean value) {
        if (value == null || !value) return EMPTY;
        return "✓";
    }

    @NonNull
    public static String format(final String value) {
        if (value == null) return "";
        return value;
    }

}
