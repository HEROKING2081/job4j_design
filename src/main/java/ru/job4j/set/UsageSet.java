package ru.job4j.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UsageSet {
    public static void main(String[] args) {
        Set<String> strings = new HashSet<>();
        strings.add("one");
        strings.add("two");
        strings.add("three");
        Iterator<String> it = strings.iterator();
        while (it.hasNext()) {
            System.out.println("Текущий элемент: " + it.next());
        }
        boolean b = strings.contains("two");
        System.out.println("Множество содержит элемент: " + b);
        int size = strings.size();
        System.out.println("Наше множество содержит: " + size + " элемента.");
        strings.stream()
                .filter(s -> s.length() < 5)
                .forEach(st -> System.out.println("Текущий элемент: " + st));
        strings.removeIf(s -> s.startsWith("t"));
        System.out.println("Вывод в консоль после удаления...");
        for (String s : strings) {
            System.out.println("Текущий элемент: " + s);
        }
    }
}
