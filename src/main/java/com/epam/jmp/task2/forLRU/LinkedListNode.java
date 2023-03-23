package com.epam.jmp.task2.forLRU;

/**
 * Simple interface where all main methods with nodes declared.
 *
 * @param <V> element.
 */
public interface LinkedListNode<V> {

    boolean hasElement();

    boolean isEmpty();

    V getElement() throws NullPointerException;

    void detach();

    DoublyLinkedList<V> getListReference();

    LinkedListNode<V> getPrev();

    LinkedListNode<V> setPrev(LinkedListNode<V> prev);

    LinkedListNode<V> getNext();

    LinkedListNode<V> setNext(LinkedListNode<V> next);

    LinkedListNode<V> search(V value);
}

