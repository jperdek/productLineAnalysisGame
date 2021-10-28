package derivator;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationVariableManager {

	private Map<String, String> configVariables;
	
	public ConfigurationVariableManager() {
		configVariables = new HashMap<String, String>();
	}
	
	public void addVariable(String variableName, String value) {
		configVariables.put(variableName, value);
	}
	
	public String getVariable(String variableName) {
		return configVariables.get(variableName);
	}
	
	public String[] getAllVariableNames() {
		Object[] keys = configVariables.keySet().toArray();
		String[] results = new String[keys.length];
		for(int i = 0; i < keys.length; i++) {
			results[i] = (String) keys[i];
		}
		return results;
	}
}
