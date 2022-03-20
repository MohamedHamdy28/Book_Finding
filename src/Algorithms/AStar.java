package Algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * This class is responsible for implementing the A star algorithm
 */
public class AStar {
    String[][] map;
    Point exit;
    Point book;
    boolean hasCloak = false;
    LinkedList<Point> path;
    int[] row = {-1, 1, 0, 0, 1, -1, -1, 1};
    int[] col = {0, 0, -1, 1, 1, 1, -1, -1};

    public AStar(String[][] TheMap, Point TheExit, int input2) {
        this.map = TheMap;
        this.exit = TheExit;
        path = new LinkedList<>();
    }

    /**
     * here we need to check for the position of the book to be able to apply the A star algorithm
     */
    void findBook(String[][] TheMap) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (TheMap[i][j] != null) {
                    if (TheMap[i][j].equals("B") || TheMap[i][j].equals("BH") || TheMap[i][j].equals("CB") || TheMap[i][j].equals("BC")) {
                        book = new Point(i, j);
                        return;
                    }
                }
            }
        }
    }

    /**
     * check if the next position is valid or not
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    boolean isValid(int x, int y, String target) {
        if (x >= 0 && y >= 0 && x <= 8 && y <= 8) { // inside the map
            if (map[x][y] != null) {
                if (hasCloak && map[x][y].equals("r")) {
                    return true;
                }
                if (map[x][y].equals("C")) {
                    hasCloak = true;
                    return true;
                }
            }

            //this will be like a virtual map used while exploring the path and the v represent that this cell was visited, and r is restricted area
            return map[x][y] == null || map[x][y].equals(target) || map[x][y].equals("C") || map[x][y].equals("C" + target);
        }
        return false;
    }

    /**
     * check if the given position is the destination or not
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    boolean isDestination(int x, int y, String target) {
        if (map[x][y] != null) {
            return map[x][y].equals(target);
        }
        return false;
    }

    /**
     * calculating the heuristic function for the given position
     *
     * @param x
     * @param y
     * @return
     */
    double calculateH(int x, int y) {
        int dx = Math.abs(x - book.X);
        int dy = Math.abs(y - book.Y);
        int D = 1;
        double D2 = Math.sqrt(2);
        return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
    }

    /**
     * this method is resposible for calling the algorithm for the book and exit, and printing the results
     *
     * @param q
     * @param w
     */
    public void findPath(int q, int w) {
        long startTime = System.nanoTime();
        findBook(map);
        if (aStarSearch(q, w, "B")) {

            if (aStarSearch(book.X, book.Y, "E")) {
                System.out.println("Win");
                System.out.println("The number of steps algorithm took to reach exit door: " + (path.size() - 2));
                for (Point p : path) {
                    if (map[p.X][p.Y] == null || map[p.X][p.Y].equals("C"))
                        map[p.X][p.Y] = "*";
                    if (hasCloak) {
                        if (map[p.X][p.Y] != null) {
                            if (map[p.X][p.Y].equals("r")) {
                                map[p.X][p.Y] = "*";
                            }

                        }
                    }
                }
                printWorld(map);
                System.out.println("Here are the points that Harry passed through");
                LinkedList<Point> bookPath = new LinkedList<>();
                LinkedList<Point> exitPath = new LinkedList<>();
                int c = 0;
                while (path.get(c).X != 0 && path.get(c).Y != 0) {
                    bookPath.add(new Point(path.get(c).X, path.get(c).Y));
                    c++;
                }
                bookPath.add(new Point(path.get(c).X, path.get(c).Y));
                c++;
                while (c < path.size()) {
                    exitPath.add(new Point(path.get(c).X, path.get(c).Y));
                    c++;
                }
                for (int i = bookPath.size() - 1; i >= 0; i--) {
                    System.out.print("[" + bookPath.get(i).X + "," + bookPath.get(i).Y + "] ");
                }
                for (int i = exitPath.size() - 1; i >= 0; i--) {
                    System.out.print("[" + exitPath.get(i).X + "," + exitPath.get(i).Y + "] ");
                }
                System.out.println();
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println("Time taken by the algorithm to reach the exit door: " + (totalTime / 1000000) + " msec");

            } else {
                System.out.println("Lose");
            }
        } else {
            System.out.println("Lose");
        }


    }

    /**
     * This method trace the path
     *
     * @param nodeDetails
     * @param target
     */
    void pathTracing(Node[][] nodeDetails, Point target) {
        int row = target.X;
        int col = target.Y;
        while (!(nodeDetails[row][col].parent.X == row && nodeDetails[row][col].parent.Y == col)) {
            path.add(new Point(row, col));
            int tempRow = nodeDetails[row][col].parent.X;
            int tempCol = nodeDetails[row][col].parent.Y;
            row = tempRow;
            col = tempCol;
        }
        path.add(new Point(row, col));
    }

    /**
     * the algorithm, it depends on calculating the heuristic function for each cell
     * and choose the one with the smallest distance to the goal
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    boolean aStarSearch(int x, int y, String target) {
        if (isDestination(x, y, target)) {
            return true;
        }
        // Create a closed list and initialise it to false which
        // means that no cell has been included yet This closed
        // list is implemented as a boolean 2D array
        boolean[][] closedList = new boolean[9][9];
        Node[][] nodeDetails = new Node[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                closedList[i][j] = false;
                nodeDetails[i][j] = new Node(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, new Point(-1, -1));
            }
        }
        nodeDetails[x][y].f = 0.0;
        nodeDetails[x][y].g = 0.0;
        nodeDetails[x][y].h = 0.0;
        nodeDetails[x][y].parent.Y = y;
        nodeDetails[x][y].parent.X = x;

        PriorityQueue<threeNums> openList = new PriorityQueue<threeNums>(Comparator.comparing(threeNums::getF));
        openList.add(new threeNums(0.0, x, y));
        boolean foundDest = false;
        while (!openList.isEmpty()) {
            threeNums p = openList.remove();
            int i = p.i;
            int j = p.j;
            closedList[i][j] = true;
            for (int q = 0; q < 8; q++) {
                i = p.i;
                j = p.j;
                i += row[q];
                j += col[q];

                if (isValid(i, j, target)) {
                    if (isDestination(i, j, target)) {
                        nodeDetails[i][j].parent.X = i - row[q];
                        nodeDetails[i][j].parent.Y = j - col[q];
                        if (target.equals("B"))
                            pathTracing(nodeDetails, book);
                        else
                            pathTracing(nodeDetails, exit);
                        foundDest = true;
                        return true;
                    } else if (!closedList[i][j]) {
                        double gNew, hNew, fNew;
                        gNew = nodeDetails[i - row[q]][j - col[q]].g + 1.0;
                        hNew = calculateH(i, j);
                        fNew = gNew + hNew;
                        if (nodeDetails[i][j].f == Double.MAX_VALUE || nodeDetails[i][j].f > fNew) {
                            openList.add(new threeNums(fNew, i, j));
                            nodeDetails[i][j].f = fNew;
                            nodeDetails[i][j].g = gNew;
                            nodeDetails[i][j].h = hNew;
                            nodeDetails[i][j].parent.X = i - row[q];
                            nodeDetails[i][j].parent.Y = j - col[q];
                        }

                    }
                }
            }
        }
        return false;

    }

    /**
     * method for printing the world
     *
     * @param items
     */
    void printWorld(String[][] items) {
        System.out.print(" ");
        for (int i = 0; i <= 8; i++) {
            System.out.print("   " + i);

        }
        System.out.println();
        for (int i = 8; i >= 0; i--) {

            System.out.print(i + " | ");

            for (int j = 0; j <= 8; j++) {
                if (items[i][j] == null) {
                    System.out.print("  | ");
                } else {
                    System.out.print(items[i][j]);
                    System.out.print(" | ");
                }
            }
            System.out.println("\n  |---|---|---|---|---|---|---|---|---| ");
        }
    }
}
