package ru.volnenko.plugin.openapidoc.generator;

import lombok.NonNull;

public interface IRootGenerator extends IGenerator {

    @NonNull
    IRootGenerator headerFirstEnabled(boolean headerFirstEnabled);

    @NonNull
    IRootGenerator headerSecondEnabled(boolean headerSecondEnabled);

    @NonNull
    IRootGenerator tableOfContentsEnabled(boolean tableOfContentsEnabled);

    @NonNull
    IRootGenerator headerFirstText(@NonNull String headerFirstText);

    boolean headerFirstEnabled();

    boolean tableOfContentsEnabled();

    boolean headerSecondEnabled();

    @NonNull
    String headerFirstText();

}
