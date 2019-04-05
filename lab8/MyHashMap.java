import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
//    private class HashMapIterable<K> implements Iterator<K> {
//        MyHashMap<K, V> hashMap
//        @Override
//        public boolean hasNext() {
//            return false;
//        }
//
//        @Override
//        public K next() {
//            return null;
//        }
//    }

    private class Node {
        K key;
        V value;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Set<K> keySet;
    private LinkedList<Node>[] buckets;
    private int size;
    private int initialSize = 16;
    private double loadFactor = 0.75;

    public MyHashMap() {
        size = 0;
        keySet = new HashSet<>();
        buckets = new LinkedList[initialSize];
    }

    public MyHashMap(int initialSize) {
        size = 0;
        this.initialSize = initialSize;
        keySet = new HashSet<>();
        buckets = new LinkedList[initialSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        this.loadFactor = loadFactor;
        this.initialSize = initialSize;
        keySet = new HashSet<>();
        buckets = new LinkedList[initialSize];
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        keySet.clear();
        buckets = new LinkedList[initialSize];
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int index = key.hashCode() / buckets.length;
        int n = buckets[index].size();
        for (int i = 0; i < n; i++) {
            Node node = buckets[index].get(i);
            if (node.key == key) {
                return node.value;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int index = key.hashCode() / buckets.length;
        if (containsKey(key)) {
            int n = buckets[index].size();
            for (int i = 0; i < n; i++) {
                Node node = buckets[index].get(i);
                if (node.key == key) {
                    node.value = value;
                    break;
                }
            }
        } else {
            buckets[index].add(new Node(key, value));
            keySet.add(key);
            size++;
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}
