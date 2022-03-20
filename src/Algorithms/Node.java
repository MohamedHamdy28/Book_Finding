package Algorithms;

/**
 * node to contain information for implementing the A star algorithm
 */
public class Node {
    Point parent;
    double f, g, h;

    Node(double f, double g, double h, Point p) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.parent = p;
    }

}
