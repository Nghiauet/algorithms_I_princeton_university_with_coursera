import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final SET<Point2D> pointsSet;
    public PointSET() {
        pointsSet = new SET<>();
    }

    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    public int size() {
        return pointsSet.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        pointsSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        return pointsSet.contains(p);
    }

    /** draw all points to standard draw */
    public void draw() {
        pointsSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Rectangle cannot be null");
        List<Point2D> pointsInRect = new ArrayList<>();
        for (Point2D point : pointsSet) {
            if (rect.contains(point)) {
                pointsInRect.add(point);
            }
        }
        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point cannot be null");
        if (pointsSet.isEmpty())
            return null;
        double min = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D point : pointsSet) {
            if (point.distanceSquaredTo(p) < min) {
                nearestPoint = point;
                min = point.distanceSquaredTo(p);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}