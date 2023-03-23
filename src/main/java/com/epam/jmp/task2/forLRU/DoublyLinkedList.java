package com.epam.jmp.task2.forLRU;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Linked list class which is using in LRU implementation class and LRUNode class.
 *
 * @param <T> formal parameter.
 */
public class DoublyLinkedList<T> {

    private final DummyNode<T> dummyNode;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private AtomicInteger size;

    public DoublyLinkedList() {
        this.dummyNode = new DummyNode<T>(this);
        clear();
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            head = dummyNode;
            tail = dummyNode;
            size = new AtomicInteger(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size() {
        this.lock.readLock().lock();
        return size.get();
    }

    public LinkedListNode<T> add(T value) {
        this.lock.writeLock().lock();
        head = new LRUNode<T>(value, head, this);
        if (tail.isEmpty()) {
            tail = head;
        }
        size.incrementAndGet();
        return head;
    }

    public LinkedListNode<T> removeTail() {
        this.lock.writeLock().lock();
        LinkedListNode<T> oldTail = tail;
        if (oldTail == head) {
            tail = head = dummyNode;
        } else {
            tail = tail.getPrev();
            oldTail.detach();
        }
        if (!oldTail.isEmpty()) {
            size.decrementAndGet();
        }
        return oldTail;
    }


    public LinkedListNode<T> moveToFront(LinkedListNode<T> node) {
        return node.isEmpty() ? dummyNode : updateAndMoveToFront(node, node.getElement());
    }

    public LinkedListNode<T> updateAndMoveToFront(LinkedListNode<T> node, T newValue) {
        this.lock.writeLock().lock();
        if (node.isEmpty() || (this != (node.getListReference()))) {
            return dummyNode;
        }
        detach(node);
        add(newValue);
        return head;
    }

    private void detach(LinkedListNode<T> node) {
        if (node != tail) {
            node.detach();
            if (node == head) {
                head = head.getNext();
            }
            size.decrementAndGet();
        } else {
            removeTail();
        }
    }
}
