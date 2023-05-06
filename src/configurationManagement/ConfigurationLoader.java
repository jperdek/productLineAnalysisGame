package configurationManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;


//@{}
public class ConfigurationLoader {
	
	public ConfigurationLoader(String configPath) {
		System.out.println("Loading config...");
		loadJSONConfig(configPath);
		System.out.println("Config loaded!");
	}
	
	private void loadJSONConfig(String configPath) {
		try {
	        JSONParser parser = new JSONParser();
	        JSONObject configurationObject = (JSONObject) parser.parse(new FileReader(configPath));
	        
	        loadAndSetDifficulty(configurationObject);
	        setGlobalSettings(configurationObject);
	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
	}

	private void loadAndSetDifficulty(JSONObject configurationObject) {
		String chosenDifficultyName = (String) configurationObject.get("difficulty");
		Configuration.difficulty = chosenDifficultyName;
		
		JSONObject difficulties =  (JSONObject) configurationObject.get("difficultyLevels");
		JSONObject chosenDifficulty = (JSONObject) difficulties.get(chosenDifficultyName);
		
		setConfigurationForPlayer((JSONObject) chosenDifficulty.get("player"));
		setConfigurationForOpponent((JSONObject) chosenDifficulty.get("opponent"));
	}
	
	private void setConfigurationForPlayer(JSONObject playerObject) {
		JSONObject areaSize = (JSONObject) playerObject.get("areaSize");
		Configuration.playerWidth = ((Long) areaSize.get("width")).intValue();
		Configuration.playerHeight = ((Long) areaSize.get("height")).intValue();

		Object[] array = ((JSONArray) playerObject.get("playerShips")).toArray();
		int[] playerShips = new int[array.length];
		for(int i=0; i< array.length; i++) {
			playerShips[i] = ((Long) array[i]).intValue();
		}
		Configuration.playerShips = playerShips;
	}
	
	private void setConfigurationForOpponent(JSONObject opponentObject) {
		JSONObject areaSize = (JSONObject) opponentObject.get("areaSize");
		Configuration.opponentWidth = ((Long)areaSize.get("width")).intValue();
		Configuration.opponentHeight = ((Long) areaSize.get("height")).intValue();
		
		Object[] array = ((JSONArray) opponentObject.get("playerShips")).toArray();
		int[] opponentShips = new int[array.length];
		for(int i=0; i< array.length; i++) {
			opponentShips[i] = ((Long) array[i]).intValue();
		}
		Configuration.opponentShips = opponentShips;
	}
	
	private void setGlobalSettings(JSONObject configurationObject) {
		Configuration.playerNames = (boolean) configurationObject.get("playerNames");
		Configuration.computerOpponent = (boolean) configurationObject.get("computerOpponent");
		Configuration.statistics = (boolean) configurationObject.get("statistics");
		Configuration.challenge = (boolean) configurationObject.get("challenge");
	}
}
