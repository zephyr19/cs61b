package bearmaps.hw4.integerhoppuzzle;

import bearmaps.hw4.AStarSolver;
import bearmaps.hw4.LazySolver;
import bearmaps.hw4.ShortestPathsSolver;
import bearmaps.hw4.SolutionPrinter;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Showcases how the AStarSolver can be used for solving integer hop puzzles.
 * NOTE: YOU MUST REPLACE LazySolver WITH AStarSolver OR THIS DEMO WON'T WORK!
 * Created by hug.
 */
public class DemoIntegerHopPuzzleSolution {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int start = StdRandom.uniform(0, 1000);
            int goal = StdRandom.uniform(0, 1000);

            IntegerHopGraph ahg = new IntegerHopGraph();

            ShortestPathsSolver<Integer> solver = new AStarSolver<>(ahg, start, goal, 10);
            SolutionPrinter.summarizeSolution(solver, " => ");
        }
    }
}
