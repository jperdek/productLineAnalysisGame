package computerOpponent;

import battleship.BoardManager;
import battleship.ComputerPlayer;
import configurationManagement.Configuration;
import battleship.Board;
import battleship.AbstractPlayer;
import battleship.Battleship;

//@{"computerOpponent": "true"}
public aspect ComputerInstantiator {

	pointcut useComputerInCaseOfPlayer(
			String player1ID, Board playerBoard1, String playerID2, Board playerBoard2): 
		call(* BoardManager.registerPlayerComputer(String, Board, String, Board)) 
				&& args(player1ID, playerBoard1, playerID2, playerBoard2);
	
	pointcut instantiateComputerInCaseOfPlayer(
			String opponentID, int[] playerShips, BoardManager boardManager): 
		call(AbstractPlayer *.instantiateOpponent(String, int[], BoardManager))
				&& args(opponentID, playerShips, boardManager); 
	
	pointcut instantiateComputerInCaseOfPlayer2(
			String opponentID, BoardManager boardManager): 
		call(AbstractPlayer *.instantiateOpponent(String, BoardManager))
				&& args(opponentID,  boardManager);
	
	pointcut manageOpponentTurn(Battleship battleship, AbstractPlayer player1, AbstractPlayer player2): 
		call(* Battleship.opponentTurn(AbstractPlayer, AbstractPlayer))
				&& args(player1, player2) && this(battleship); 
	
	void around(String player1ID, Board playerBoard1,String playerID2,Board playerBoard2):
		useComputerInCaseOfPlayer(player1ID, playerBoard1, playerID2, playerBoard2) {
		if (Configuration.computerOpponent) {
			System.out.println("SETUP for computer!");
			proceed(player1ID, playerBoard1, "COMPUTER", playerBoard2);
			return;
		} 
		
		proceed(player1ID, playerBoard1, playerID2, playerBoard2);
		return;
	}
	
	AbstractPlayer around(String opponentID, int[] playerShips, BoardManager boardManager):
		instantiateComputerInCaseOfPlayer(opponentID, playerShips, boardManager) {
		if (Configuration.computerOpponent) {
			System.out.println("Creating computer ---------- !");
			return new ComputerPlayer("COMPUTER", playerShips, boardManager);
		} 
		return proceed(opponentID, playerShips, boardManager);
	}
	
	AbstractPlayer around(String opponentID, BoardManager boardManager):
		instantiateComputerInCaseOfPlayer2(opponentID, boardManager) {
		if (Configuration.computerOpponent) {
			System.out.println("Creating computer ---------- !");
			return new ComputerPlayer("COMPUTER", boardManager);
		} 
		return proceed(opponentID, boardManager);
	}
	
	void around(Battleship battleship, AbstractPlayer player1, AbstractPlayer player2):
		manageOpponentTurn(battleship, player1, player2) {
		if (Configuration.computerOpponent) {
			// battleship.compMakeGuess(player1, player2); FOR OBJECT ORIENTATED WAY OPTION IN FUTURE
			Battleship.compMakeGuess(player1, player2);
		} else {
			proceed(battleship, player1, player2);
		}
	}
}
