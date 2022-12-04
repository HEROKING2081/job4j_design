package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean rsl = false;
        int i = index(key);
        if (table[i] == null) {
            table[i] = new MapEntry<>(key, value);
            rsl = true;
            count++;
            modCount++;
        }
        if ((double) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        return rsl;
    }

    private int hashCode(K key) {
        return key == null ? 0 : key.hashCode();
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private int index(K key) {
        return indexFor(hash(hashCode(key)));
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] temp = new MapEntry[capacity];
        for (MapEntry<K, V> e : table) {
            if (e != null) {
                int i = index(e.key);
                temp[i] = e;
            }
        }
        table = temp;
    }

    private boolean equals(K key, MapEntry<K, V> e) {
        int i = index(key);
        e = table[i];
        return e != null && hashCode(e.key) == hashCode(key)
                && Objects.equals(key, e.key);
    }

    @Override
    public V get(K key) {
        V rsl = null;
        int i = index(key);
        if (equals(key, table[i])) {
            rsl = table[i].value;
        }
        return rsl;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int i = index(key);
        if (equals(key, table[i])) {
            table[i] = null;
            rsl = true;
            count++;
            modCount++;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            int point = 0;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (point < capacity && table[point] == null) {
                    point++;
                }
                return point < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[point++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

}
