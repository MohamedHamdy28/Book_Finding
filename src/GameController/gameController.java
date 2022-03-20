package GameController;

import Algorithms.AStar;
import Algorithms.Backtracking;
import Algorithms.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is responsible for controlling the game and calling the algorithms
 */
public class gameController {
    Positions positions;
    String[][] items;
    Backtracking backtracking;

    public gameController() {
        positions = new Positions();
        items = new String[10][10];
    }

    /**
     * By calling this method the game will start
     * it will take the input, call the algorithms, and give the output
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to generate the positions randomly?");
        String ans = scanner.nextLine();
        if (ans.equals("yes") || ans.equals("Yes")) {
            this.generateRandomly();
        } else {
            System.out.println("Please give the positions of each object");
            String input1;
            input1 = scanner.nextLine();
            while (!this.positions.takeInput(input1)) {
                System.out.println("This input is wrong, pleas follow the instruction about the location of each agent");
                input1 = scanner.nextLine();
            }

        }
        System.out.println("Please choose a scenario between 1 or 2");
        int input2;
        input2 = scanner.nextInt();
        while (input2 != 1 && input2 != 2) {
            System.out.println("Invalid input, please choose a scenario between 1 or 2");
            input2 = scanner.nextInt();
        }
        this.addAgents();
        this.printWorld();
        backtracking = new Backtracking(items);
        long startTime = System.nanoTime();
        backtracking.findPath(this.positions.getHarryPosition().X, this.positions.getHarryPosition().Y, items, startTime, input2);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Backtracking");
        if (backtracking.hasPath) {
            System.out.println("Win");
            System.out.println("The number of steps algorithm took to reach exit door: " + backtracking.totalLength);
            for (Point p : backtracking.finalPath) {
                System.out.print("[" + p.X + "," + p.Y + "]");
            }
            System.out.println();
            backtracking.printWorld(backtracking.finalMap);

            System.out.println("Time taken by the algorithm to reach the exit door: " + (totalTime / 1000000) + " msec");
        } else {
            System.out.println("Lose");
        }
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("A star algorithm");
        AStar aStar = new AStar(backtracking.initialMap, positions.getExitPosition(), input2);
        aStar.findPath(this.positions.getHarryPosition().X, this.positions.getHarryPosition().Y);
    }

    /**
     * This method add the agents which were given manually
     */
    void addAgents() {
        List<String> agentsNames = new ArrayList<String>() {{
            add("H");
            add("F");
            add("N");
            add("B");
            add("C");
            add("E");
        }};
        int counter = 0;
        for (int i = 0; i < 12; i += 2) {
            int a = positions.positionsList.get(i);
            int b = positions.positionsList.get(i + 1);
            if (items[a][b] == null) {
                items[a][b] = agentsNames.get(counter);
            } else {
                String twoAgents;
                twoAgents = agentsNames.get(counter) + items[a][b];
                items[a][b] = twoAgents;
            }
            counter++;
        }
        addPerception();
    }

    /**
     * This method add the perception of the inspectors
     */
    void addPerception() {
        int x = positions.getFilchPosition().X;
        int y = positions.getFilchPosition().Y;
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (i >= 0 && j >= 0 && i <= 8 && j <= 8) {
                    if (i == x && j == y)
                        continue;
                    items[i][j] = "r";
                }
            }
        }
        x = positions.getNorrisPosition().X;
        y = positions.getNorrisPosition().Y;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i <= 8 && j <= 8) {
                    if (i == x && j == y)
                        continue;
                    items[i][j] = "r";
                }
            }
        }
    }

    /**
     * This method is for generating the positions of the agents randomly
     */
    void generateRandomly() {
        positions.setHarryPosition(0, 0);
        // assign the position of Argus Filch
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            positions.setFilchPosition(x, y);

        } while (positions.nearHarry(x, y, 2));
        //assign the position of Norris
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            positions.setNorrisPosition(x, y);
        } while (positions.nearHarry(x, y, 1));
        //assign position of book
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            positions.setBookPosition(x, y);
        } while (positions.nearFilch(x, y) || positions.nearNorris(x, y));
        //assign position of cloak
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            positions.setCloakPosition(x, y);
        } while (positions.nearFilch(x, y) || positions.nearNorris(x, y));
        //assign position of exit
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            positions.setExitPosition(x, y);
        } while (positions.nearFilch(x, y) || positions.nearNorris(x, y) || x == positions.getBookPosition().X && y == positions.getBookPosition().Y);

    }

    /**
     * This method print the initial map
     */
    void printWorld() {
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
