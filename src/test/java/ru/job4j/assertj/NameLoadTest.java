package ru.job4j.assertj;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkContains() {
        NameLoad nameLoad = new NameLoad();
        String[] strArr = {};
        assertThatThrownBy(() -> nameLoad.parse(strArr))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("empty")
                .message()
                .isNotEmpty()
                .isEqualTo("Names array is empty");
    }

    @Test
    void checkStartsWith() {
        NameLoad nameLoad = new NameLoad();
        String[] strArr = {"test1", "test2=abc"};
        assertThatThrownBy(() -> nameLoad.parse(strArr))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty()
                .isEqualTo(
                        String.format("this name: %s does not contain the symbol \"=\"", strArr[0])
                );
    }

    @Test
    void whenArrayContainsElementStartsWithEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String[] strArr = {"test1=abc", "=test2"};
        assertThatThrownBy(() -> nameLoad.parse(strArr))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty()
                .isEqualTo(String.format("this name: %s does not contain a key", strArr[1]));
    }

    @Test
    void whenArrayContainsElementEndsWithEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String[] strArr = {"test1=abc", "test2="};
        assertThatThrownBy(() -> nameLoad.parse(strArr))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty()
                .isEqualTo(String.format("this name: %s does not contain a value", strArr[1]));
    }

    @Test
    void whenArrayIsCompletelyValidated() {
        Map<String, String> expected = new HashMap<>();
        expected.put("test1", "abc");
        expected.put("test2", "def");
        NameLoad nameLoad = new NameLoad();
        String[] strArr = {"test1=abc", "test2=def"};
        nameLoad.parse(strArr);
        Map<String, String> map = nameLoad.getMap();
        assertThat(map).isEqualTo(expected);
    }
}