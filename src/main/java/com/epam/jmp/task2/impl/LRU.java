package com.epam.jmp.task2.impl;

import com.epam.jmp.task2.forLRU.DoublyLinkedList;
import com.epam.jmp.task2.forLRU.LRUCacheElement;
import com.epam.jmp.task2.forLRU.LinkedListNode;
import com.epam.jmp.task2.service.CacheService;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class for implementation of CacheService interface using LRU caching.
 *
 * @param <K> key.
 * @param <V> value.
 */
public class LRU<K, V> implements CacheService<K, V> {
    /* Capacity of cache */
    static final int MAXSIZE = 100000;
    private final Map<K, LinkedListNode<LRUCacheElement<K, V>>> linkedListNodeMap;
    private final DoublyLinkedList<LRUCacheElement<K, V>> doublyLinkedList;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    /* current size of Cache */
    public int size;

    public LRU() {
        this.linkedListNodeMap = new ConcurrentHashMap<>(MAXSIZE);
        this.doublyLinkedList = new DoublyLinkedList<>();
    }

    /*Method for putting new element for caching using key and value*/
    @Override
    public void put(K key, V value) {
        this.lock.writeLock().lock();
        LRUCacheElement<K, V> item = new LRUCacheElement<K, V>(key, value);
        LinkedListNode<LRUCacheElement<K, V>> newNode;
        if (this.linkedListNodeMap.containsKey(key)) {
            LinkedListNode<LRUCacheElement<K, V>> node = this.linkedListNodeMap.get(key);
            newNode = doublyLinkedList.updateAndMoveToFront(node, item);
        } else {
            if (this.size() >= MAXSIZE) {
                this.evictElement();
            }
            newNode = this.doublyLinkedList.add(item);
        }
        if (newNode.isEmpty()) {
            return;
        }
        this.linkedListNodeMap.put(key, newNode);
        size++;
        System.out.println("Set new " + item + " entry, current cache size: " +
                size() + " max cache size: " + MAXSIZE);
    }


    /*Method for getting cached element using key*/
    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        LinkedListNode<LRUCacheElement<K, V>> linkedListNode = this.linkedListNodeMap.get(key);
        if (linkedListNode != null && !linkedListNode.isEmpty()) {
            linkedListNodeMap.put(key, this.doublyLinkedList.moveToFront(linkedListNode));
            return Optional.of(linkedListNode.getElement().getValue());
        }
        return Optional.empty();
    }


    public int size() {
        this.lock.readLock().lock();
        return doublyLinkedList.size();
    }


    /* Method for the element eviction*/
    private void evictElement() {
        this.lock.writeLock().lock();
        LinkedListNode<LRUCacheElement<K, V>> linkedListNode = doublyLinkedList.removeTail();
        if (linkedListNode.isEmpty()) {
            return;
        }
        linkedListNodeMap.remove(linkedListNode.getElement().getKey());
    }
}


