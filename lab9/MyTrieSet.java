import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 256;   //extended ASCII

    private Node root;  //root of trie

    public class Node {
        private boolean mark;
        private Node[] next;

        public Node() {
            mark = false;
            next = new Node[R];
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
        return get(node.next[key.charAt(index)], key, index + 1);
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (!contains(key)) {
            root = add(root, key, 0);
        }
    }

    private Node add(Node node, String key, int index) {
        if (node == null) {
            node = new Node();
        }
        if (index == key.length()) {
            node.mark = true;
            return node;
        }
        char n = key.charAt(index);
        node.next[n] = add(node.next[n], key, index + 1);
        return node;
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
//        keysWithPrefix(prefix, res, node);
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
