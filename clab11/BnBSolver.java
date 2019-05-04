import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Pair<Bear, Bed>> pairs;
    private List<Bear> solvedBears;
    private List<Bed> solvedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        pairs = new LinkedList<>();
        solvedBears = new LinkedList<>();
        solvedBeds = new LinkedList<>();
        partition(bears.get(0), beds, bears);
        for (Pair pair : pairs) {
            solvedBears.add((Bear) pair.first());
            solvedBeds.add((Bed) pair.second());
        }
    }

    private void partition(Bear bearPivot, List<Bed> bedUnsorted, List<Bear> bearUnsorted) {
        List<Bear> bearLess = new LinkedList<>();
        List<Bear> bearGreater = new LinkedList<>();
        List<Bed> bedLess = new LinkedList<>();
        List<Bed> bedGreater = new LinkedList<>();
        Bed bedPivot = new Bed(0);
        for (Bed bed : bedUnsorted) {
            int cmp = bearPivot.compareTo(bed);
            if (cmp > 0) {
                bedLess.add(bed);
            } else if (cmp < 0) {
                bedGreater.add(bed);
            } else {
                bedPivot = bed;
            }
        }
        pairs.add(new Pair<>(bearPivot, bedPivot));
        for (Bear bear : bearUnsorted) {
            int cmp = bedPivot.compareTo(bear);
            if (cmp > 0) {
                bearLess.add(bear);
            } else if (cmp < 0) {
                bearGreater.add(bear);
            }
        }
        if (bearLess.size() > 1) {
            partition(bearLess.get(0), bedLess, bearLess);
        }
        if (bearGreater.size() > 1) {
            partition(bearGreater.get(0), bedGreater, bearGreater);
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
