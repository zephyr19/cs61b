package bearmaps;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class PriorityNode {
        T item;
        double priority;

        private PriorityNode(T e, double p) {
            item = e;
            priority = p;
        }
    }

    private PriorityNode[] array;
    private Set<T> key;
    private int size;

    public ArrayHeapMinPQ() {
        array = new ArrayHeapMinPQ.PriorityNode[8];
        key = new LinkedHashSet<>();
        size = 0;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("The item was added once. You can use changePriority method");
        }
        if (size > array.length * 3 / 4) {
            resize();
        }
        array[size] = new PriorityNode(item, priority);
        key.add(item);
        swim(size);
        size++;
    }

    /* If size is larger than 3/4s array.length. resize the array in double size. */
    private void resize() {
        PriorityNode[] temp = new ArrayHeapMinPQ.PriorityNode[array.length * 2];
        System.arraycopy(array, 0, temp, 0, array.length);
        array = temp;
    }

    /* Help item go to the right position. */
    private void swim(int i) {
        while (i > 0 && smaller(i, parent(i))) {
            int parent = parent(i);
            exchange(i, parent);
            i = parent;
        }
    }

    /* Exchange two items with the given index. */
    private void exchange(int i, int j) {
        PriorityNode swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    /* Return true if i is less priority. */
    private boolean smaller(int i, int j) {
        return array[i].priority - array[j].priority < 0;
    }

    /* Return the parent index of i. */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return key.contains(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty!");
        }
        return array[0].item;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        T res = getSmallest();
        array[0] = array[size - 1];
        size--;
        sink(0);
        return res;
    }

    /* Help item go to the right position. */
    private void sink(int i) {
        while (i < size && smaller(child(i), i)) {
            int child = child(i);
            exchange(child, i);
            i = child;
        }
    }

    /* Return a child of i. */
    private int child(int i) {
        return (i + 1) * 2;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Returns true if the number of items in the PQ equals to 0. */
    private boolean isEmpty() {
        return size == 0;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("The item is not in the priority queue. You should add it first.");
        }
    }
}
