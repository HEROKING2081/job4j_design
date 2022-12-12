package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private String phrase;
    private final List<String> log = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        List<String> phrases = readPhrases(botAnswers);
        System.out.println("Enter a word or phrase: ");
        while (!OUT.equals(phrase)) {
            phrase = input.nextLine();
            log.add(phrase);
            if (STOP.equals(phrase)) {
                while (!CONTINUE.equals(phrase)) {
                    phrase = input.nextLine();
                    log.add(phrase);
                }
            }
            if (OUT.equals(phrase)) {
                break;
            }
            String randomPhrase = phrases.get(new Random().nextInt(phrases.size()));
            System.out.println(randomPhrase);
            log.add(randomPhrase);
        }
        saveLog(log);
    }

    private List<String> readPhrases(String path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(botAnswers,
                Charset.forName("WINDOWS-1251")))) {
            list = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(path))) {
            log.forEach(printWriter::println);
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("logSave.txt", "inputPhrase.txt");
        cc.run();
    }
}
