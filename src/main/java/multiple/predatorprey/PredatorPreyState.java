package multiple.predatorprey;

import multiple.agent.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PredatorPreyState extends State {

    /* Constants for the initial state of the single.agent. */
    protected static int INIT_X1 = 1;
    protected static int INIT_Y1 = 1;

//    protected static int INIT_X2 = 1;
//    protected static int INIT_Y2 = 2;
    protected static int INIT_X2 = 7;
    protected static int INIT_Y2 = 2;

//    protected static int INIT_X3 = 2;
//    protected static int INIT_Y3 = 1;
    protected static int INIT_X3 = 5;
    protected static int INIT_Y3 = 8;

    private static final int CLEAR = 0;
    private static final int HNTR1 = 1;
    private static final int HNTR2 = 2;
    private static final int HNTR3 = 3;
    private static final int DEER = 4;
    private static final int HILL = 5;

    /*
     * Default map for initial state. The FOREST is completely surrounded by HILLS
     */
    protected static int[][] defaultMap = {  // TODO: don't pass over other hunters...
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL },
            { HILL, HNTR1, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, DEER,  CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HNTR2, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, HNTR3, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL } };
    protected static int n = 10;


    /* Variables for the state of the single.agent. */
    protected int agent1X;
    protected int agent1Y;

    protected int agent2X;
    protected int agent2Y;

    protected int agent3X;
    protected int agent3Y;

    /** An array that contains the locations of objects in the world. */
    protected int[][] map;

    protected boolean isDeerAlive = true;

    protected int height;
    protected int width;

    static final int DEFAULT_HEIGHT = 10;
    static final int DEFAULT_WIDTH = 10;

    /** Returns the default initial state for the vacuum world. */
    public static PredatorPreyState getInitState() {

        PredatorPreyState state;

        state = new PredatorPreyState();
        state.width = defaultMap.length;
        state.height = defaultMap[0].length;
        state.map = new int[state.width][state.height];
        state.map = defaultMap;
        state.agent1X = INIT_X1;
        state.agent1Y = INIT_Y1;
        state.agent2X = INIT_X2;
        state.agent2Y = INIT_Y2;
        state.agent3X = INIT_X3;
        state.agent3Y = INIT_Y3;
        return state;
    }


    /** Constructs a new vacuum state. */
    public PredatorPreyState() {
    }

    /** Returns the single.agent's X position. */
    public int getAgentX(int indx) {
        if (indx == 0)
            return agent1X;
        else if (indx == 1)
            return agent2X;
        else
            return agent3X;
    }

    /** Returns the single.agent's Y position. */
    public int getAgentY(int indx) {
        if (indx == 0)
            return agent1Y;
        else if (indx == 1)
            return agent2Y;
        else
            return agent3Y;
    }

    /** Changes the single.agent's X position. */
    public void setAgentX(int x, int indx) {
        if (indx == 0)
            agent1X = x;
        else if (indx == 1)
            agent2X = x;
        else
            agent3X = x;
    }

    /** Changes the single.agent's Y position. */
    public void setAgentY(int y, int indx) {
        if (indx == 0)
            agent1Y = y;
        else if (indx == 1)
            agent2Y = y;
        else
            agent3Y = y;
    }

    /** Removes dirt from the specified location. */
    public void shootDeer(int x, int y) {
        // check if deer is in gun range - the shooter is in the middle (x,y) and we check their surroundings
        if (x-1 >= 0 && y-1 >= 0 && map[x-1][y-1] == DEER) { map[x-1][y-1] = CLEAR; isDeerAlive = false; }
        if (x-1 >= 0 && map[x-1][y] == DEER) { map[x-1][y] = CLEAR; isDeerAlive = false; }
        if (x+1 < n && y+1 < n && map[x-1][y+1] == DEER) { map[x-1][y+1] = CLEAR; isDeerAlive = false; }

        if (y-1 >= 0 && map[x][y-1] == DEER) { map[x][y-1] = CLEAR; isDeerAlive = false; }
        if (y+1 < n && map[x][y+1] == DEER) { map[x][y+1] = CLEAR; isDeerAlive = false; }

        if (x+1 < n && y-1 >= 0 && map[x+1][y-1] == DEER) { map[x+1][y-1] = CLEAR; isDeerAlive = false; }
        if (x+1 < n && map[x+1][y] == DEER) { map[x+1][y] = CLEAR; isDeerAlive = false; }
        if (x+1 < n && y+1 < n && map[x+1][y+1] == DEER) { map[x+1][y+1] = CLEAR; isDeerAlive = false; }
    }

    /** Returns true if the specified location has dirt in it. */
    public boolean hasDeer(int x, int y) {

        if (map[x][y] == DEER)
            return true;
        else
            return false;
    }

    /** Returns true if the specified location is a wall. */
    public boolean isHill(int x, int y) {

        if (map[x][y] == HILL)
            return true;
        else
            return false;
    }


    /** Returns true if the location is within bounds of the state's map. */
    public boolean inBounds(int x, int y) {

        if (x > 0 && x < width-1 && y > 0 && y < height-1)
            return true;
        else
            return false;
    }


    /**
     * Prints an output of the state to the screen. This output includes a map as
     * well as information about the single.agent's location and the direction it is
     * facing. On the map, "A" denotes the single.agent and "*" denotes dirt.
     */
    public void display() {

        for (int j = 1; j < width-1; j++)
            System.out.print("  " + j);
        System.out.println();

        System.out.print(" ");
        for (int j = 1; j < width-1; j++)
            System.out.print("+--");
        System.out.println("+");

        /*
         * To print to the screen properly, the loop in the Y direction must be
         * the outer loop.
         */
        for (int i = 1; i < height-1; i++) {
            System.out.print(i + "|");
            for (int j = 1; j < width-1; j++) {
                for (int indx : new int[]{0, 1, 2}) {
                    if (i == getAgentY(indx) && j == getAgentX(indx)) {
                        System.out.print("H");
                        continue;
                    }
                }
                if (hasDeer(i, j))
                    System.out.print("D");
                else
                    System.out.print(" ");
                System.out.print(" |");
            }
            System.out.println();

            System.out.print(" +");
            for (int j = 1; j < width - 2; j++)
                System.out.print("--+");
            System.out.println("--+");

        }
        int indx = 0;  // TODO: refactor this for MAS
        System.out.println("Location: (" + getAgentX(indx) + "," + getAgentY(indx) + ")  ");
        System.out.println("Location: (" + getAgentX(indx+1) + "," + getAgentY(indx+1) + ")  ");
        System.out.println("Location: (" + getAgentX(indx+2) + "," + getAgentY(indx+2) + ")  ");
        System.out.println();

        System.out.println("Press RETURN to continue.");

        BufferedReader console = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            String input = console.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    public boolean isDeerAlive() {
        return isDeerAlive;
    }

}
