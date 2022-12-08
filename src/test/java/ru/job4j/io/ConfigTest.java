package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfigTest {

    @Test
    public void whenComment() {
        String path = "./app.propeties";
        Config config = new Config(path);
        config.load();
        assertNull(config.value("key"));
    }
}