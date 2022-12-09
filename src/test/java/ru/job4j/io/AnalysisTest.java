package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalysisTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenNormal() throws IOException {
        File source = folder.newFile("source.log");
        File target = folder.newFile("target.txt");
        try (PrintWriter writer = new PrintWriter(source)) {
            writer.write(
                    "200 10:56:01" + System.lineSeparator() +
                            "200 10:57:01" + System.lineSeparator() +
                            "400 10:58:01" + System.lineSeparator() +
                            "200 10:59:01" + System.lineSeparator() +
                            "500 11:01:02" + System.lineSeparator() +
                            "200 11:02:02"
            );
        }
        new Analysis().unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(el -> {
                String string = el + System.lineSeparator();
                rsl.append(string);
            });
        }
        String f = String.valueOf(rsl);
        String exp = "10:58:01;10:59:01" + System.lineSeparator() + "11:01:02;11:02:02" + System.lineSeparator();
        assertThat(f, is(exp));
    }

    @Test
    public void whenTooNormal() throws IOException {
        File source = folder.newFile("source.log");
        try (PrintWriter writer = new PrintWriter(source)) {
            writer.write(
                    "500 10:43:13" + System.lineSeparator() +
                            "400 10:43:34" + System.lineSeparator() +
                            "200 10:43:55" + System.lineSeparator() +
                            "300 10:44:16" + System.lineSeparator() +
                            "200 10:44:37" + System.lineSeparator() +
                            "200 10:44:58" + System.lineSeparator() +
                            "200 10:45:19" + System.lineSeparator() +
                            "300 10:45:40" + System.lineSeparator() +
                            "300 10:46:01"
            );
        }
        File target = folder.newFile("target.txt");
        new Analysis().unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(el -> {
                String string = el + System.lineSeparator();
                rsl.append(string);
            });
        }
        String f = String.valueOf(rsl);
        String exp = "10:43:13;10:43:55" + System.lineSeparator();
        assertThat(f, is(exp));
    }
}