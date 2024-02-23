import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    // Line segments - array representation
    private LineSegment[] lineSegments;

    // Line segments - array list representation
    private ArrayList<LineSegment> lineSegmentsArrayList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // copy the array
        validateInput(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        checkDuplicatePoints(pointsCopy);

        // check for null and duplicate points
        if (points.length < 4) {
            lineSegments = new LineSegment[0];
            return;
        }
        // Find line segments
        Point[] startPoints = Arrays.copyOf(pointsCopy, points.length);
        for (int i = 0; i < startPoints.length; i++) {
            // Sort points by slope relative to the origin
            Point start = startPoints[i];
            // Sort by 2 cretieria
            Arrays.sort(pointsCopy, start.slopeOrder());
            boolean flag = true;
            if (pointsCopy[1] == null)
                break;
            double slope1 = start.slopeTo(pointsCopy[1]);
            double slope2;
            int count = 1;
            Point end = null;
            for (int j = 2; j < pointsCopy.length; j++) {
                slope2 = start.slopeTo(pointsCopy[j]);
                if (slope2 == slope1) {

                    if (count == 1) {
                        end = pointsCopy[j - 1];
                        count += 1;
                        if (pointsCopy[j - 1].compareTo(start) < 0) {
                            flag = false;
                        }
                    }
                    count += 1;

                    if (pointsCopy[j].compareTo(start) < 0) {
                        flag = false;
                    }
                    if (pointsCopy[j].compareTo(end) > 0) {
                        end = pointsCopy[j];
                    }
                } else {
                    if (count >= 4 && flag) {
                        lineSegmentsArrayList.add(new LineSegment(start, end));
                    }
                    flag = true;
                    count = 1;
                    slope1 = slope2;
                    end = null;
                }
            }
            if (count >= 4 && flag) {
                lineSegmentsArrayList.add(new LineSegment(start, end));
            }
        }
        lineSegments = lineSegmentsArrayList.toArray(new LineSegment[lineSegmentsArrayList.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    private void validateInput(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Points array can't be null or contain null values");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Points array can't be null or contain null values");
        }
    }

    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate points found");
        }
    }

    public LineSegment[] segments() // the line segments
    {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    // Test client
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        StdOut.println("Number of segments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}