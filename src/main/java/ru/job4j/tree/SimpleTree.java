package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean rsl = false;
        Optional<Node<E>> childElement = findBy(child);
        if (childElement.isEmpty()) {
            Optional<Node<E>> parentElement = findBy(parent);
            if (parentElement.isPresent()) {
                Node<E> childNode = new Node<>(child);
                parentElement.get().children.add(childNode);
                rsl = true;
            }
        }
        return rsl;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Predicate<Node<E>> predicate = (find) -> find.value.equals(value);
        return findByPredicate(predicate);
    }

    @Override
    public boolean isBinary() {
        boolean rsl = false;
        Predicate<Node<E>> predicate = (value) -> value.children.size() > 2;
        if (findByPredicate(predicate).isEmpty()) {
            rsl = true;
        }
        return rsl;
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (condition.test(el)) {
                rsl = Optional.of(el);
                break;
            }
            data.addAll(el.children);
        }
        return rsl;
    }
}