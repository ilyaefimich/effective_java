package com.epam.jmp.task2.service;

import java.util.Optional;

/**
 * Interface for the main caching operations. Includes two default methods "put" and "get".
 *
 * @param <K> key.
 * @param <V> value.
 */
public interface CacheService<K, V> {

    void put(K key, V value);

    Optional<V> get(K key);


}
