package battleship;

import java.util.Scanner;

public class Battleship {
	public static Scanner reader = InputReader.getReader();
	public BoardManager boardManager = new BoardManager();
	public Player userPlayer, computer;
	
	
	public Battleship() {
		printAbout();
		
		boardManager = new BoardManager();
		//boardManager.registerPlayerComputer("PLAYER", new Board(), "COMPUTER", new Board());
		boardManager.registerPlayerComputer("PLAYER", new Board(), "PLAYER2", new Board());
		
		
		System.out.println("\nPlayer SETUP:");
		userPlayer = new Player("PLAYER", boardManager);

		System.out.println("Computer SETUP...DONE...PRESS ENTER TO CONTINUE...");
		reader.nextLine();
		reader.nextLine();
		int[] player_ships = {2};
		//computer = new Player("COMPUTER", player_ships, boardManager);
		computer = new Player("PLAYER2", player_ships, boardManager);
		System.out.println("\nCOMPUTER GRID (FOR DEBUG)...");
		computer.printShips();

		String result = "";
		while (true) {
			System.out.println("\n--------------------------------------------------------------------");
			System.out.println("\nUSER MAKING GUESS...");
			result = askForGuess(userPlayer, computer);

			if (userPlayer.hasLost()) {
				System.out.println("COMP HIT!...USER LOSES");
				break;
			} else if (computer.hasLost()) {
				System.out.println("HIT!...COMPUTER LOSES");
				break;
			}

			System.out.println("\n--------------------------------------------------------------------");
			System.out.println("\nCOMPUTER IS MAKING GUESS...");

			//compMakeGuess(computer, userPlayer);
			result = askForGuess(computer, userPlayer);
			
			if (userPlayer.hasLost()) {
				System.out.println("COMP HIT!...USER LOSES");
				break;
			} else if (computer.hasLost()) {
				System.out.println("HIT!...COMPUTER LOSES");
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Battleship battleshipGame = new Battleship();
	}

	private static void compMakeGuess(Player comp, Player user) {
		int maxRowRestriction = comp.getPlayerBoard().getAreaRowsWidth() - 1;
		int maxColRestriction = comp.getPlayerBoard().getAreaColsHeight() - 1;
		
		int row = Randomizer.nextInt(0, maxRowRestriction);
		int col = Randomizer.nextInt(0, maxColRestriction);

		// While computer already guessed this position, make a new random guess
		while (comp.alreadyGuessedGrid(row, col)) {
			row = Randomizer.nextInt(0, maxRowRestriction);
			col = Randomizer.nextInt(0, maxColRestriction);
		}

		if (user.hasShipOnGrid(row, col)) {
			comp.markHitOpponent(row, col);
			user.markMissPlayer(row, col);
			System.out.println("COMP HIT AT " + 
					GridHelper.convertIntToLetter(row) + GridHelper.convertCompColToRegular(col));
		} else {
			comp.markMissOpponent(row, col);
			user.markMissPlayer(row, col);
			System.out.println("COMP MISS AT " + 
					GridHelper.convertIntToLetter(row) + GridHelper.convertCompColToRegular(col));
		}

		System.out.println("\nYOUR BOARD...PRESS ENTER TO CONTINUE...");
		reader.nextLine();
		user.printCombined();
		System.out.println("PRESS ENTER TO CONTINUE...");
		reader.nextLine();
	}

	private static void printAbout() {
		System.out.println("JAVA BATTLESHIP - ** Yuval Marcus ** and remake by Jakub Perdek");
	}
	
	private static String askForGuess(Player p, Player opp) {
		System.out.println("Viewing My Guesses:");
		p.printOpponentGridStatus();
		int maxColRestriction = p.getPlayerBoard().getAreaColsHeight() - 1;
		int row = -1;
		int col = -1;

		String oldRow = "Z";
		int oldCol = -1;

		while (true) {
			System.out.print("Type in row (A-J): ");
			String userInputRow = reader.next();
			userInputRow = userInputRow.toUpperCase();
			oldRow = userInputRow;
			row = GridHelper.convertLetterToInt(userInputRow);

			System.out.print("Type in column (1-10): ");
			col = reader.nextInt();
			oldCol = col;
			col = GridHelper.convertUserColToProCol(col);

			// System.out.println("DEBUG: " + row + col);

			if (col >= 0 && col <= maxColRestriction && row != -1)
				break;

			System.out.println("Invalid location!");
		}

		if (opp.hasShipOnGrid(row, col)) {
			p.markHitOpponent(row, col);
			opp.markHitPlayer(row, col);
			return "** USER HIT AT " + oldRow + oldCol + " **";
		} else {
			p.markMissOpponent(row, col);
			opp.markMissPlayer(row, col);
			return "** USER MISS AT " + oldRow + oldCol + " **";
		}
	}

}