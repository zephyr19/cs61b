import org.junit.Test;
import static org.junit.Assert.*;
public class TestArrayDequeGold {
    @Test
    public void randomlyCall() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();
        for (Integer i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                sol1.addLast(i);
            } else {
                sad1.addFirst(i);
                sol1.addFirst(i);
            }
        }
        for (int i = 0; i < 11520; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addLast(i);
                sol1.addLast(i);
            } else if (numberBetweenZeroAndOne < 0.50){
                sad1.addFirst(i);
                sol1.addFirst(i);
            } else if (numberBetweenZeroAndOne < 0.75) {
                assertEquals(sol1.removeFirst(), sad1.removeFirst());
            }
            else {
                assertEquals(sol1.removeLast(), sad1.removeLast());
            }
        }
    }
}
