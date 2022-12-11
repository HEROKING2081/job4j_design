package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(target)
        ))) {
            for (Path s : sources) {
                zos.putNextEntry(new ZipEntry(s.toFile().getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(s.toFile()))) {
                    zos.write(out.readAllBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validate(ArgsName argsName) {
        File directory = new File(argsName.get("d"));
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(String
                    .format("Directory is missing %s check Configurations argument", directory));
        }
        if (!argsName.get("e").startsWith(".")) {
            throw new IllegalArgumentException(String.format("Exclusion must start from . : %s", argsName.get("e")));
        }
        if (!argsName.get("o").endsWith(".zip")) {
            throw new IllegalArgumentException(String.format("The zip extension %s is not set correctly", Path.of(argsName.get("o"))));
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    String.format("Check the arguments there should be three of them and not %s", args.length));
        }
        Zip zip = new Zip();
        ArgsName argsName = ArgsName.of(args);
        validate(argsName);
        List<Path> list = Search.search(Paths.get(argsName.get("d")),
                path -> !path.toFile().getName().endsWith(argsName.get("e")));
        zip.packFiles(list, new File(argsName.get("o")));

        Zip zipSingle = new Zip();
        zipSingle.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
    }
}
