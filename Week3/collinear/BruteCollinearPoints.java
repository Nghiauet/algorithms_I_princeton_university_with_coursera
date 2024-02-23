import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException("Points array can't be null or contain null values");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Points array can't be null or contain null values");
        }

        ArrayList<LineSegment> lineSegmentsArarArrayList = new ArrayList<>();

        // copy to avoid mutating the input array
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        // check for duplicate points
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate points found");
        }
        for (int p = 0; p < pointsCopy.length; p++) {
            for (int q = p + 1; q < pointsCopy.length; q++) {
                for (int r = q + 1; r < pointsCopy.length; r++) {
                    if (pointsCopy[p].slopeTo(pointsCopy[q]) != pointsCopy[p].slopeTo(pointsCopy[r]))
                        continue;
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            lineSegmentsArarArrayList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));

                        }
                    }
                }
            }
            // transform the ArrayList to an array
            lineSegments = lineSegmentsArarArrayList.toArray(new LineSegment[lineSegmentsArarArrayList.size()]);
        }
    }

    public int numberOfSegments() // the number of line segments
    {
        return lineSegments.length;
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}