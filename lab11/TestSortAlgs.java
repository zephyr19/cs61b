import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        assertTrue(isSorted(QuickSort.quickSort(tas)));
        /* My approach to check if list is sorted. */
//        tas = QuickSort.quickSort(tas);
//        while (tas.size() > 1) {
//            assertTrue(tas.dequeue().compareTo(tas.peek()) <= 0);
//        }
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        assertTrue(isSorted(MergeSort.mergeSort(tas)));
        /* My approach to check if list is sorted. */
//        tas = MergeSort.mergeSort(tas);
//        while (tas.size() > 1) {
//            assertTrue(tas.dequeue().compareTo(tas.peek()) <= 0);
//        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items A Queue of items
     * @return true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
