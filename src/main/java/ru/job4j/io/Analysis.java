package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    public void unavailable(String source, String target) {
        List<String> rsl = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new FileReader(source))) {
            String str = read.readLine();
            int count = 0;
            boolean active = true;
            while (str != null) {
                String[] arr = str.split(" ");
                if (arr.length != 2) {
                    throw new IllegalArgumentException();
                }
                int j = Integer.parseInt(arr[0]);
                if ((j == 400 || j == 500) && active == true) {
                    rsl.add(arr[1]);
                    count++;
                    active = false;
                } else if ((j == 200 || j == 300) && active == false) {
                    String change = rsl.get(count - 1) + ";" + arr[1];
                    rsl.set(count - 1, change);
                    active = true;
                }
                str = read.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        write(rsl, target);
    }

    private void write(List<String> list, String target) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(target))) {
            for (String s : list) {
                out.write(s);
                out.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception el) {
            el.printStackTrace();
        }
    }
}
