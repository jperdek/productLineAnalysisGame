package battleship;

import java.util.Scanner;

import configurationManagement.ConfigurationLoader;

//@{}
public class Battleship {
	public static Scanner reader = InputReader.getReader();
	public BoardManager boardManager = new BoardManager();
	public AbstractPlayer userPlayer, computer;
	
	
	public Battleship() {
		printAbout();
		
		boardManager = new BoardManager();
		boardManager.registerPlayerComputer("PLAYER", new Board(), "PLAYER2", new Board());
		
		
		System.out.println("\nPlayer SETUP:");
		userPlayer = this.instantiatePlayer("PLAYER", boardManager);

		System.out.println("OPPONENT SETUP...DONE...PRESS ENTER TO CONTINUE...");
		reader.nextLine();
		reader.nextLine();
		
		computer = this.instantiateOpponent("PLAYER2", boardManager);
		
		System.out.println("\nCOMPUTER GRID (FOR DEBUG)...");
		computer.printShips();

		while (true) {
			System.out.println("\n--------------------------------------------------------------------");
			System.out.println("\nUSER MAKING GUESS...");
			askForGuess(userPlayer, computer);

			if (checkFinalState(userPlayer, computer)) { break; }

			System.out.println("\n--------------------------------------------------------------------");
			System.out.println("\n OPPONENT IS MAKING GUESS...");

			opponentTurn(computer, userPlayer);
			
			if (checkFinalState(userPlayer, computer)) { break; }
		}
	}
	
	private void opponentTurn(AbstractPlayer computer, AbstractPlayer userPlayer) {
		askForGuess(computer, userPlayer);
	}
	
	private boolean checkFinalState(AbstractPlayer userPlayer, AbstractPlayer computer) {
		if (userPlayer.hasLost()) {
			System.out.println("OPPONENT HIT!...USER LOSES");
			return true;
		} else if (computer.hasLost()) {
			System.out.println("HIT!...OPPONENT LOSES");
			return true;
		}
		return false;
	}
	
	public AbstractPlayer instantiateOpponent(String opponentID, int[] playerShips, BoardManager boardManager) {
		return new Player(opponentID, playerShips, boardManager);
	}

	public AbstractPlayer instantiateOpponent(String opponentID, BoardManager boardManager) {
		return new Player(opponentID, boardManager);
	}
	
	public AbstractPlayer instantiatePlayer(String playerID, int[] playerShips, BoardManager boardManager) {
		return new Player(playerID, playerShips, boardManager);
	}
	
	public AbstractPlayer instantiatePlayer(String playerID, BoardManager boardManager) {
		return new Player(playerID, boardManager);
	}
	
	//needs to be public
	public static void compMakeGuess(AbstractPlayer comp, AbstractPlayer user) {
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
			System.out.println("OPPONENT HIT AT " + 
					GridHelper.convertIntToLetter(row) + GridHelper.convertCompColToRegular(col));
		} else {
			comp.markMissOpponent(row, col);
			user.markMissPlayer(row, col);
			System.out.println("OPPONENT MISS AT " + 
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
	
	private static String askForGuess(AbstractPlayer p, AbstractPlayer opp) {
		System.out.println("Viewing My Guesses:");
		p.printOpponentGridStatus();
		int maxColRestriction = p.getPlayerBoard().getAreaColsHeight() - 1;
		int row = -1;
		int col = -1;

		String oldRow = "Z";
		int oldCol = -1;

		while (true) {
			System.out.print("Type in row (A-n): ");
			String userInputRow = reader.next();
			userInputRow = userInputRow.toUpperCase();
			oldRow = userInputRow;
			row = GridHelper.convertLetterToInt(userInputRow);

			System.out.print("Type in column (1-n): ");
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
	
	
	public static void main(String[] args) {
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/battleshipConfig.json");
		Battleship battleshipGame = new Battleship();
	}
}