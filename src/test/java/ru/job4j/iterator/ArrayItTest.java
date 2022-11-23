package ru.job4j.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArrayItTest {

    @Test
    public void whenMultiCallhasNextThenTrue() {
        ArrayIt it = new ArrayIt(
                new int[] {1, 2, 3}
        );
        assertThat(it.hasNext(), is(true));
        assertThat(true, is(true));
    }

    @Test
    public void whenReadSequence() {
        ArrayIt it = new ArrayIt(
                new int[] {1, 2, 3}
        );
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextFromEmpty() {
        ArrayIt it = new ArrayIt(
                new int[] {}
        );
        it.next();
    }
}