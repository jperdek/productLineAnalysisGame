package battleship.statistics;

import battleship.Player;
import configurationManagement.Configuration;

//@{"statistics": "true"}
public aspect SuccessMetric {
	StatisticManager statisticManager = new StatisticManager();

	public pointcut playerMove(Player processedPlayer, Player otherPlayer): 
		call(* *.*Guess(Player, Player)) && args(processedPlayer, otherPlayer)
		&& if(Configuration.statistics);
	
	public pointcut hasShipPointcut(Player player): 
		call(boolean battleship.Grid.hasShip(..)) && this(player) 
		&& if(Configuration.statistics);
	
	boolean around(Player processedPlayer): hasShipPointcut(processedPlayer) {
		String playerId = processedPlayer.getId() + StatisticVariableNames.HITS;
		boolean result = proceed(processedPlayer);

		if(result) {
			IntegerObject playerHit = (IntegerObject) statisticManager.getVariable(playerId);
			if (playerHit == null) {
				playerHit = new IntegerObject(0);
				playerHit.increaseValue();
			} else {
				playerHit.increaseValue();
			}
			statisticManager.addVariable(playerId, playerHit);
		}
		System.out.println("Player: " + playerId + 
				" Unsuccessful hits: " + Integer.toString(getUnsuccessfulHits(processedPlayer)));
		return result;
	}
	
	public int getUnsuccessfulHits(Player processedPlayer) {
		String hitsId = processedPlayer.getId() + StatisticVariableNames.HITS;
		String movesId = processedPlayer.getId() + StatisticVariableNames.MOVES;
		IntegerObject movesCount = (IntegerObject) statisticManager.getVariable(movesId);
		IntegerObject hitsCount = (IntegerObject) statisticManager.getVariable(hitsId);
		if(hitsCount == null && movesCount != null) {
			return movesCount.getValue();
		}
		if (hitsCount == null) {
			System.out.println("Unknown values - values has not been inserted yet!");
			return 0;
		}
		if(movesCount == null) {
			return 0;
		}
		return movesCount.getValue() - hitsCount.getValue();
	}
	
	before(Player processedPlayer, Player otherPlayer): playerMove(processedPlayer, otherPlayer) {
		String playerId = processedPlayer.getId() + StatisticVariableNames.MOVES;
		IntegerObject playerMove = (IntegerObject) statisticManager.getVariable(playerId);
		if (playerMove == null) {
			playerMove = new IntegerObject(0);
			playerMove.increaseValue();
		} else {
			playerMove.increaseValue();
		}
		statisticManager.addVariable(playerId, playerMove);
		
		IntegerObject playerMove1 = (IntegerObject) statisticManager.getVariable(playerId);
		System.out.println(playerMove1.getValue());
	}
	
	after(Player processedPlayer, Player otherPlayer): playerMove(processedPlayer, otherPlayer) {
		System.out.println(thisJoinPoint);
		//statisticManager.getVariable(")
	}
}
