package Algorithms;

import java.util.LinkedList;

/**
 * This class is responsible for implementing the backtracking algorithm
 */
public class Backtracking {
    String[][] map;
    String[][] ans;
    public String[][] initialMap;
    public String[][] finalMap;
    int scenario;
    boolean hasCloak = false;
    int shortLength = Integer.MAX_VALUE;
    int length = 0;
    int[] row = {-1, 1, 0, 0, 1, -1, -1, 1};
    int[] col = {0, 0, -1, 1, 1, 1, -1, -1};
    int[][] visited;
    public int totalLength;
    boolean rtError = false;
    Point bookPosition;
    Point cloakPosition;
    public boolean hasPath = false;
    public LinkedList<Point> finalPath;

    public Backtracking(String[][] map) {
        initialMap = new String[9][9];
        finalPath = new LinkedList<>();
        this.map = map;
        this.visited = new int[9][9];
        bookPosition = new Point(0, 0);
        cloakPosition = new Point(0, 0);
        ans = new String[9][9];
        finalMap = new String[9][9];

    }

    /**
     * this method takes the initial position, pass it to the visit method to find the position of the book
     * and then pass the position of the book again to visit to find the path to the exit
     * Also it is responsible for drawing the path and handling errors
     *
     * @param q
     * @param w
     * @param initMap
     * @param startTime
     * @param input2
     */
    public void findPath(int q, int w, String[][] initMap, long startTime, int input2) {
        scenario = input2;
        for (int i = 0; i < 9; i++) {
            System.arraycopy(initMap[i], 0, initialMap[i], 0, 9);
        }

        visit(q, w, "B", startTime);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (ans[i][j] != null) {
                    if (ans[i][j].equals("*")) {
                        finalPath.add(new Point(i, j));
                    }
                }
            }
        }
        totalLength = shortLength;
        shortLength = Integer.MAX_VALUE;
        length = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                finalMap[i][j] = ans[i][j];
                map[i][j] = initMap[i][j];
            }
        }
        long startTime2 = System.nanoTime();
        visit(bookPosition.X, bookPosition.Y, "E", startTime2);
        int stars = 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (ans[i][j] != null) {
                    if (ans[i][j].equals("*")) {
                        finalPath.add(new Point(i, j));
                        stars++;
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (ans[i][j] != null)
                    if (ans[i][j].equals("*"))
                        finalMap[i][j] = ans[i][j];
            }
        }
        totalLength += shortLength;
        if (!hasCloak) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (initialMap[i][j] != null)
                        if (initialMap[i][j].equals("r") || initialMap[i][j].equals("C"))
                            finalMap[i][j] = initialMap[i][j];
                }
            }
        }
        if (rtError) {
            totalLength = stars;
        }
    }

    /**
     * This method is responsible for checking if the next position is valid or not
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    boolean isValid(int x, int y, String target) {
        if (x >= 0 && y >= 0 && x <= 8 && y <= 8) { // inside the map
            if (visited[x][y] == 1)
                return false;
            if (map[x][y] != null)
                if (hasCloak && map[x][y].equals("r")) {
                    return true;
                }
            //this will be like a virtual map used while exploring the path and the v represent that this cell was visited, and r is restricted area
            return map[x][y] == null || map[x][y].equals(target) || map[x][y].equals("C") || map[x][y].equals("C" + target);
        }
        return false;
    }

    /**
     * Here we implement the backtracking algorithm
     *
     * @param x
     * @param y
     * @param target
     * @param startTime
     */
    void visit(int x, int y, String target, long startTime) {

        if (map[x][y] != null)
            if (map[x][y].equals(target) || map[x][y].equals("C" + target) || map[x][y].equals("BH") || map[x][y].equals(target + "C")) {
                if (map[x][y].equals("E") && target.equals("E"))
                    hasPath = true;
                if (map[x][y].equals("C" + target)) {
                    hasCloak = true;
                    cloakPosition.Y = y;
                    cloakPosition.X = x;
                }
                if (length < shortLength) {
                    shortLength = length;
                    for (int i = 0; i < 9; i++) {
                        System.arraycopy(map[i], 0, ans[i], 0, 9);
                    }
                }

                return;
            }
        if (map[x][y] != null)
            if (map[x][y].equals("C")) {
                hasCloak = true;
                cloakPosition.X = x;
                cloakPosition.Y = y;
            }
        if (length > shortLength) {
            return;
        }


        visited[x][y] = 1;
        if (map[x][y] != null) {
            if (!map[x][y].equals("B") && !map[x][y].equals("H")) {
                map[x][y] = "*";
            }
        } else
            map[x][y] = "*";
        length++;

        for (int i = 0; i < 8; i++) {
            int tx = x;
            int ty = y;
            tx += row[i];
            ty += col[i];

            if (isValid(tx, ty, target)) {
                if (map[tx][ty] != null) {
                    if (map[tx][ty].equals(target)) {
                        bookPosition = new Point(tx, ty);
                    }
                    if (map[tx][ty].equals("C")) {
                        hasCloak = true;
                    }
                }
                long endTime = System.nanoTime();
                if (endTime - startTime > 2000000000) {
                    rtError = true;
                    return;
                }
                visit(tx, ty, target, startTime);
            }
        }
        visited[x][y] = 0;

        if (hasCloak) {
            hasCloak = false;
        }
        map[x][y] = null;
        length--;

    }

    /**
     * for printing the world
     *
     * @param items
     */
    public void printWorld(String[][] items) {
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
