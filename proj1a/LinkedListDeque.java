public class LinkedListDeque<T> {
	private class TNode {
		private TNode prev;
		private TNode next;
		private T item;
		private TNode() {
			prev = this;
			next = this;
		}
		private TNode(T i, TNode p, TNode n) {
			item = i;
			prev = p;
			next = n;
		}

		/** Gets the item at the given index in a recursive way. */
		// A helper method.
		private T getRecursiveHelper(int index) {
			if (index == 0) {
				return this.item;
			}
			return this.next.getRecursiveHelper(index-1);
		}
	}

	private TNode sentinel;
	private int size;

	/** makes a list. */
	public LinkedListDeque() {
		sentinel = new TNode();
		size = 0;
	}

	public LinkedListDeque(T i) {
		sentinel = new TNode();
		sentinel.next = new TNode(i, sentinel, sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}

	/** Creates a deep copy of the other. */
	public LinkedListDeque(LinkedListDeque<T> other) {
		size = 0;
		sentinel = new TNode();
		TNode otherNode = other.sentinel.next;
		while (otherNode != other.sentinel) {
			addLast(otherNode.item);
			otherNode = otherNode.next;
		}
	}

	/** Adds the given item to the front of the list. */
	public void addFirst(T item) {
		sentinel.next = new TNode(item, sentinel, sentinel.next);
		sentinel.next.next.prev = sentinel.next;
		size++;
	}

	/** Adds the given item to the end of the list. */
	public void addLast(T item) {
		sentinel.prev = new TNode(item, sentinel.prev, sentinel);
		sentinel.prev.prev.next = sentinel.prev;
		size++;
	}

	/** Returns true if the list is empty. */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/** Returns the size of the list. */
	public int size() {
		return size;
	}

	/** Prints out all the item in the list. */
	public void printDeque() {
		TNode node = sentinel.next;
		while (node != sentinel.prev) {
			System.out.print(node.item + " ");
			node = node.next;
		}
		System.out.println(node.item);
	}

	/** Pop the first item of the list and returns it. */
	public T removeFirst() {
		if (isEmpty()) {
			return null;
		}
		T res = sentinel.next.item;
		sentinel.next = sentinel.next.next;
		sentinel.next.prev = sentinel;
		size--;
		return res;
	}

	/** Pop the last item of the list and returns it. */
	public T removeLast() {
		if (isEmpty()) {
			return null;
		}
		T res = sentinel.prev.item;
		sentinel.prev = sentinel.prev.prev;
		sentinel.prev.next = sentinel;
		size--;
		return res;
	}

	/** Gets the item at the given index. */
	public T get(int index) {
		TNode node = sentinel.next;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.item;
	}

	/** Gets the item at the given index in a recursive way. */
	public T getRecursive(int index) {
		return  sentinel.next.getRecursiveHelper(index);
	}

	public static void main(String[] args) {

	}
}