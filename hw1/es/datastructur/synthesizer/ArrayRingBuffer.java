package es.datastructur.synthesizer;

import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class ArraySetIterator implements Iterator<T> {
        int index;
        int ownFillCount;

        private ArraySetIterator(int i, int n) {
            index = i;
            ownFillCount = n;
        }

        @Override
        public boolean hasNext() {
            return ownFillCount != 0;
        }

        @Override
        public T next() {
            T res = rb[index];
            index++;
            ownFillCount--;
            if (index == capacity()) {
                index = 0;
            }
            return res;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Return the capacity of the buffer.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Return number of items currently in the buffer.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last++;
        fillCount++;
        if (last == capacity()) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T res = rb[first];
        first++;
        fillCount--;
        if (first == capacity()) {
            first = 0;
        }
        return res;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator(first, fillCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<Double> other = (ArrayRingBuffer<Double>) o;
        if (other.capacity() != capacity()) {
            return false;
        }
        if (other.fillCount() != fillCount) {
            return false;
        }
        ArrayRingBuffer<Double> newOther = new ArrayRingBuffer<>(other.capacity());
        Boolean ifEquals = true;
        for (int i = first; i < fillCount; i++) {
            Double item = other.dequeue();
            if (item != rb[i]) {
                ifEquals = false;
            }
            newOther.enqueue(item);
        }
        o = newOther;
        return ifEquals;
    }
}
