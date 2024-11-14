import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

// This class implements a 2D tree data structure for efficient nearest neighbor search
public class KdTree {
    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = 0.0;
    private static final double YMAX = 1.0;

    // Node class represents a point in 2D space and the splitting dimension
    private class Node {
        private final Point2D point;
        private Node left, right;
        private final boolean verticalSplit;
                // true if splitting vertically (by x), false if horizontally (by y)

        public Node(Point2D point, boolean verticalSplit) {
            if (point == null) {
                throw new IllegalArgumentException("Point cannot be null");
            }
            this.point = point;
            this.left = null;
            this.right = null;
            this.verticalSplit = verticalSplit;
        }
    }

    private Node root;
    private int size;
    private final List<Point2D> pointsInRect = new ArrayList<>();

    private double minDistance;
    private Point2D nearestPoint;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    private boolean isBigger(Point2D a, Point2D b, boolean verticalSplit) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Points cannot be null");
        }
        if (verticalSplit) {
            return Point2D.X_ORDER.compare(a, b) >= 0;
        }
        return Point2D.Y_ORDER.compare(a, b) >= 0;
    }

    private Node insertRec(Node node, Point2D point, boolean verticalSplit) {
        if (point == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (node == null) {
            size++;
            return new Node(point, verticalSplit);
        }

        if (isBigger(point, node.point, verticalSplit)) {
            node.right = insertRec(node.right, point, !verticalSplit);
        }
        else {
            node.left = insertRec(node.left, point, !verticalSplit);
        }
        return node;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (contains(p)) {
            return;
        }
        root = insertRec(root, p, true);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }

        Node node = root;
        while (node != null) {
            if (node.point.equals(p)) {
                return true;
            }
            if (isBigger(p, node.point, node.verticalSplit)) {
                node = node.right;
            }
            else {
                node = node.left;
            }
        }
        return false;
    }

    public void draw() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Tree is empty");
        }
        draw(root, new RectHV(XMIN, YMIN, XMAX, YMAX));
    }

    private void draw(Node n, RectHV rectHV) {
        if (n == null || rectHV == null) {
            throw new IllegalArgumentException("Node or rectangle cannot be null");
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        n.point.draw();

        StdDraw.setPenRadius();
        if (n.verticalSplit) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point.x(), rectHV.ymin(), n.point.x(), rectHV.ymax());
            if (n.left != null) {
                draw(n.left, new RectHV(rectHV.xmin(), rectHV.ymin(), n.point.x(), rectHV.ymax()));
            }
            if (n.right != null) {
                draw(n.right, new RectHV(n.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
            }
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rectHV.xmin(), n.point.y(), rectHV.xmax(), n.point.y());
            if (n.left != null) {
                draw(n.left, new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), n.point.y()));
            }
            if (n.right != null) {
                draw(n.right, new RectHV(rectHV.xmin(), n.point.y(), rectHV.xmax(), rectHV.ymax()));
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
        pointsInRect.clear();
        if (isEmpty()) {
            return pointsInRect;
        }
        range(root, rect);
        return pointsInRect;
    }

    private void range(Node node, RectHV rect) {
        if (node == null || rect == null) {
            return;
        }
        if (rect.contains(node.point)) {
            pointsInRect.add(node.point);
        }

        if (node.verticalSplit) {
            if (rect.xmin() <= node.point.x()) {
                range(node.left, rect);
            }
            if (rect.xmax() >= node.point.x()) {
                range(node.right, rect);
            }
        }
        else {
            if (rect.ymin() <= node.point.y()) {
                range(node.left, rect);
            }
            if (rect.ymax() >= node.point.y()) {
                range(node.right, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (isEmpty()) {
            return null;
        }
        minDistance = Double.POSITIVE_INFINITY;
        nearestPoint = null;
        nearest(root, p, new RectHV(XMIN, YMIN, XMAX, YMAX));
        return nearestPoint;
    }

    // Key changes made to optimize traversal:
    // 1. Added RectHV parameter to track the rectangle bounds of each node
    // 2. Early pruning: Skip subtrees if their bounding rectangle is farther than current best
    // 3. Visit closer subtree first based on query point location
    // The idea behind is each point devides the space two 2 rectangles.
    private void nearest(Node node, Point2D point, RectHV rect) {
        if (node == null || rect.distanceSquaredTo(point) > minDistance) {
            return;
        }

        double distance = node.point.distanceSquaredTo(point);
        if (distance < minDistance) {
            minDistance = distance;
            nearestPoint = node.point;
        }

        RectHV leftRect, rightRect;
        if (node.verticalSplit) {
            leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
            rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            if (point.x() < node.point.x()) {
                nearest(node.left, point, leftRect);
                nearest(node.right, point, rightRect);
            }
            else {
                nearest(node.right, point, rightRect);
                nearest(node.left, point, leftRect);
            }
        }
        else {
            leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
            rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            if (point.y() < node.point.y()) {
                nearest(node.left, point, leftRect);
                nearest(node.right, point, rightRect);
            }
            else {
                nearest(node.right, point, rightRect);
                nearest(node.left, point, leftRect);
            }
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();

        // Create a map of points to labels for easier printing
        Point2D pointA = new Point2D(0.375, 0.875);    // A
        Point2D pointB = new Point2D(0.8125, 1.0);     // B
        Point2D pointC = new Point2D(0.0, 0.5);        // C
        Point2D pointD = new Point2D(0.5625, 0.375);   // D
        Point2D pointE = new Point2D(0.75, 0.8125);    // E
        Point2D pointF = new Point2D(0.5, 0.75);       // F
        Point2D pointG = new Point2D(0.4375, 0.3125);  // G
        Point2D pointH = new Point2D(0.625, 0.25);     // H
        Point2D pointI = new Point2D(0.3125, 0.125);   // I
        Point2D pointJ = new Point2D(0.1875, 0.6875);  // J

        // Insert points
        System.out.println("Inserting points:");
        kdTree.insert(pointA);
        System.out.println("Inserted A: " + pointA);
        kdTree.insert(pointB);
        System.out.println("Inserted B: " + pointB);
        kdTree.insert(pointC);
        System.out.println("Inserted C: " + pointC);
        kdTree.insert(pointD);
        System.out.println("Inserted D: " + pointD);
        kdTree.insert(pointE);
        System.out.println("Inserted E: " + pointE);
        kdTree.insert(pointF);
        System.out.println("Inserted F: " + pointF);
        kdTree.insert(pointG);
        System.out.println("Inserted G: " + pointG);
        kdTree.insert(pointH);
        System.out.println("Inserted H: " + pointH);
        kdTree.insert(pointI);
        System.out.println("Inserted I: " + pointI);
        kdTree.insert(pointJ);
        System.out.println("Inserted J: " + pointJ);

        // Test nearest point query
        Point2D queryPoint = new Point2D(0.125, 0.9375);
        System.out.println("\nQuery point: " + queryPoint);

        Point2D nearest = kdTree.nearest(queryPoint);
        System.out.println("Nearest point: " + nearest);

        // Expected sequence: A C J B D F
        System.out.println("\nExpected sequence: A C J B D F");
        System.out.println("Your sequence seems to include G as well");
    }
}
