package bearmaps;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class PriorityNode {
        T item;
        double priority;

        private PriorityNode(T e, double p) {
            item = e;
            priority = p;
        }
    }

    private PriorityNode[] array;    //********************
    private Map<T, Integer> key;
    private int size;

    public ArrayHeapMinPQ() {
        array = new ArrayHeapMinPQ.PriorityNode[8];
        key = new HashMap<>();
        size = 0;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (size > array.length * 3 / 4) {
            resize();
        }
        array[size] = new PriorityNode(item, priority);
        key.put(item, size);
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
        T item1 = array[i].item;
        T item2 = array[j].item;
        key.remove(item1);
        key.put(item1, i);
        key.remove(item2);
        key.put(item2, j);
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
        return key.containsKey(item);
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
        key.remove(res);
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
        int rightChild =  (i + 1) * 2;
        int leftChild = rightChild - 1;
        if (leftChild >= size) {
            return i;       // avoid NullPoint if both children are null.
        } else if (rightChild >= size) {
            return leftChild;   // only leftChild is valid.
        } else {
            // return the smaller priority one
            if (smaller(leftChild, rightChild)) {
                return leftChild;
            } else {
                return rightChild;
            }
        }
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
            throw new NoSuchElementException();
        }
        int index = key.get(item);
        array[index].priority = priority;
        swim(index);
        index = key.get(item);
        sink(index);
    }
}
