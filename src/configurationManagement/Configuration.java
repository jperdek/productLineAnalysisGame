package configurationManagement;

import derivator.ConfigurationVariableManager;

public class Configuration {
	public static int playerWidth = 5;
	public static int playerHeight = 5;
	public static int[] playerShips = {2};
	
	public static int opponentWidth = 5;
	public static int opponentHeight = 5;
	public static int[] opponentShips = {2};
	
	public static boolean setNames = true;
	public static boolean computerOpponent = true;
	public static boolean collectStatistics = true;
	public static boolean challenge = false;
	
	public static String difficulty = "Easy";
	
	public static ConfigurationVariableManager createConfigurableVariableManager() {
		ConfigurationVariableManager configurationVariableManager = new ConfigurationVariableManager();
		
		configurationVariableManager.addVariable("setNames", Boolean.toString(setNames));
		configurationVariableManager.addVariable("computerOpponent", Boolean.toString(computerOpponent));
		configurationVariableManager.addVariable("collectStatistics", Boolean.toString(collectStatistics));
		configurationVariableManager.addVariable("challenge", Boolean.toString(challenge));
		configurationVariableManager.addVariable("difficulty", difficulty);
		
		return configurationVariableManager;
	}
}
