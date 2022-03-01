package single.mineragent;

import single.PythonUtils;
import single.agent.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** Represents a state in the gold-mining world. */
public class MinerState extends State {

	/* Constants for the initial state of the single.agent. */
	protected static int[] INIT_X = new int[]{1, 8};
	protected static int[] INIT_Y = new int[]{1, 8};

	private static final int CLEAR = 0;
	private static final int GOLD = 1;
	private static final int HILL = 2;
	private static final int MINR = 3;

	/*
	 * Default map for initial state. The forest is completely surrounded by hills
	 */
	protected static int[][] defaultMap = {
			{HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL},
			{HILL, MINR,  CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, GOLD,  CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL},
			{HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, MINR,  HILL},
			{HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL}
	};
	private static int n;

	//	private static int[] minerLOS = new int[]{-2, -1, 0, 1, 2};
	private static int[] minerLOS = new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4};  // Miner' Lane Of Sight

	/* Variables for the state of the single.agent. */
	protected int[] agentX;
	protected int[] agentY;

	/**
	 * An array that contains the locations of objects in the world.
	 */
	protected int[][] map;

	protected boolean isGoldRemaining = true;
	protected int goldQuantity = 10;

	protected int height;
	protected int width;

	/**
	 * Returns the default initial state for the gold-mining world.
	 */
	public static MinerState getInitState() {

		MinerState state;

		state = new MinerState();
		state.width = defaultMap.length;
		state.height = defaultMap[0].length;
		state.map = new int[state.width][state.height];
		state.map = defaultMap;
		state.agentX = INIT_X;
		state.agentY = INIT_Y;
		n = state.width;
		return state;
	}

	public static void setDefaultMap(int[][] map) {
		defaultMap = map;
	}

	/**
	 * Constructs a new miner state.
	 */
	public MinerState() {
	}

	/**
	 * Returns the single.agent's X position.
	 */
	public int getAgentX(int indx) {
		return agentX[indx];
	}

	/**
	 * Returns the single.agent's Y position.
	 */
	public int getAgentY(int indx) {
		return agentY[indx];
	}


	/**
	 * Changes the single.agent's X position.
	 */
	public void setAgentX(int x, int indx) {
		map[agentX[indx]][agentY[indx]] = CLEAR;
		agentX[indx] = x;
		map[x][agentY[indx]] = MINR;
	}

	/**
	 * Changes the single.agent's Y position.
	 */
	public void setAgentY(int y, int indx) {
		map[agentX[indx]][agentY[indx]] = CLEAR;
		agentY[indx] = y;
		map[agentX[indx]][y] = MINR;
	}

	/**
	 * Removes some of the gold from the specified location.
	 */
	public void mineGold(int x, int y) {
		boolean flag = false;
		// check if gold is in pickaxe range - the miner is in the middle (x,y) and we check their surroundings
		if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x - 1][y - 1] = CLEAR;
			}
			flag = true;
		}
		if (x - 1 >= 0 && map[x - 1][y] == GOLD) {
			if (goldQuantity == 1) {
				map[x - 1][y] = CLEAR;
			}
			flag = true;
		}
		if (x + 1 < n && y + 1 < n && map[x - 1][y + 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x - 1][y + 1] = CLEAR;
			}
			flag = true;
		}

		if (y - 1 >= 0 && map[x][y - 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x][y - 1] = CLEAR;
			}
			flag = true;
		}
		if (y + 1 < n && map[x][y + 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x][y + 1] = CLEAR;
			}
			flag = true;
		}

		if (x + 1 < n && y - 1 >= 0 && map[x + 1][y - 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x + 1][y - 1] = CLEAR;
			}
			flag = true;
		}
		if (x + 1 < n && map[x + 1][y] == GOLD) {
			if (goldQuantity == 1) {
				map[x + 1][y] = CLEAR;
			}
			flag = true;
		}
		if (x + 1 < n && y + 1 < n && map[x + 1][y + 1] == GOLD) {
			if (goldQuantity == 1) {
				map[x + 1][y + 1] = CLEAR;
			}
			flag = true;
		}
		if (flag) {
			if (goldQuantity == 1) {
				isGoldRemaining = false;
			}
			goldQuantity--;
		}
	}

	public boolean isGoldInEyesight(int x, int y) {
		for (int i : minerLOS) {
			for (int j : minerLOS) {
				if (inBounds(x + i, y + j) && map[x + i][y + j] == GOLD) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean canMineGold(int x, int y) {
		return x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == GOLD ||
				(x - 1 >= 0 && map[x - 1][y] == GOLD) ||
				(x + 1 < n && y + 1 < n && map[x - 1][y + 1] == GOLD) ||

				(y - 1 >= 0 && map[x][y - 1] == GOLD) ||
				(y + 1 < n && map[x][y + 1] == GOLD) ||

				(x + 1 < n && y - 1 >= 0 && map[x + 1][y - 1] == GOLD) ||
				(x + 1 < n && map[x + 1][y] == GOLD) ||
				(x + 1 < n && y + 1 < n && map[x + 1][y + 1] == GOLD);
	}

	public int getGoldX(int x, int y) {  // only for eyesight range
		for (int i : minerLOS) {
			for (int j : minerLOS) {
				if (inBounds(x + i, y + j) && hasGold(x + i, y + j)) {
					return x + i;
				}
			}
		}
		return -10;
	}

	public int getGoldY(int x, int y) {  // only for eyesight range (could be refactored, but I like only python tuples)
		for (int i : minerLOS) {
			for (int j : minerLOS) {
				if (inBounds(x + i, y + j) && hasGold(x + i, y + j)) {
					return y + j;
				}
			}
		}
		return -10;
	}


	/**
	 * Returns true if the specified location has the gold in it.
	 */
	public boolean hasGold(int x, int y) {
		return map[x][y] == GOLD;
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
	 * Returns the state of the gold deposit.
	 */
	public boolean isGoldRemaining() {
		return isGoldRemaining;
	}

	/**
	 * Prints an output of the state to the screen. This output includes a map as
	 * well as information about the single.agent's location and the direction it is
	 * facing. On the map, "A" denotes the single.agent and "*" denotes the gold.
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
					else if (hasGold(i, j))
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

}
