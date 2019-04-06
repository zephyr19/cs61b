import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver extends PriorityQueue {

    private PriorityQueue<Flight> pq;

    public FlightSolver(ArrayList<Flight> flights) {
        pq = new PriorityQueue<>();
        pq.addAll(flights);
    }

    public int solve() {
        Flight res = pq.peek();
        if (res == null) {
            throw new IllegalCallerException();
        }
        return res.passengers();
    }

    @Override
    public Comparator<Flight> comparator() {
        return new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return o1.passengers() - o2.passengers();
            }
        };
    }
}
