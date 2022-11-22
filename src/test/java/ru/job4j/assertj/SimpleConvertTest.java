package ru.job4j.assertj;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> resultList =
                simpleConvert.toList("first", "second", "three", "four", "five", "six");
        assertThat(resultList).isNotEmpty()
                .hasSize(6)
                .contains("four")
                .contains("five", Index.atIndex(4))
                .containsSequence("second", "three")
                .allSatisfy(elem -> {
                    assertThat(elem.length()).isLessThan(7);
                    assertThat(elem.length()).isGreaterThan(2);
                })
                .anySatisfy(elem -> {
                    assertThat(elem).isEqualTo("six");
                    assertThat(elem.length()).isGreaterThan(2);
                })
                .allMatch(elem -> elem.length() > 2)
                .anyMatch(elem -> elem.equals("five"))
                .noneMatch(elem -> elem.equals("seven"))
                .first().isEqualTo("first");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> rsl =
                simpleConvert.toSet("5", "4", "3", "2", "7", "4", "3", "8", "5", "6");
        assertThat(rsl).hasSize(7);
        assertThat(rsl).element(5).isEqualTo("7");
        assertThat(rsl).first().isEqualTo("2");
        assertThat(rsl).last().isNotNull().isEqualTo("8");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> rsl =
                simpleConvert.toMap("a", "b", "b", "a", "a", "c", "d", "c");
        assertThat(rsl)
                .containsKeys("a", "b", "c", "d")
                .containsValues(0, 1, 5, 6)
                .containsEntry("a", 0)
                .containsEntry("b", 1)
                .containsEntry("c", 5)
                .containsEntry("d", 6)
                .hasSize(4);
    }
}