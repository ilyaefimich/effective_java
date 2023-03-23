package com.epam.jmp.task2.forLRU;

/**
 * Class which presents basic LRU cache element and main operations with that element.
 *
 * @param <K> key.
 * @param <V> value.
 */
public class LRUCacheElement<K, V> {

    private K key;
    private V value;

    public LRUCacheElement(K key, V value) {
        this.value = value;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String toString() {
        return "[" + key.toString() + "," + value.toString() + "]";
    }
}
