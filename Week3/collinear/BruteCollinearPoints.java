import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)
    {
        validateInput(points);
        ArrayList<LineSegment> lineSegmentsArarArrayList = new ArrayList<>();

        // copy to avoid mutating the input array
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        checkDuplicatePoints(pointsCopy);

        if (pointsCopy.length < 4) {
            lineSegments = new LineSegment[0];
            return;
        }

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    // avoid onemore iteration if the slopes are different
                    double slopePQ = pointsCopy[p].slopeTo(pointsCopy[q]);
                    double slopePR = pointsCopy[p].slopeTo(pointsCopy[r]);
                    if (slopePQ != slopePR)
                        continue;
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double slopePS = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if (slopePQ == slopePS) {
                            lineSegmentsArarArrayList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
            // transform the ArrayList to an array
            lineSegments = lineSegmentsArarArrayList.toArray(new LineSegment[lineSegmentsArarArrayList.size()]);
        }
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

    public int numberOfSegments()
    {
        return lineSegments.length;
    }

    public LineSegment[] segments()
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