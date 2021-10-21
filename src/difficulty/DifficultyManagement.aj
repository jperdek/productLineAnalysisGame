package difficulty;

import battleship.AbstractPlayer;
import battleship.Battleship;
import battleship.BoardManager;
import configurationManagement.Configuration;

//@{}
public aspect DifficultyManagement {
	pointcut manageDifficultyDuringInstantiationOfPlayerOpponent(
			String opponentID, int[] playerShips, BoardManager boardManager): 
		call(AbstractPlayer Battleship.instantiateOpponent(String, int[], BoardManager))
				&& args(opponentID, playerShips, boardManager)  && !within(DifficultyManagement);
	
	pointcut manageDifficultyDuringInstantiationOfPlayerOpponent2(
			Battleship battleship, String opponentID, BoardManager boardManager): 
		call(AbstractPlayer Battleship.instantiateOpponent(String, BoardManager))
				&& args(opponentID, boardManager) && this(battleship);
	
	pointcut manageDifficultyDuringInstantiationOfPlayerPlayer(
			String playerID, int[] playerShips, BoardManager boardManager): 
		call(AbstractPlayer Battleship.instantiatePlayer(String, int[], BoardManager))
				&& args(playerID, playerShips, boardManager)  && !within(DifficultyManagement);
	
	pointcut manageDifficultyDuringInstantiationOfPlayerPlayer2(
			Battleship battleship, String playerID, BoardManager boardManager): 
		call(AbstractPlayer Battleship.instantiatePlayer(String, BoardManager))
				&& args(playerID, boardManager) && this(battleship); 
				
	AbstractPlayer around(String opponentID, int[] playerShips, BoardManager boardManager):
		manageDifficultyDuringInstantiationOfPlayerOpponent(opponentID, playerShips, boardManager) {
		return proceed(opponentID, Configuration.opponentShips, boardManager);
	}

	AbstractPlayer around(Battleship battleship, String opponentID, BoardManager boardManager):
		manageDifficultyDuringInstantiationOfPlayerOpponent2(battleship, opponentID, boardManager) {
		return battleship.instantiateOpponent(opponentID, Configuration.opponentShips, boardManager);
	}
	
	AbstractPlayer around(String playerID, int[] playerShips, BoardManager boardManager):
		manageDifficultyDuringInstantiationOfPlayerPlayer(playerID, playerShips, boardManager) {
		return proceed(playerID, Configuration.playerShips, boardManager);
	}

	AbstractPlayer around(Battleship battleship, String playerID, BoardManager boardManager):
		manageDifficultyDuringInstantiationOfPlayerPlayer2(battleship, playerID, boardManager) {
		return battleship.instantiatePlayer(playerID, Configuration.playerShips, boardManager);
	}
}
