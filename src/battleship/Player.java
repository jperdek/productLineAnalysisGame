package battleship;

import java.util.Scanner;

public class Player {
	private static int[] SHIP_DEFAULT_LENGTH = { 5};
	// These are the lengths of all of the ships.
	private int[] ship_lengths = { 2 }; // { 2, 3, 3, 4, 5 };
	private int num_of_ships = 1; // 5
	private String id;
	private Board board;
	
	private Ship[] ships;
	private Grid playerGrid;
	private Grid oppGrid;
	
	
	public Player(String id, BoardManager boardManager) {
		this(id, Player.SHIP_DEFAULT_LENGTH, boardManager);
	}
	
	public Player(String id, int[] ship_lengths, BoardManager boardManager) {
		this.id = id;
		this.board = boardManager.getBoard(this.id);
		Board oponentsBoard = boardManager.getOppositeBoards(this.id).get(0);
		
		this.ship_lengths = ship_lengths;
		this.num_of_ships = this.ship_lengths.length;
		
		ships = new Ship[this.num_of_ships];
		for (int i = 0; i < this.num_of_ships; i++) {
			Ship tempShip = new Ship(this.ship_lengths[i]);
			ships[i] = tempShip;
		}

		playerGrid = new Grid(board.getAreaRowsWidth(), board.getAreaColsHeight());
		oppGrid = new Grid(oponentsBoard.getAreaRowsWidth(), oponentsBoard.getAreaColsHeight());
		if(this.id != "COMPUTER") {
			this.setup();
		} else {
			this.setupComputer();
		}
	}

	public String getId() {
		return this.id;
	}
	
	public Board getPlayerBoard() {
		return this.board;
	}
	
	public void addShips() {
		for (Ship s : ships) {
			playerGrid.addShip(s);
		}
	}

	public int numOfShipsLeft() {
		int counter = this.num_of_ships;
		for (Ship s : ships) {
			if (s.isLocationSet() && s.isDirectionSet())
				counter--;
		}

		return counter;
	}

	public void chooseShipLocation(Ship s, int row, int col, int direction) {
		s.setLocation(row, col);
		s.setDirection(direction);
		playerGrid.addShip(s);
	}
	
	public boolean hasShipOnGrid(int row, int col) {
		return playerGrid.hasShip(row, col);
	}
	
	public void markMissPlayer(int row, int col) {
		playerGrid.markMiss(row, col);
	}
	
	public void markMissOpponent(int row, int col) {
		oppGrid.markMiss(row, col);
	}

	public void markHitPlayer(int row, int col) {
		playerGrid.markHit(row, col);
	}
	
	public void markHitOpponent(int row, int col) {
		oppGrid.markHit(row, col);
	}
	
	private void setup() {
		this.playerGrid.printShips();
		Scanner reader = InputReader.getReader();
		System.out.println();
		int counter = 1;
		int normCounter = 0;
		while (this.numOfShipsLeft() > 0) {
			for (Ship s : this.ships) {
				System.out.println("\nShip #" + counter + ": Length-" + s.getLength());
				int row = -1;
				int col = -1;
				int dir = -1;
				while (true) {
					System.out.print("Type in row (A-J): ");
					String userInputRow = reader.next();
					userInputRow = userInputRow.toUpperCase();
					row = GridHelper.convertLetterToInt(userInputRow);

					System.out.print("Type in column (1-10): ");
					col = reader.nextInt();
					col = GridHelper.convertUserColToProCol(col);

					System.out.print("Type in direction (0-H, 1-V): ");
					dir = reader.nextInt();

					// System.out.println("DEBUG: " + row + col + dir);

					if (col >= 0 && col <= this.board.getAreaColsHeight() && row != -1 && dir != -1) // Check valid input
					{
						if (!hasErrors(row, col, dir, normCounter)) // Check if errors will produce (out of bounds)
						{
							break;
						}
					}

					System.out.println("Invalid location!");
				}

				// System.out.println("FURTHER DEBUG: row = " + row + "; col = " + col);
				this.ships[normCounter].setLocation(row, col);
				this.ships[normCounter].setDirection(dir);
				this.playerGrid.addShip(this.ships[normCounter]);
				this.playerGrid.printShips();
				System.out.println();
				System.out.println("You have " + this.numOfShipsLeft() + " remaining ships to place.");

				normCounter++;
				counter++;
			}
		}
	}
	
