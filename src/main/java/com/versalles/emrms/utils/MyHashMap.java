package com.versalles.emrms.utils;

import java.io.Serializable;
import  com.versalles.emrms.structures.SimpleLinkedList;

/**
 *
 * @author JUANM
 */
public class MyHashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_CAPACITY = 16;
    private SimpleLinkedList<Entry<K, V>>[] table;

    public MyHashMap() {
        table = new SimpleLinkedList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = new SimpleLinkedList<>();
        }
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % DEFAULT_CAPACITY);
    }

    public void put(K key, V value) {
        int index = hash(key);
        SimpleLinkedList<Entry<K, V>> bucket = table[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
    }

    public V get(K key) {
        int index = hash(key);
        SimpleLinkedList<Entry<K, V>> bucket = table[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        SimpleLinkedList<Entry<K, V>> bucket = table[index];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getKey().equals(key)) {
                bucket.remove(i);
                return;
            }
        }
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        SimpleLinkedList<Entry<K, V>> bucket = table[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (SimpleLinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    static class Entry<K, V> implements Serializable {

        private static final long serialVersionUID = 1L;
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
