package derivator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import derivator.features.Features;
import derivator.features.SingleFeature;


public class ConfigFilePersistance {
	
	private JSONObject configurationObject;
	
	public ConfigFilePersistance(
			ConfigurationVariableManager configurationVariableManager, 
			String inputConfigPath, String outputConfigPath) {
		System.out.println("Loading config: ");
		this.loadJSONConfig(inputConfigPath);
		System.out.println("Applying configuration for it:");
		this.applyConfiguration(this.configurationObject, configurationVariableManager);
		System.out.println("Saving config to:" + outputConfigPath);
		this.writeJSONConfig(outputConfigPath, this.configurationObject);
	}
	
	private void applyConfiguration(JSONObject builtInConfig,
			ConfigurationVariableManager configurationVariableManager) {
		Iterator<Object> keys = builtInConfig.keySet().iterator();
		Object  processedObject;
		String key;
		
		while(keys.hasNext()) {
			key = (String) keys.next();
			processedObject = builtInConfig.get(key);
			if( processedObject instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) processedObject;
				Iterator<Object> array= jsonArray.iterator();
				while(array.hasNext()) {
					Object nextValue = array.next();
					if (nextValue instanceof JSONObject || nextValue instanceof JSONArray) {
						applyConfiguration((JSONObject) array.next(), configurationVariableManager);
					}
				}
			} else if (processedObject instanceof JSONObject) {			
				applyConfiguration((JSONObject) processedObject, configurationVariableManager);
			} else {
				Features feature = configurationVariableManager.getVariable(key);
				if (feature != null) {
					String variable =((SingleFeature) feature).convertToString();
					if(variable != null) {
						if(variable.equals("true") || variable.equals("false")) { 
							builtInConfig.put(key, (boolean) Boolean.parseBoolean(variable));
						} else if(variable.matches("-?\\d+")) {
							builtInConfig.put(key, (int) Integer.parseInt(variable));
						} else if(variable.matches("^([+-]?\\d*\\.?\\d*)$")) {
							builtInConfig.put(key, (float) Float.parseFloat(variable));
						} else {
							builtInConfig.put(key, variable);
						}
						System.out.println("Set " + key);
					}
				}
			}
		}
	}
	
	private JSONObject loadJSONConfig(String configPath) {
		try {
	        JSONParser parser = new JSONParser();
	        return  this.configurationObject = (JSONObject) parser.parse(new FileReader(configPath));
	    } catch (IOException | ParseException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	private void writeJSONConfig(String configPath, JSONObject configurationObject) {
		FileWriter fileWriter;
		File file= new File(configPath);

		try {
			file.createNewFile();
			fileWriter = new FileWriter(file);

			fileWriter.write(configurationObject.toJSONString()); 
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
