package com.epam.jmp.task2.entry;

/**
 * Simple class for entry with one String field which includes String value;
 */
public class Entry {

    public String value;

    public Entry(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "value='" + value + '\'' +
                '}';
    }
}
