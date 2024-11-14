/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        Point2D lastPoint = null;
        long lastClickTime = 0;
        while (true) {
            if (StdDraw.isMousePressed()) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime >= 500) { // Only process click every 0.5 seconds
                    double x = StdDraw.mouseX();
                    double y = StdDraw.mouseY();
                    Point2D p = new Point2D(x, y);
                    if (rect.contains(p) && (lastPoint == null || !p.equals(lastPoint))) {
                        StdOut.printf("%8.6f %8.6f\n", x, y);
                        kdtree.insert(p);
                        StdDraw.clear();
                        kdtree.draw();
                        StdDraw.show();
                        lastPoint = p;
                        lastClickTime = currentTime;
                    }
                }
            } else {
                lastPoint = null;
            }
            StdDraw.pause(20);
        }
    }
}
