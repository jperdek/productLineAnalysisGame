package configurationManagement;

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
	        
	        setConfigurationForCleaning(configurationObject);
	        setGlobalSettings(configurationObject);
	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
	}
	
	private void setConfigurationForCleaning(JSONObject opponentObject) {
		JSONObject cleaningConfig = (JSONObject) opponentObject.get("cleaning");
		Configuration.emojiCleaner = (boolean) cleaningConfig.get("emojiCleaner");
		Configuration.lowerCasting = (boolean) cleaningConfig.get("lowerCasting");
		Configuration.punctuationCleaner = (boolean) cleaningConfig.get("punctuationCleaner");
	}
	
	private void setGlobalSettings(JSONObject configurationObject) {
		Configuration.dataEntity = (String) configurationObject.get("dataEntity");
		Configuration.dataConversion = (String) configurationObject.get("dataConversion");
		Configuration.standardQuotes = (boolean) configurationObject.get("standardQuotes");
		Configuration.dataFiltering = (boolean) configurationObject.get("dataFiltering");
	}
}
