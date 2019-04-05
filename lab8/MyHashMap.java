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
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public MyHashMap(int initialSize) {
        size = 0;
        this.initialSize = initialSize;
        keySet = new HashSet<>();
        buckets = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        this.loadFactor = loadFactor;
        this.initialSize = initialSize;
        keySet = new HashSet<>();
        buckets = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        keySet.clear();
        buckets = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = new LinkedList<>();
        }
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
        int index = (key.hashCode() & 0x7FFFFFFF) % buckets.length;
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
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
        if (value == null) {
            return;
        }
        if (containsKey(key)) {
            int index = (key.hashCode() & 0x7FFFFFFF) % buckets.length;
            int n = buckets[index].size();
            for (int i = 0; i < n; i++) {
                Node node = buckets[index].get(i);
                if (node.key.equals(key)) {
                    node.value = value;
                    break;
                }
            }
        } else {
            ifOverLoad();
            int index = (key.hashCode() & 0x7FFFFFFF) % buckets.length;
            buckets[index].add(new Node(key, value));
            keySet.add(key);
            size++;
        }
    }

    /** Expend buckets if the radio of size and buckets' length larger than loadFactor. */
    private void ifOverLoad() {
        if ((double) size / buckets.length > loadFactor) {
            LinkedList<Node>[] newBuckets = new LinkedList[buckets.length * 2];
            for (int i = 0; i < newBuckets.length; i++) {
                newBuckets[i] = new LinkedList<>();
            }
            for (LinkedList<Node> list : buckets) {
                for (Node node : list) {
                    int index = (node.key.hashCode() & 0x7FFFFFFF) % newBuckets.length;
                    newBuckets[index].add(node);
                }
            }
            buckets = newBuckets;
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
