package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
            String[] str = text.toString().split(System.lineSeparator());
            for (String rsl : str) {
                String out = Integer.parseInt(rsl) % 2 == 0 ? "is even" : "is odd";
                System.out.println("Number: " + rsl + " " + out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
