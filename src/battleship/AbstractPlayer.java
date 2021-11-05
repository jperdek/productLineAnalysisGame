package battleship;

//@{}
public abstract class AbstractPlayer {
	protected static int[] SHIP_DEFAULT_LENGTH = { 5};
	// These are the lengths of all of the ships.
	protected int[] shipLengths = { 2 }; // { 2, 3, 3, 4, 5 };
	protected int numOfShips = 1; // 5
	protected String id;
	protected Board board;
	
	protected Ship[] ships;
	protected Grid playerGrid;
	protected Grid oppGrid;
	
	
	public AbstractPlayer(String id, BoardManager boardManager) {
		this(id, Player.SHIP_DEFAULT_LENGTH, boardManager);
	}
	
	public AbstractPlayer(String id, int[] shipLengths, BoardManager boardManager) {
		this.id = id;
		this.board = boardManager.getBoard(this.id);
		Board oponentsBoard = boardManager.getOppositeBoards(this.id).get(0);
		
		this.shipLengths = shipLengths;
		this.numOfShips = this.shipLengths.length;
		
		int costOfWholeShips = 0;
		ships = new Ship[this.numOfShips];
		for (int i = 0; i < this.numOfShips; i++) {
			Ship tempShip = new Ship(this.shipLengths[i]);
			ships[i] = tempShip;
			costOfWholeShips = costOfWholeShips + this.shipLengths[i];
		}

		playerGrid = new Grid(board.getAreaRowsWidth(), board.getAreaColsHeight(), costOfWholeShips);
		oppGrid = new Grid(oponentsBoard.getAreaRowsWidth(), oponentsBoard.getAreaColsHeight(), -1);
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
		int counter = this.numOfShips;
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
	
	
	protected boolean hasErrors(int row, int col, int dir, int count) {
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
