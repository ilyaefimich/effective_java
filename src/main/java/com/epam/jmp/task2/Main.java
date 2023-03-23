package com.epam.jmp.task2;

import com.epam.jmp.task2.entry.Entry;
import com.epam.jmp.task2.impl.LFU;
import com.epam.jmp.task2.impl.LRU;

/**
 * Main class for testing.
 */
public class Main {
    /**
     * Our main method.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Entry entry1 = new Entry("hello");
        Entry entry2 = new Entry("hello Ilya");
        LFU<Integer, String> lfuCache = new LFU<>();
        lfuCache.put(123, entry1.getValue());
        lfuCache.put(126, entry2.getValue());
        System.out.println(lfuCache.get(123));
        LRU<Integer, String> lruCache = new LRU<>();
        lruCache.put(123, entry1.getValue());
        lruCache.put(125, entry2.getValue());
        System.out.println(lruCache.get(123));
    }
}
