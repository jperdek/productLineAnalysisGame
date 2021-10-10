package battleship.statistics;

import battleship.Grid;
import battleship.Player;

public aspect SuccessMetric {
	StatisticManager statisticManager = new StatisticManager();
	
	public pointcut playerMove(Player processedPlayer, Player otherPlayer): 
		call(* *.*Guess(Player, Player)) && args(processedPlayer, otherPlayer);
	//public pointcut playerScore(): call(* );
	
	public pointcut hasShipPointcut(Player player): call(boolean battleship.Grid.hasShip(..)) && this(player);
	public pointcut hasShipPointcut1(Grid player): call(boolean battleship.Grid.hasShip(..)) && target(player);
	
	before(Grid player): hasShipPointcut1(player) {
		
	}
	boolean around(Player processedPlayer): hasShipPointcut(processedPlayer) {
		String playerId = processedPlayer.getId() + "_hits";
		boolean result = proceed(processedPlayer);
		System.out.println("HAS SHIP ");
		System.out.println(result);
		if(result) {
			IntegerObject playerHit = (IntegerObject) statisticManager.getVariable(playerId);
			if (playerHit == null) {
				playerHit = new IntegerObject(0);
				playerHit.increaseValue();
				statisticManager.addVariable(playerId, playerHit);
			} else {
				playerHit.increaseValue();
			}
		}
		return result;
	}
	
	
	before(Player processedPlayer, Player otherPlayer): playerMove(processedPlayer, otherPlayer) {
		System.out.println("HERE THIS METHOD IS CALLED");
		System.out.println(processedPlayer.getId());
		String playerId = processedPlayer.getId() + "_moves";
		System.out.println(thisJoinPoint);
		IntegerObject playerMove = (IntegerObject) statisticManager.getVariable(playerId);
		if (playerMove == null) {
			playerMove = new IntegerObject(0);
			playerMove.increaseValue();
			statisticManager.addVariable(playerId, playerMove);
		} else {
			playerMove.increaseValue();
		}
		IntegerObject playerMove1 = (IntegerObject) statisticManager.getVariable(playerId);
		System.out.println(playerMove1.getValue());
		//statisticManager.getVariable(")
	}
	
	after(Player processedPlayer, Player otherPlayer): playerMove(processedPlayer, otherPlayer) {
		System.out.println(thisJoinPoint);
		//statisticManager.getVariable(")
	}
}
