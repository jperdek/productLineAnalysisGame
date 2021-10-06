package battleship;

public class Player {
	private static int[] SHIP_DEFAULT_LENGTH = { 5};
	// These are the lengths of all of the ships.
	private int[] ship_lengths = { 5 }; // { 2, 3, 3, 4, 5 };
	private int num_of_ships = 1; // 5

	public Ship[] ships;
	public Grid playerGrid;
	public Grid oppGrid;

	public Player() {
		this(Player.SHIP_DEFAULT_LENGTH);
	}
	
	public Player(int[] ship_lengths) {
		this.ship_lengths = ship_lengths;
		this.num_of_ships = this.ship_lengths.length;
		
		ships = new Ship[this.num_of_ships];
		for (int i = 0; i < this.num_of_ships; i++) {
			Ship tempShip = new Ship(this.ship_lengths[i]);
			ships[i] = tempShip;
		}

		playerGrid = new Grid();
		oppGrid = new Grid();
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
}