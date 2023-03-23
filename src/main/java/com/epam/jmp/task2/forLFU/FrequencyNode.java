package com.epam.jmp.task2.forLFU;

/**
 * Class for LFU cache entry and frequency which is used in the LFU implementation class.
 */
public class FrequencyNode extends LFUNode {

    public int frequency;
    public NodeList lfuCacheEntryList;

    public FrequencyNode(int frequency) {
        this.frequency = frequency;
        lfuCacheEntryList = new NodeList();
    }

    public int hashCode() {
        return frequency * 31;
    }

    public String toString() {
        return Integer.toString(frequency);
    }
}