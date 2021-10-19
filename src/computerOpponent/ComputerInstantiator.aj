package computerOpponent;

import battleship.BoardManager;
import battleship.ComputerPlayer;
import battleship.Board;
import battleship.AbstractPlayer;
import battleship.Battleship;

public aspect ComputerInstantiator {
	private static final boolean INSTANTIATE_COMPUTER = true;

	pointcut useComputerInCaseOfPlayer(
			String player1ID, Board playerBoard1, String playerID2, Board playerBoard2): 
		call(* BoardManager.registerPlayerComputer(String, Board, String, Board)) 
				&& args(player1ID, playerBoard1, playerID2, playerBoard2);
	
	pointcut instantiateComputerInCaseOfPlayer(
			String opponentID, int[] playerShips, BoardManager boardManager): 
		call(AbstractPlayer Battleship.instantiateOpponent(String, int[], BoardManager))
				&& args(opponentID, playerShips, boardManager); 
		
	pointcut manageOpponentTurn(Battleship battleship, AbstractPlayer player1, AbstractPlayer player2): 
		call(* Battleship.opponentTurn(AbstractPlayer, AbstractPlayer))
				&& args(player1, player2) && this(battleship); 
	
	void around(String player1ID, Board playerBoard1,String playerID2,Board playerBoard2):
		useComputerInCaseOfPlayer(player1ID, playerBoard1, playerID2, playerBoard2) {
		if (ComputerInstantiator.INSTANTIATE_COMPUTER) {
			System.out.println("SETUP for computer!");
			proceed(player1ID, playerBoard1, "COMPUTER", playerBoard2);
			return;
		} 
		
		proceed(player1ID, playerBoard1, playerID2, playerBoard2);
		return;
	}
	
	AbstractPlayer around(String opponentID, int[] playerShips, BoardManager boardManager):
		instantiateComputerInCaseOfPlayer(opponentID, playerShips, boardManager) {
		if (ComputerInstantiator.INSTANTIATE_COMPUTER) {
			System.out.println("Creating computer ---------- !");
			return new ComputerPlayer("COMPUTER", playerShips, boardManager);
		} 
		
		return proceed(opponentID, playerShips, boardManager);
	}
	
	void around(Battleship battleship, AbstractPlayer player1, AbstractPlayer player2):
		manageOpponentTurn(battleship, player1, player2) {
		if (ComputerInstantiator.INSTANTIATE_COMPUTER) {
			battleship.compMakeGuess(player1, player2);
		} else {
			proceed(battleship, player1, player2);
		}
	}
}
