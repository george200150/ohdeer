package single.simplehunteragent;

import single.agent.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** Represents a state in the vacuum world. */
public class HunterState extends State {

	/* Constants for the initial state of the single.agent. */
	protected static int INIT_X = 1;
	protected static int INIT_Y = 1;

	private static final int CLEAR = 0;
	private static final int DEER = 1;
	private static final int HILL = 2;

	/*
	 * Default map for initial state. The room is completely surrounded by walls
	 */
	protected static int[][] defaultMap = {
			{HILL, HILL, HILL, HILL, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, DEER, HILL},
			{HILL, HILL, HILL, HILL, HILL}};
	private static int n;


	/* Variables for the state of the single.agent. */
	protected int agentX;
	protected int agentY;

	/**
	 * An array that contains the locations of objects in the world.
	 */
	protected int[][] map;

	protected boolean isDeerAlive = true;

	protected int height;
	protected int width;

	static final int DEFAULT_HEIGHT = 5;
	static final int DEFAULT_WIDTH = 5;

	/**
	 * Returns the default initial state for the vacuum world.
	 */
	public static HunterState getInitState() {

		HunterState state;

		state = new HunterState();
		state.width = defaultMap.length;
		state.height = defaultMap[0].length;
		state.map = new int[state.width][state.height];
		state.map = defaultMap;
		state.agentX = INIT_X;
		state.agentY = INIT_Y;
		n = state.width;
		return state;
	}


	/**
	 * Constructs a new vacuum state.
	 */
	public HunterState() {
	}

	/**
	 * Returns the single.agent's X position.
	 */
	public int getAgentX() {
		return agentX;
	}

	/**
	 * Returns the single.agent's Y position.
	 */
	public int getAgentY() {
		return agentY;
	}

	/**
	 * Changes the single.agent's X position.
	 */
	public void setAgentX(int x) {
		agentX = x;
	}

	/**
	 * Changes the single.agent's Y position.
	 */
	public void setAgentY(int y) {
		agentY = y;
	}


	/**
	 * Removes dirt from the specified location.
	 */
	public void killDeer(int x, int y) {
		// check if deer is in gun range - the shooter is in the middle (x,y) and we check their surroundings
		if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == DEER) {
			map[x - 1][y - 1] = CLEAR;
			isDeerAlive = false;
		}
		if (x - 1 >= 0 && map[x - 1][y] == DEER) {
			map[x - 1][y] = CLEAR;
			isDeerAlive = false;
		}
		if (x + 1 < n && y + 1 < n && map[x - 1][y + 1] == DEER) {
			map[x - 1][y + 1] = CLEAR;
			isDeerAlive = false;
		}

		if (y - 1 >= 0 && map[x][y - 1] == DEER) {
			map[x][y - 1] = CLEAR;
			isDeerAlive = false;
		}
		if (y + 1 < n && map[x][y + 1] == DEER) {
			map[x][y + 1] = CLEAR;
			isDeerAlive = false;
		}

		if (x + 1 < n && y - 1 >= 0 && map[x + 1][y - 1] == DEER) {
			map[x + 1][y - 1] = CLEAR;
			isDeerAlive = false;
		}
		if (x + 1 < n && map[x + 1][y] == DEER) {
			map[x + 1][y] = CLEAR;
			isDeerAlive = false;
		}
		if (x + 1 < n && y + 1 < n && map[x + 1][y + 1] == DEER) {
			map[x + 1][y + 1] = CLEAR;
			isDeerAlive = false;
		}
	}

	public boolean isDeerInEyesight(int x, int y) {
		for (int i : new int[]{-2, -1, 0, 1, 2}) {
			for (int j : new int[]{-2, -1, 0, 1, 2}) {
				if (inBounds(x + i, y + j) && map[x + i][y + j] == DEER) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDeerInGunsight(int x, int y) {
		return x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == DEER ||
				(x - 1 >= 0 && map[x - 1][y] == DEER) ||
				(x + 1 < n && y + 1 < n && map[x - 1][y + 1] == DEER) ||

				(y - 1 >= 0 && map[x][y - 1] == DEER) ||
				(y + 1 < n && map[x][y + 1] == DEER) ||

				(x + 1 < n && y - 1 >= 0 && map[x + 1][y - 1] == DEER) ||
				(x + 1 < n && map[x + 1][y] == DEER) ||
				(x + 1 < n && y + 1 < n && map[x + 1][y + 1] == DEER);
	}

	public int getDeerX(int x, int y) {  // only for eyesight range
		for (int i : new int[]{-2, -1, 0, 1, 2}) {
			for (int j : new int[]{-2, -1, 0, 1, 2}) {
				if (inBounds(x + i, y + j) && hasDeer(x + i, y + j)) {
					return x + i;
				}
			}
		}
		return -10;
	}

	public int getDeerY(int x, int y) {  // only for eyesight range (could be refactored, but I like only python tuples)
		for (int i : new int[]{-2, -1, 0, 1, 2}) {
			for (int j : new int[]{-2, -1, 0, 1, 2}) {
				if (inBounds(x + i, y + j) && hasDeer(x + i, y + j)) {
					return y + j;
				}
			}
		}
		return -10;
	}


	/**
	 * Returns true if the specified location has dirt in it.
	 */
	public boolean hasDeer(int x, int y) {
		if (map[x][y] == DEER)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if the specified location is a wall.
	 */
	public boolean isHill(int x, int y) {
		if (map[x][y] == HILL)
			return true;
		else
			return false;
	}


	/**
	 * Returns true if the location is within bounds of the state's map.
	 */
	public boolean inBounds(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			return true;
		else
			return false;
	}

	/**
	 * Returns the number of dirty locations in the state.
	 */
	public boolean getIsDeerAlive() {
		return isDeerAlive;
	}

	/**
	 * Prints an output of the state to the screen. This output includes a map as
	 * well as information about the single.agent's location and the direction it is
	 * facing. On the map, "A" denotes the single.agent and "*" denotes dirt.
	 */
	public void display() {
		for (int j = 1; j < width - 1; j++)
			System.out.print("  " + j);
		System.out.println();

		System.out.print(" ");
		for (int j = 1; j < width - 1; j++)
			System.out.print("+--");
		System.out.println("+");

		/*
		 * To print to the screen properly, the loop in the Y direction must be
		 * the outer loop.
		 */
		for (int i = 1; i < height - 1; i++) {
			System.out.print(i + "|");
			for (int j = 1; j < width - 1; j++) {
				if (i == agentY && j == agentX)
					System.out.print("A");
				else if (hasDeer(i, j))
					System.out.print("*");
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
		System.out.println("Location: (" + agentX + "," + agentY + ")");
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
}
