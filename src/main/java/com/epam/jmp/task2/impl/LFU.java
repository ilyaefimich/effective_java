package com.epam.jmp.task2.impl;

import com.epam.jmp.task2.forLFU.FrequencyNode;
import com.epam.jmp.task2.forLFU.LFUCacheElement;
import com.epam.jmp.task2.forLFU.NodeList;
import com.epam.jmp.task2.service.CacheService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class for implementation of CacheService interface using LFU caching.
 *
 * @param <K> key.
 * @param <V> value.
 */
public class LFU<K, V> implements CacheService<K, V> {

    static final int MAXSIZE = 100000;

    Map<K, LFUCacheElement<K, V>> kvStore = new HashMap<>();

    Map<Integer, FrequencyNode> frequencyMap = new HashMap<>();

    NodeList freqList = new NodeList();

    int size;

    public LFU() {
    }

    public void checkBeforeDelete(LFUCacheElement<K, V> entry) {
        if (!kvStore.containsKey(entry.key)){
            return;
        }
        kvStore.remove(entry.key);
        entry.frequencyNode.lfuCacheEntryList.remove(entry);
        if (entry.frequencyNode.lfuCacheEntryList.length <= 0) {
            frequencyMap.remove(entry.frequencyNode.frequency);
            freqList.remove(entry.frequencyNode);
        }
        size--;
    }

    public void delete(LFUCacheElement<K, V> entry) {
        checkBeforeDelete(entry);
    }

    public FrequencyNode getFrequencyNode(int frequency) {
        if (!frequencyMap.containsKey(frequency - 1) &&
                !frequencyMap.containsKey(frequency) &&
                frequency != 1) {
            System.out.println("Request for Frequency Node " + frequency +
                    " But " + frequency + " or " + (frequency - 1) +
                    " Doesn't exist");
            return null;
        }
        if (!frequencyMap.containsKey(frequency)) {
            FrequencyNode newFrequencyNode = new FrequencyNode(frequency);
            if (frequency != 1)
                freqList.insertAfter(frequencyMap.get(frequency - 1),
                        newFrequencyNode);
            else
                freqList.prepend(newFrequencyNode);
            frequencyMap.put(frequency, newFrequencyNode);
        }
        return frequencyMap.get(frequency);
    }

    @Override
    public void put(K key, V value) {
        FrequencyNode newFrequencyNode = null;
        if (kvStore.containsKey(key)) {
            /* Remove old key if exists */
            newFrequencyNode = getFrequencyNode(kvStore.get(key).frequencyNode.frequency + 1);
            delete(kvStore.get(key));
        }
        if (size == MAXSIZE) {
            /* If cache size if full remove first element from freq list */
            FrequencyNode fNode = (FrequencyNode) freqList.head;
            LFUCacheElement<K, V> entry = (LFUCacheElement<K, V>) fNode.lfuCacheEntryList.head;
            delete(entry);
            System.out.println("Cache full. Removed entry " + entry);
        }
        if (newFrequencyNode == null)
            newFrequencyNode = getFrequencyNode(1);
        LFUCacheElement<K, V> entry = new LFUCacheElement<K, V>(key, value,
                newFrequencyNode);
        kvStore.put(key, entry);
        newFrequencyNode.lfuCacheEntryList.append(entry);
        size++;
        System.out.println("Set new " + entry + " entry, current cache size: " + size + " max cache " +
                "size: " + MAXSIZE);
    }

    @Override
    public Optional<V> get(K key) {
        if (!kvStore.containsKey(key)) {
            return Optional.empty();
        }
        LFUCacheElement<K, V> entry = kvStore.get(key);
        FrequencyNode newFrequencyNode =
                getFrequencyNode(entry.frequencyNode.frequency + 1);
        entry.frequencyNode.lfuCacheEntryList.remove(entry);
        newFrequencyNode.lfuCacheEntryList.append(entry);
        if (entry.frequencyNode.lfuCacheEntryList.length <= 0) {
            frequencyMap.remove(entry.frequencyNode.frequency);
            freqList.remove(entry.frequencyNode);
        }
        entry.frequencyNode = newFrequencyNode;
        return Optional.of(entry.value);
    }
}
