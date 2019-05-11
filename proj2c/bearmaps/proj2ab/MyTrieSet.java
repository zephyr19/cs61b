package bearmaps.proj2ab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;  //root of trie

    public class Node {
        private boolean mark;
        private HashMap<Character, Node> next;

        public Node() {
            mark = false;
            next = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node();
    }
    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else {
            return get(key);
        }
    }

    private boolean get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return false;
        } else {
            return x.mark;
        }
    }

    private Node get(Node node, String key, int index) {
        if (node == null) {
            return null;
        }
        if (index == key.length()) {
            return node;
        }
        return get(node.next.get(key.charAt(index)), key, index + 1);
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (!contains(key)) {
            Node node = root;
            int i = 0;
            for (int n = key.length(); i < n; i++) {
                char ch = key.charAt(i);
                if (!node.next.containsKey(ch)) {
                    break;
                }
                node = node.next.get(ch);
            }
            add(node, key, i);
        }
    }

    private void add(Node node, String key, int index) {
        if (node == null) {
            node = new Node();
        }
        if (index == key.length()) {
            node.mark = true;
            return;
        }
        char n = key.charAt(index);
        node.next.put(n, new Node());
        add(node.next.get(n), key, index + 1);
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null || prefix.length() < 1) {
            throw new IllegalArgumentException();
        }
        Node node = get(root, prefix, 0);
        if (node == null) {
            return null;
        }
        List<String> res = new LinkedList<>();
        keysWithPrefixHelper(prefix, res, node);
        return res;
    }

    private void keysWithPrefixHelper(String key, List<String> list, Node node) {
        for (char ch : node.next.keySet()) {
            keysWithPrefixHelper(key + ch, list, node.next.get(ch));
        }
        if (node.mark) {
            list.add(key);
        }
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
