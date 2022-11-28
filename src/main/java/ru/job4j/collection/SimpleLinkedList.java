package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
    }
    private int size = 0;
    private int modCount = 0;
    private final Node<E>[] container = new Node[10];

    @Override
    public void add(E value) {
        Node<E> el = new Node();
        el.item = value;
        if (size != 0) {
            el.prev = container[size - 1];
            container[size - 1].next =el;
        }
        container[size] = el;
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return container[index].item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            final int expectedModCount = modCount;
            int cursor = 0;

            @Override
            public boolean hasNext() {
                while (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[cursor++].item;
            }
        };
    }
}
