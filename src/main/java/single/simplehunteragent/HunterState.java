package single.simplehunteragent;

import single.PythonUtils;
import single.agent.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** Represents a state in the predator-prey world. */
public class HunterState extends State {

	/* Constants for the initial state of the single.agent. */
//	protected static int INIT_X = 1;
//	protected static int INIT_Y = 1;
	protected static int[] INIT_X = new int[]{1, 8};
	protected static int[] INIT_Y = new int[]{1, 8};

//	protected static int INIT_DEER_X = 5;
//	protected static int INIT_DEER_Y = 5;

	private static final int CLEAR = 0;
	private static final int DEER = 1;
	private static final int HILL = 2;
	private static final int HNTR = 3;

	/*
	 * Default map for initial state. The forest is completely surrounded by hills
	 */
	protected static int[][] defaultMap = {
			{HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL},
			{HILL, HNTR,  CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, DEER,  CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HNTR,  HILL},
			{HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL}
	};
	private static int n;

	//	private static int[] hunterLOS = new int[]{-2, -1, 0, 1, 2};
	private static int[] hunterLOS = new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4};  // Hunters' Lane Of Sight
//	private static int[] deerLOS = new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4};  // Deer's Lane Of Sight


	/* Variables for the state of the single.agent. */
//	protected int agentX;
//	protected int agentY;
	protected int[] agentX;
	protected int[] agentY;

//	protected int deerX;
//	protected int deerY;

	/**
	 * An array that contains the locations of objects in the world.
	 */
	protected int[][] map;

	protected boolean isDeerAlive = true;

	protected int height;
	protected int width;

	static final int DEFAULT_HEIGHT = 10;
	static final int DEFAULT_WIDTH = 10;

	/**
	 * Returns the default initial state for the predator-prey world.
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

//		state.deerX = INIT_DEER_X;
//		state.deerY = INIT_DEER_Y;
		n = state.width;
		return state;
	}

	public static void setDefaultMap(int[][] map) {
		defaultMap = map;
	}

	/**
	 * Constructs a new hunter state.
	 */
	public HunterState() {
	}

	/**
	 * Returns the single.agent's X position.
	 */
	public int getAgentX(int indx) {
		return agentX[indx];
	}

	/**
	 * Returns the deer.agent's X position.
	 */
//	public int getDeerX() {
//		return deerX;
//	}

	/**
	 * Returns the single.agent's Y position.
	 */
	public int getAgentY(int indx) {
		return agentY[indx];
	}

	/**
	 * Returns the deer.agent's Y position.
	 */
//	public int getDeerY() {
//		return deerY;
//	}

	/**
	 * Changes the single.agent's X position.
	 */
	public void setAgentX(int x, int indx) {
		map[agentX[indx]][agentY[indx]] = CLEAR;
		agentX[indx] = x;
		map[x][agentY[indx]] = HNTR;
	}

	/**
	 * Changes the deer.agent's X position.
	 */
//	public void setDeerX(int x) {
//		map[deerX][deerY] = CLEAR;
//		deerX = x;
//		map[x][deerY] = DEER;
//	}

	/**
	 * Changes the single.agent's Y position.
	 */
	public void setAgentY(int y, int indx) {
		map[agentX[indx]][agentY[indx]] = CLEAR;
		agentY[indx] = y;
		map[agentX[indx]][y] = HNTR;
	}

	/**
	 * Changes the deer.agent's Y position.
	 */
//	public void setDeerY(int y) {
//		map[deerX][deerY] = CLEAR;
//		deerY = y;
//		map[deerX][y] = DEER;
//	}

	/**
	 * Removes the deer from the specified location.
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
		for (int i : hunterLOS) {
			for (int j : hunterLOS) {
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
		for (int i : hunterLOS) {
			for (int j : hunterLOS) {
				if (inBounds(x + i, y + j) && hasDeer(x + i, y + j)) {
					return x + i;
				}
			}
		}
		return -10;
	}

	public int getDeerY(int x, int y) {  // only for eyesight range (could be refactored, but I like only python tuples)
		for (int i : hunterLOS) {
			for (int j : hunterLOS) {
				if (inBounds(x + i, y + j) && hasDeer(x + i, y + j)) {
					return y + j;
				}
			}
		}
		return -10;
	}


	/**
	 * Returns true if the specified location has the deer in it.
	 */
	public boolean hasDeer(int x, int y) {
		return map[x][y] == DEER;
	}

	/**
	 * Returns true if the specified location is a hill.
	 */
	public boolean isHill(int x, int y) {
		return map[x][y] == HILL;
	}


	/**
	 * Returns true if the location is within bounds of the state's map.
	 */
	public boolean inBounds(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	/**
	 * Returns the state of deer's life.
	 */
	public boolean isDeerAlive() {
		return isDeerAlive;
	}

	/**
	 * Prints an output of the state to the screen. This output includes a map as
	 * well as information about the single.agent's location and the direction it is
	 * facing. On the map, "A" denotes the single.agent and "*" denotes the deer.
	 */
	public void display() {
		for (int j = 1; j < width - 1; j++)
			System.out.print("  " + j);
		System.out.println();

		System.out.print(" ");
		for (int j = 1; j < width - 1; j++)
			System.out.print("+--");
		System.out.println("+");

		for (int i = 1; i < height - 1; i++) {
			System.out.print(i + "|");
			for (int j = 1; j < width - 1; j++) {
				for (int indx : PythonUtils.range(INIT_X.length)) {
					if (i == agentX[indx] && j == agentY[indx])
						System.out.print("A");
					else if (hasDeer(i, j))
						System.out.print("*");
					else
						System.out.print(" ");
					System.out.print(" |");
				}
			}
			System.out.println();

			System.out.print(" +");
			for (int j = 1; j < width - 2; j++)
				System.out.print("--+");
			System.out.println("--+");

		}
		System.out.println("Location A1: (" + agentX[0] + "," + agentY[0] + ")");
		System.out.println("Location A2: (" + agentX[1] + "," + agentY[1] + ")");
		System.out.println();

		System.out.println("Press RETURN to continue.");

		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		try {
			String input = console.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

//	public boolean isHunterInEyesight(int x, int y) {
////		for (int i : hunterLOS) {
////			for (int j : hunterLOS) {
////				return x + i == getAgentX() && y + j == getAgentY();
////			}
////		}
////		return false;
//		for (int i : deerLOS) {
//			for (int j : deerLOS) {
//				if (inBounds(x + i, y + j) && map[x + i][y + j] == HNTR1) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

}
