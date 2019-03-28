public class UnionFind {

    private int[] array;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= array.length) {
            throw new IllegalArgumentException("Input is not valid");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int root = parent(v1);
        while (root >= 0) {
            root = parent(root);
        }
        return 0 - root;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return array[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (sizeOf(v1) <= sizeOf(v2)) {
            array[find(v2)] += array[find(v1)];
            array[find(v1)] = find(v2);
        } else {
            array[find(v1)] += array[find(v2)];
            array[find(v2)] = find(v1);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        int n = parent(v1), root = v1;
        while (n >= 0) {
            root = n;
            n = parent(n);
        }
        if (parent(v1) < 0) {
            return root;
        }
        int otherRoot = root;
        while (parent(v1) != root) {
            int m = parent(v1), firstNode = v1;
            while (m == otherRoot) {
                firstNode = m;
                m = parent(m);
            }
            otherRoot = firstNode;
            array[firstNode] = root;
        }
        return root;
    }

}
