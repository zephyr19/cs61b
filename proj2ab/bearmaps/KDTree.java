package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private class Node {
        boolean ifLeft;
        double x;
        double y;
        Node left;
        Node right;

        private Node(double x, double y, boolean b) {
            this.x = x;
            this.y = y;
            this.ifLeft = b;
        }
    }

    private Node root;
    private Node best;
    private double distance;

    private void add(Point point) {
        root = add(root, point, true); // First add left or right, then add top or down.
    }

    private Node add(Node node, Point point, boolean ifLeft) {
        if (node == null) {
            return new Node(point.getX(), point.getY(), ifLeft);
        }
        if (node.ifLeft) {
            if (point.getX() < node.x) {
                node.left = add(node.left, point, false);
            } else {
                node.right= add(node.right, point, false);
            }
        } else {
            if (point.getY() < node.y) {
                node.left = add(node.left, point,true);
            } else {
                node.right = add(node.right, point, true);
            }
        }
        return node;
    }

    public KDTree(List<Point> points) {
        for (Point point : points) {
            add(point);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        distance = 10e5;
        nearest(x, y,root);
        if (best == null) {
            throw new IllegalCallerException();
        }
        return new Point(best.x, best.y);
    }

    private void nearest(double x, double y, Node node) {
        if (node == null) {
            return;
        }
        double xDist = node.x - x;
        double yDist = node.y - y;
        double dist = xDist * xDist + yDist * yDist;
        if (dist < distance) {
            distance = dist;
            best = node;
        }
        Node goodSide;
        Node badSide;
        if (node.ifLeft) {
            if (x - node.x < 0) {
                goodSide = node.left;
                badSide = node.right;
            } else {
                goodSide = node.right;
                badSide = node.left;
            }
        } else {
            if (y - node.y < 0) {
                goodSide = node.left;
                badSide = node.right;
            } else {
                goodSide = node.right;
                badSide = node.left;
            }
        }
        nearest(x, y, goodSide);
        if (node.ifLeft) {
            if (Math.abs(x - node.x) < distance) {
                nearest(x, y, badSide);
            }
        } else {
            if (Math.abs(y - node.y) < distance) {
                nearest(x, y, badSide);
            }
        }
    }
}
