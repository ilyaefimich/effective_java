package com.epam.jmp.task2.forLFU;

/**
 * Class which presents basic LFU cache element and main operations with that element.
 *
 * @param <K> key.
 * @param <V> value.
 */
public class LFUCacheElement<K, V> extends LFUNode {

    public K key;
    public V value;
    public FrequencyNode frequencyNode;

    public LFUCacheElement(K key, V value, FrequencyNode frequencyNode) {
        this.key = key;
        this.value = value;
        this.frequencyNode = frequencyNode;
    }

    public int hashCode() {
        return key.hashCode() * 31 + value.hashCode() * 17;
    }

    public String toString() {
        return "[" + key.toString() + "," + value.toString() + "]";
    }
}
