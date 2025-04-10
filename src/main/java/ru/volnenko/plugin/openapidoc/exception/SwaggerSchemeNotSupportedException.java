package ru.volnenko.plugin.openapidoc.exception;

public final class SwaggerSchemeNotSupportedException extends RuntimeException {

    public SwaggerSchemeNotSupportedException() {
        super("Error! Swagger scheme is not supported (only openapi)...");
    }

}
