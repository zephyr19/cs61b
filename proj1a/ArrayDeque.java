public class ArrayDeque<T> {

    private T[] items;
    private int len;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * Creates a new deque.
     */
    public ArrayDeque() {
        len = 8;
        items = (T[]) new Object[len];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    /**
     * Creates a deep copy of the other.
     */
    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        len = other.len;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        items = (T[]) new Object[len];
        if (nextFirst == nextLast) {
            System.arraycopy(other.items, 0, items, 0, len);
        } else if (nextFirst < nextLast) {
            System.arraycopy(other.items, nextFirst + 1,
                    items, nextFirst + 1, nextLast - nextFirst - 2);
        } else {
            System.arraycopy(other.items, 0, items, 0, nextLast);
            System.arraycopy(other.items, nextFirst + 1,
                    items, nextFirst + 1, len - 1 - nextFirst);
        }
    }

    /**
     * Resize the deque to a Two-thirds space.
     */
    private void isExpandList() {
        if (size > len * 0.9) {
            int prevLen = len;
            len = len / 2 * 3;
            T[] newItems = (T[]) new Object[len];
            if (nextFirst < nextLast) {
                System.arraycopy(items, nextFirst + 1,
                        newItems, nextFirst + 1, nextLast - nextFirst - 2);
            } else {
                System.arraycopy(items, 0, newItems, 0, nextLast);
                int prevNextFirst = nextFirst;
                nextFirst = len - prevLen + 1 + nextFirst;
                System.arraycopy(items, prevNextFirst + 1,
                        newItems, nextFirst + 1, prevLen - 1 - prevNextFirst);
            }
            items = newItems;
        }
    }

    /**
     * Resize the deque to a half space.
     */
    private void isShrinkList() {
        if (len * 2 / 3 > size) {
            int prevLen = len;
            len = len * 2 / 3;
            T[] newItems = (T[]) new Object[len];
            if (nextFirst < nextLast) {
                System.arraycopy(items, nextFirst + 1, newItems, 1, nextLast - nextFirst - 2);
                nextLast = nextLast - nextFirst - 1;
                nextFirst = 0;
            } else {
                System.arraycopy(items, 0, newItems, 0, nextLast);
                int prevNextFirst = nextFirst;
                nextFirst = len - prevLen + prevNextFirst;
                System.arraycopy(items, prevNextFirst + 1,
                        newItems, nextFirst + 1, prevLen - 1 - prevNextFirst);
            }
            items = newItems;
        }
    }

    /**
     * Adds the given item at the front of the deque.
     */
    public void addFirst(T item) {
        isExpandList();
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = len - 1;
        } else {
            nextFirst--;
        }
        size++;
    }

    /**
     * Adds the given item at the end of the deque.
     */
    public void addLast(T item) {
        isExpandList();
        items[nextLast] = item;
        if (nextLast == len - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }
        size++;
    }

    /**
     * Returns true if the deque is empty.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints out all the item in the deque.
     */
    public void printDeque() {
        int index = nextFirst + 1;
        if (index == len) {
            index = 0;
        }
        while (index != nextLast - 1) {
            if (index == len) {
                index = 0;
            }
            System.out.print(items[index] + " ");
            index++;
        }
        System.out.println(items[index]);
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size <= 8) {
            if (isEmpty()) {
                return null;
            }
        } else {
            isShrinkList();
        }
        if (nextFirst == len - 1) {
            nextFirst = 0;
        } else {
            nextFirst++;
        }
        T res = items[nextFirst];
        size--;
        return res;
    }

    /**
     * Removes and returns the item at the end of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size <= 8) {
            if (isEmpty()) {
                return null;
            }
        } else {
            isShrinkList();
        }
        if (nextLast == 0) {
            nextLast = len - 1;
        } else {
            nextLast--;
        }
        T res = items[nextLast];
        size--;
        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        int i = nextFirst + index + 1;
        if (i <= len - 1) {
            return items[i];
        } else {
            i -= len;
            return items[i];
        }
    }
}
