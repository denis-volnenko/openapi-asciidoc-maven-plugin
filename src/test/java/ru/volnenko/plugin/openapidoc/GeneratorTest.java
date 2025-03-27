package ru.volnenko.plugin.openapidoc;

import org.junit.Assert;
import org.junit.Test;
import ru.volnenko.plugin.openapidoc.model.Root;

public final class GeneratorTest {

    @Test
    public void test() {
        final Generator generator = new Generator();
        Assert.assertNotNull(generator.generate(new Root()));
    }

}
