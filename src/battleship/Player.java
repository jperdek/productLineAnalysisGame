package battleship;

import java.util.Scanner;

public class Player extends AbstractPlayer{

	
	
	public Player(String id, BoardManager boardManager) {
		super(id, AbstractPlayer.SHIP_DEFAULT_LENGTH, boardManager);
	}
	
	public Player(String id, int[] ship_lengths, BoardManager boardManager) {
		super(id, ship_lengths, boardManager);
		this.setup();
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
}