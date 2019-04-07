package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void test1() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("hello", 5);
        pq.add("larger", 7);
        pq.add("me", 2);
        assertEquals("me", pq.getSmallest());
    }
}
