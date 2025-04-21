package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;

public interface IHeaderGenerator extends IGenerator {

    @NonNull
    IHeaderGenerator headerFirstEnabled(boolean headerFirstEnabled);

    @NonNull
    IHeaderGenerator headerSecondEnabled(boolean headerSecondEnabled);

    @NonNull
    IHeaderGenerator tableOfContentsEnabled(boolean tableOfContentsEnabled);

    @NonNull
    IHeaderGenerator headerFirstText(@NonNull String headerFirstText);

    boolean headerFirstEnabled();

    boolean tableOfContentsEnabled();

    boolean headerSecondEnabled();

    @NonNull
    String headerFirstText();

}
