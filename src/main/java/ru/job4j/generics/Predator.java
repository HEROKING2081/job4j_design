package ru.job4j.generics;

public class Predator extends Animal {
    private final String area;

    public String getArea() {
        return area;
    }

    public Predator(String area, String habitat) {
        super(habitat);
        this.area = area;
    }

    @Override
    public String toString() {
        return "Predator{" +
                "area='" + area + '\'' +
                "habitat=" + getHabitat() +
                '}';
    }
}
