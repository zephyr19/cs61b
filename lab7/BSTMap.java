import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size;

        private Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private Node root;
    private Set<K> set = new TreeSet<>();

    /** Creates an empty BSTMap. */
    public BSTMap() {
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return getHelper(root, key);
    }

    public V getHelper(Node p, K key) {
        if (key == null) throw new IllegalArgumentException("calls get with a null key");
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) return getHelper(p.left, key);
        else if (cmp > 0) return getHelper(p.right, key);
        else return p.value;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        if (root == null) return 0;
        return root.size;
    }

    /** Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put with a null key");
        if (value == null) throw new IllegalArgumentException("calls put with a null value");
        root = putHelper(root, key, value);
    }

    private Node putHelper(Node p, K key, V value) {
        if (p == null) return new Node(key, value, 1);
        int cmp = key.compareTo(p.key);
        if (cmp < 0) p.left = putHelper(p.left, key, value);
        else if (cmp > 0) p.right = putHelper(p.right, key, value);
        else p.value = value;
        p.size++;
        return p;
    }

    /** Prints out BSTMap in order of increasing key. */
    public void printInOrder() {
        printHelper(root);
    }

    private void printHelper(Node p) {
        if (p == null) return;
        printHelper(p.left);
        System.out.print(get(p.key) + " ");
        printHelper(p.right);
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        keySetHelper(root);
        return set;
    }

    private void keySetHelper(Node p) {
        if (p == null) return;
        keySetHelper(p.left);
        set.add(p.key);
        keySetHelper(p.right);
    }

    /** Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        V value = get(key);
        return remove(key, value);
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        if (!value.equals(get(key))) return null;
        root = removeHelper(root, root, key, value);
        return value;
    }

    private Node removeHelper(Node p, Node preP, K key, V value) {
        int cmp = key.compareTo(p.key);
        if (cmp < 0) p.left = removeHelper(p.left, p, key, value);
        else if (cmp > 0) p.right = removeHelper(p.right, p, key, value);
        else {
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;
            Node min = min(p.right);
            min.left = p.left;
            min.right = removeMin(p.right);
        }
        p.size--;
        return p;
    }

    private Node min(Node p) {
        if (p.left != null) return min(p.left);
        return p;
    }

    private Node removeMin(Node p) {
        if (p.left == null) return p.right;
        p.left = removeMin(p.left);
        p.size--;
        return p;
    }

    public Iterator<K> iterator() {
        return set.iterator();
    }
}
