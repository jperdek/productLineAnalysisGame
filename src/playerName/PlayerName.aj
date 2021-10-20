package playerName;
import java.util.Scanner;

import battleship.AbstractPlayer;
import battleship.ComputerPlayer;
import battleship.InputReader;
import battleship.Player;
import configurationManagement.Configuration;

public aspect PlayerName {
	private String AbstractPlayer.name;
	
	private void AbstractPlayer.setName(String playerName) {
		this.name = playerName;
	}
	
	private String AbstractPlayer.getName() {
		return this.name;
	}
	
	Player around(): call(Player.new(..)) && if(Configuration.setNames){
		Scanner reader = InputReader.getReader();
		System.out.println("Set player name:");
		String playerNameLine = reader.nextLine().replace("\n", "");
		
		Player createdPlayer = proceed();
		createdPlayer.setName(playerNameLine);
		
		System.out.println(createdPlayer.getName());
		
		return createdPlayer;
	}

	ComputerPlayer around(): call(ComputerPlayer.new(..)) && if(Configuration.setNames){
		Scanner reader = InputReader.getReader();
		System.out.println("Set computer name:");
		String playerNameLine = reader.nextLine().replace("\n", "");
		
		ComputerPlayer createdComputerPlayer = proceed();
		createdComputerPlayer.setName(playerNameLine);
		
		System.out.println(createdComputerPlayer.getName());
		
		return createdComputerPlayer;
	}
}