	private boolean hasErrors(int row, int col, int dir, int count) {
		// System.out.println("DEBUG: count arg is " + count);
		int maxRowRestriction = this.board.getAreaRowsWidth();
		int maxColRestriction = this.board.getAreaColsHeight();
		int length = this.ships[count].getLength();

		// Check if off grid - Horizontal
		if (dir == 0) {
			int checker = length + col;
			// System.out.println("DEBUG: checker is " + checker);
			if (checker > maxColRestriction) {
				if(this.id != "COMPUTER") { System.out.println("SHIP DOES NOT FIT"); }
				return true;
			}
		}

		// Check if off grid - Vertical
		if (dir == 1) // VERTICAL
		{
			int checker = length + row;
			// System.out.println("DEBUG: checker is " + checker);
			if (checker > maxRowRestriction) {
				if(this.id != "COMPUTER") { System.out.println("SHIP DOES NOT FIT"); }
				return true;
			}
		}

		// Check if overlapping with another ship
		if (dir == 0) // Hortizontal
		{
			// For each location a ship occupies, check if ship is already there
			for (int i = col; i < col + length; i++) {
				// System.out.println("DEBUG: row = " + row + "; col = " + i);
				if (this.playerGrid.hasShip(row, i)) {
					if(this.id != "COMPUTER") { System.out.println("THERE IS ALREADY A SHIP AT THAT LOCATION"); }
					return true;
				}
			}
		} else if (dir == 1) // Vertical
		{
			// For each location a ship occupies, check if ship is already there
			for (int i = row; i < row + length; i++) {
				// System.out.println("DEBUG: row = " + row + "; col = " + i);
				if (this.playerGrid.hasShip(i, col)) {
					if(this.id != "COMPUTER") {System.out.println("THERE IS ALREADY A SHIP AT THAT LOCATION");}
					return true;
				}
			}
		}

		return false;
	}

	private void setupComputer() {
		System.out.println();
		int normCounter = 0;
		int maxRowRestriction = this.board.getAreaRowsWidth() - 1;
		int maxColRestriction = this.board.getAreaColsHeight() - 1;

		while (this.numOfShipsLeft() > 0) {
			for (Ship s : this.ships) {
				int row = Randomizer.nextInt(0, maxRowRestriction);
				int col = Randomizer.nextInt(0, maxColRestriction);
				int dir = Randomizer.nextInt(0, 1);

				// System.out.println("DEBUG: row-" + row + "; col-" + col + "; dir-" + dir);

				while (hasErrors(row, col, dir, normCounter)) // while the random nums make error, start again
				{
					row = Randomizer.nextInt(0, maxRowRestriction);
					col = Randomizer.nextInt(0, maxColRestriction);
					dir = Randomizer.nextInt(0, 1);
					// System.out.println("AGAIN-DEBUG: row-" + row + "; col-" + col + "; dir-" +
					// dir);
				}

				// System.out.println("FURTHER DEBUG: row = " + row + "; col = " + col);
				this.ships[normCounter].setLocation(row, col);
				this.ships[normCounter].setDirection(dir);
				this.playerGrid.addShip(this.ships[normCounter]);

				normCounter++;
			}
		}
	}
	
	public boolean hasLost() {
		return this.playerGrid.hasLost();
	}
	
	public void printShips() {
		this.playerGrid.printShips();
	}
	
	public void printCombined() {
		this.playerGrid.printCombined();
	}
	
	public void printOpponentGridStatus() {
		this.oppGrid.printStatus();
	}
	
	public boolean alreadyGuessedGrid(int row, int col) {
		return this.oppGrid.alreadyGuessed(row, col);
	}
}