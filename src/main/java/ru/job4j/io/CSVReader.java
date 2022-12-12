package ru.job4j.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    public static void handle(ArgsName argsName) {
        isValidateArgs(argsName);

        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String[] columns = argsName.get("filter").split(",");
        File file = Path.of(argsName.get("path")).toFile();
        List<Integer> indexes = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();

        readFile(delimiter, file, lines);
        findIndexFilters(columns, indexes, lines);
        printTable(argsName, out, indexes, lines);
    }

    private static void readFile(String delimiter, File file, List<String[]> lines) {
        try (Scanner scanner = new Scanner(file).useDelimiter(System.lineSeparator())) {
            while (scanner.hasNext()) {
                String[] split = scanner.next().split(delimiter);
                lines.add(split);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void findIndexFilters(String[] columns, List<Integer> indexes, List<String[]> lines) {
        for (String col : columns) {
            String[] get = lines.get(0);
            for (int i = 0; i < get.length; i++) {
                if (col.equals(get[i])) {
                    indexes.add(i);
                }
            }
        }
    }

    private static void printTable(ArgsName argsName, String out,
                                   List<Integer> indexes, List<String[]> lines) {
        StringBuilder builder = new StringBuilder();
        for (var line : lines) {
            for (int i = 0; i < indexes.size(); i++) {
                Integer index = indexes.get(i);
                if (out.equals("stdout")) {
                    System.out.print(line[index]);
                    if (i != indexes.size() - 1) {
                        System.out.print(";");
                    }
                } else {
                    builder.append(line[index]);
                    if (i != indexes.size() - 1) {
                        builder.append(";");
                    }
                }

            }
            if (out.equals("stdout")) {
                System.out.println();
            } else {
                builder.append(System.lineSeparator());
            }
        }
        if (!out.equals("stdout")) {
            File target = Path.of(argsName.get("out")).toFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(target))) {
                writer.write(builder.toString());
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    private static void isValidateArgs(ArgsName argsName) {
        File file = Path.of(argsName.get("path")).toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException("The file (path) does not exist");
        }
        if (argsName.get("delimiter").isBlank()) {
            throw new IllegalArgumentException("Delimiter empty");
        }
        if (argsName.get("filter").isBlank()) {
            throw new IllegalArgumentException("Filter empty");
        }
        if (!argsName.get("path").contains(".csv")) {
            throw new IllegalArgumentException("Unsupported format file, csv is needed");
        }
    }
}
