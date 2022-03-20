package com.company.GameController;

import com.company.Algorithms.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the position of each object in the map
 * It stores the position and provide it if needed
 */
class Positions {
    int xh, yh, xf, yf, xn, yn, xb, yb, xc, yc, xe, ye;
    List<Integer> positionsList = new ArrayList<>(12);

    /**
     * This method is used to extract the information from the raw string input and validate it
     *
     * @param s
     */
    boolean takeInput(String s) {
        xh = Integer.parseInt(String.valueOf(s.charAt(1)));
        yh = Integer.parseInt(String.valueOf(s.charAt(3)));
        positionsList.add(0, xh);
        positionsList.add(1, yh);

        xf = Integer.parseInt(String.valueOf(s.charAt(7)));
        yf = Integer.parseInt(String.valueOf(s.charAt(9)));
        positionsList.add(2, xf);
        positionsList.add(3, yf);

        xn = Integer.parseInt(String.valueOf(s.charAt(13)));
        yn = Integer.parseInt(String.valueOf(s.charAt(15)));
        positionsList.add(4, xn);
        positionsList.add(5, yn);

        xb = Integer.parseInt(String.valueOf(s.charAt(19)));
        yb = Integer.parseInt(String.valueOf(s.charAt(21)));
        positionsList.add(6, xb);
        positionsList.add(7, yb);

        xc = Integer.parseInt(String.valueOf(s.charAt(25)));
        yc = Integer.parseInt(String.valueOf(s.charAt(27)));
        positionsList.add(8, xc);
        positionsList.add(9, yc);

        xe = Integer.parseInt(String.valueOf(s.charAt(31)));
        ye = Integer.parseInt(String.valueOf(s.charAt(33)));
        positionsList.add(10, xe);
        positionsList.add(11, ye);
        if (xe == xb && ye == yb) {
            return false;
        }
        if (nearFilch(xe, ye) || nearNorris(xe, ye)) {
            return false;
        }
        if (nearNorris(xb, yb) || nearFilch(xb, yb)) {
            return false;
        }
        return !nearNorris(xc, yc) && !nearFilch(xc, yc);

    }

    /**
     * getters for the positions
     *
     * @return
     */
    Point getHarryPosition() {
        return new Point(xh, yh);
    }

    Point getFilchPosition() {
        return new Point(xf, yf);
    }

    Point getNorrisPosition() {
        return new Point(xn, yn);
    }

    Point getBookPosition() {
        return new Point(xb, yb);
    }

    Point getExitPosition() {
        return new Point(xe, ye);
    }

    /**
     * setters for the positions
     *
     * @param x
     * @param y
     */
    void setHarryPosition(int x, int y) {
        xh = x;
        yh = y;
        positionsList.add(0, x);
        positionsList.add(1, y);
    }

    void setFilchPosition(int x, int y) {
        xf = x;
        yf = y;
        positionsList.add(2, x);
        positionsList.add(3, y);
    }

    void setNorrisPosition(int x, int y) {
        xn = x;
        yn = y;
        positionsList.add(4, x);
        positionsList.add(5, y);
    }

    void setBookPosition(int x, int y) {
        xb = x;
        yb = y;
        positionsList.add(6, x);
        positionsList.add(7, y);
    }

    void setCloakPosition(int x, int y) {
        xc = x;
        yc = y;
        positionsList.add(8, x);
        positionsList.add(9, y);
    }

    void setExitPosition(int x, int y) {
        xe = x;
        ye = y;
        positionsList.add(10, x);
        positionsList.add(11, y);
    }

    /**
     * this function check if the location of Harry is inside the perception of the others or not
     *
     * @param x
     * @param y
     * @param i
     * @return
     */
    boolean nearHarry(int x, int y, int i) {
        return x <= i && y <= i;
    }

    /**
     * this function check if the given position is in the perception zone or not
     *
     * @param x
     * @param y
     * @return
     */
    boolean nearFilch(int x, int y) {
        return Math.abs(x - getFilchPosition().X) <= 2 && Math.abs(y - getFilchPosition().Y) <= 2;
    }

    /**
     * this function check if the given position is in the perception zone or not
     *
     * @param x
     * @param y
     * @return
     */
    boolean nearNorris(int x, int y) {
        return Math.abs(x - getNorrisPosition().X) <= 1 && Math.abs(y - getNorrisPosition().Y) <= 1;
    }
}
