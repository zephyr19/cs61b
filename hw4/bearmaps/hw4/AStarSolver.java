package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.*;

import static bearmaps.hw4.SolverOutcome.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private Stack<Vertex> solutionStack; // help to correct the order.
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;
    private ArrayHeapMinPQ<Vertex> pq; // priority p = distTo[v] + h(v, goal)
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    /**
     * Constructor which finds the solution, computing everything necessary for all other methods
     * to return their results in constant time. Note that timeout passed in is in seconds.
     * Check to see if run out of time every time dequeue.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        long endTime = System.currentTimeMillis() + (long) (timeout * 1000);
        distTo = new HashMap<>();
        distTo.put(start, 0.0);
        edgeTo = new HashMap<>();
        solution = new ArrayList<>();
        solutionStack = new Stack<>();
        numStatesExplored = 0;
        pq = new ArrayHeapMinPQ<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        List<bearmaps.hw4.WeightedEdge<Vertex>> neighbors;
        while (!(pq.size() == 0 || pq.getSmallest().equals(end))) {
            Vertex vertex = pq.removeSmallest();
            if (System.currentTimeMillis() >= endTime) {
                break;
            }
            neighbors = input.neighbors(vertex);
            for (bearmaps.hw4.WeightedEdge<Vertex> vertexWeightedEdge : neighbors) {
                relax(input, vertexWeightedEdge);
            }
            numStatesExplored++;
        }
        if (System.currentTimeMillis() >= endTime) {
            outcome = TIMEOUT;
            explorationTime = timeout;
            solution.clear();
            solutionWeight = 0;
        } else if (pq.size() == 0) {
            outcome = UNSOLVABLE;
            explorationTime = (System.currentTimeMillis() + timeout * 1000 - endTime) / 1000;
            solution.clear();
            solutionWeight = 0;
        } else {
            outcome = SOLVED;
            explorationTime = (System.currentTimeMillis() + timeout * 1000 - endTime) / 1000;
            solutionWeight = distTo.get(end);
            Vertex v = end;
            solutionStack.push(v);
            while (true) {
                v = edgeTo.get(v);
                if (!start.equals(v)) {
                    solutionStack.push(v);
                } else {
                    break;
                }
            }
            solutionStack.push(start);
            while (!solutionStack.isEmpty()) {
                solution.add(solutionStack.pop());
            }
        }
    }

    private void relax(AStarGraph<Vertex> input, bearmaps.hw4.WeightedEdge<Vertex> vertex) {
        Vertex from = vertex.from();
        Vertex to = vertex.to();
        double weight = vertex.weight();
        if (!distTo.containsKey(to)) {
            distTo.put(to, 10e8);
        }
        if (distTo.get(to) > distTo.get(from) + weight) {
            distTo.put(to, distTo.get(from) + weight);
            edgeTo.put(to, from);
            if (pq.contains(to)) {
                pq.changePriority(to, distTo.get(to) + input.estimatedDistanceToGoal(from, to));
            } else {
                pq.add(to, distTo.get(to) + input.estimatedDistanceToGoal(from, to));
            }
        }
    }

    /**
     * Should be SOLVED if the AStarSolver was able to complete all work in the time given
     * UNSOLVABLE if the priority queue became empty
     * TIMEOUT if the solver ran out of time
     *
     * @return SOLVED or UNSOLVABLE or TIMEOUT
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /**
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE
     *
     * @return a list or null
     */
    @Override
    public List<Vertex> solution() {
        return solution;
    }

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE
     *
     * @return total weight or 0
     */
    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /**
     * @return The total number of priority queue dequeue operations
     */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /**
     * @return The total time spent in seconds by the constructor
     */
    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
