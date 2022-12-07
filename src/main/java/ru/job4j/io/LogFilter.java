package ru.job4j.io;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LogFilter {
    public static List<String> filter(String file) {
        List<String> rsl = new LinkedList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = in.readLine()) != null) {
                if (str.contains("404")) {
                    rsl.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static void save(List<String> list, String file) {
        try (PrintWriter print = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                print.println(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        save(log, "404.txt");
    }
}
