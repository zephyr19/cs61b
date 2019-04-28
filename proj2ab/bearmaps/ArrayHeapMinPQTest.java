package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import bearmaps.PrintHeapDemo.*;

public class ArrayHeapMinPQTest {
    @Test
    /* Test add() && changePriority() && getSmallest() && removeSmallest().*/
    public void test1() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("hello", 5);
        pq.add("larger", 7);
        pq.add("me", 2);
        assertEquals("me", pq.getSmallest());
        pq.changePriority("me", 10);
        assertEquals("hello", pq.removeSmallest());
        assertEquals("larger", pq.removeSmallest());
        assertEquals("me", pq.removeSmallest());
        assertEquals(0, pq.size());
    }

    @Test
    /* Test with 123456789.*/
    public void test2() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("2", 5);
        pq.add("7", 7);
        pq.add("5", 2);
        pq.add("1", 0.01);
        pq.add("3", 2.7);
        pq.add("4", 4.5);
        pq.add("6", 6);
        pq.add("8", 9);
        pq.add("9", 9.5);
        pq.changePriority("2", 2.5);
        pq.changePriority("5", 5);
        assertEquals("1", pq.removeSmallest());
        assertEquals("2", pq.removeSmallest());
        assertEquals("3", pq.removeSmallest());
        assertEquals("4", pq.removeSmallest());
        assertEquals("5", pq.removeSmallest());
        assertEquals("6", pq.removeSmallest());
        assertEquals("7", pq.removeSmallest());
        assertEquals("8", pq.removeSmallest());
        assertEquals("9", pq.removeSmallest());
        assertEquals(0, pq.size());
    }

    @Test
    public void AddRemoveSmallestTest() {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            pq.add(i, i);
        }
        for (int i = 0; i < 100000; i++) {
            assertEquals(i, (int)pq.removeSmallest());
        }
    }

    @Test
    public void ChangePriorityTest() {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            pq.add(i, 100000 - i);
        }
        for (int i = 0; i < 100000; i++) {
            pq.changePriority(i, i);
        }
        for (int i = 0; i < 100000; i++) {
            assertEquals(i, (int)pq.removeSmallest());
        }
    }

    @Test
    public void print() {
        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        pq.add(5, 5);
        pq.add(7, 7);
        pq.add(2, 2);
        PrintHeapDemo.printSimpleHeapDrawing(pq.array);
    }
}
