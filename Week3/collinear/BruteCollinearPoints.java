public class BruteCollinearPoints {
    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }
    public int numberOfSegments() // the number of line segments
    {
        return 0;
    }

    public LineSegment[] segments() // the line segments
    {
        return null;
    }
}