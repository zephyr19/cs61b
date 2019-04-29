package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    /* It seems worked! */
    public void KDTreeConstructor() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.1, 2.3);
        List<Point> points = new LinkedList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        KDTree kdTree = new KDTree(points);
    }

    @Test
    public void nearestSimpleTest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.1, 2.3);
        List<Point> points = new LinkedList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        KDTree kdTree = new KDTree(points);
        assertEquals(p1, kdTree.nearest(1, 2));
        assertEquals(p2, kdTree.nearest(3, 4));
        assertEquals(p3, kdTree.nearest(-2, 4.22));
        assertEquals(p4, kdTree.nearest(1.1, 2.4));
    }

    @Test
    /* Test passed. Using 4 m 27 s 268ms. */
    public void nearestRandomTest() {
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            double x = StdRandom.uniform(0, 10e3);
            double y = StdRandom.uniform(0, 10e3);
            Point point = new Point(x, y);
            points.add(point);
        }
        KDTree kdTree = new KDTree(points);
        NaivePointSet naivePointSet = new NaivePointSet(points);
        for (int i = 0; i < 10e5; i++) {
            double x = StdRandom.uniform(0, 10e3);
            double y = StdRandom.uniform(0, 10e3);
            assertEquals(kdTree.nearest(x, y), naivePointSet.nearest(x, y));
        }
    }

    @Test
    /* Using 7 s 805 ms. */
    public void nearestRandomTimeTest() {
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            double x = StdRandom.uniform(0, 10e3);
            double y = StdRandom.uniform(0, 10e3);
            Point point = new Point(x, y);
            points.add(point);
        }
        KDTree kdTree = new KDTree(points);
        for (int i = 0; i < 10e5; i++) {
            double x = StdRandom.uniform(0, 10e3);
            double y = StdRandom.uniform(0, 10e3);
            kdTree.nearest(x, y);
        }
    }
}
