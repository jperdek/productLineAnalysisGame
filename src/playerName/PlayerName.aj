package playerName;
import java.util.Scanner;

import battleship.InputReader;
import battleship.Player;

public aspect PlayerName {
	private String Player.name;
	
	private void Player.setName(String playerName) {
		this.name = playerName;
	}
	
	private String Player.getName() {
		return this.name;
	}
	
	Player around(): call(Player.new(..)){
		Scanner reader = InputReader.getReader();
		System.out.println("Set player name:");
		String playerNameLine = reader.nextLine().replace("\n", "");
		
		Player createdPlayer = proceed();
		createdPlayer.setName(playerNameLine);
		
		System.out.println(createdPlayer.getName());
		
		return createdPlayer;
	}
	
}
