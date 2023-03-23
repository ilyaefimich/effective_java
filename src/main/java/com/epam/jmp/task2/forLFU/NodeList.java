package com.epam.jmp.task2.forLFU;

/**
 * Class for the main operations with LFU node.
 */
public class NodeList {

    public LFUNode head;
    public LFUNode tail;
    public int length;

    public NodeList() {
    }

    public void prepend(LFUNode node) {
        if (head == null) {
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
        }
        head = node;
        length++;
    }

    public int getLength() {
        return length;
    }

    public void append(LFUNode node) {
        if (tail == null) {
            prepend(node);
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            length++;
        }
    }

    public void insertAfter(LFUNode position, LFUNode node) {
        if (position == tail) {
            append(node);
        } else {
            node.next = position.next;
            node.prev = position;
            position.next = node;
            node.next.prev = node;
            length++;
        }
    }

    public void remove(LFUNode node) {
        if (node == tail && node == head) { /* single node in LinkedList */
            head = null;
            tail = null;
        } else if (node == tail) {
            tail = tail.prev;
        } else if (node == head) {
            head = head.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        length--;
    }
}

