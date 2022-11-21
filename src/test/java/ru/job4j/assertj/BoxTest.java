package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube");
    }

    @Test
    void isGetNumberTwo() {
        Box box = new Box(2, 24);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(-1);
    }

    @Test
    void isGetNumberFour() {
        Box box = new Box(4, 24);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(4);
    }

    @Test
    void isExist() {
        Box box = new Box(4, 6);
        boolean rsl = box.isExist();
        assertThat(rsl).isEqualTo(true);
    }

    @Test
    void isNotExist() {
        Box box = new Box(2, 6);
        boolean rsl = box.isExist();
        assertThat(rsl).isEqualTo(false);
    }

    @Test
    void isBoxArea() {
        Box box = new Box(8, 6);
        double rsl = box.getArea();
        assertThat(rsl).isEqualTo(216);
    }

    @Test
    void isArea() {
        Box box = new Box(1, 6);
        double rsl = box.getArea();
        assertThat(rsl).isEqualTo(0);
    }
}