package ru.job4j.generics;

public class Tiger extends Predator {
    private final int age;

    public Tiger(int age, String area, String habitat) {
        super(area, habitat);
        this.age = age;
    }

    @Override
    public String toString() {
        return "Tiger{" +
                "age=" + age +
                "habitat=" + getHabitat() +
                "area=" + getArea() +
                '}';
    }
}
