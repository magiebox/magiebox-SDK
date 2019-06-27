package com.tszj.chain.sdk.utils;


import java.util.TreeMap;

public class QueueMap<K, V> extends TreeMap<K, V> {

    int capacity;

    public QueueMap(int maxCapacity) {
        super();
        this.capacity = maxCapacity;

    }

    public synchronized V offer(K key, V value) {
        if (this.size() >= capacity) {
            remove(this.firstKey());

        }
        return put(key,value);
    }

}
