package ru.job4j.generics;

public class Animal {

    private final String habitat;

    public String getHabitat() {
        return habitat;
    }

    public Animal(String habitat) {
        this.habitat = habitat;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "habitat='" + habitat + '\'' +
                '}';
    }
}
