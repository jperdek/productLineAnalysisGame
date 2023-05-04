package derivator;

import java.util.Map;
import derivator.features.Features;
import java.util.HashMap;


public class ConfigurationVariableManager {

private Map<String, Features> configVariables;
	
	public ConfigurationVariableManager() {
		configVariables = new HashMap<String, Features>();
	}
	
	public void addVariable(String variableName, Features value) {
		configVariables.put(variableName, value);
	}
	
	public Features getVariable(String variableName) {
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
	
	public void print() {
		Object[] keys = configVariables.keySet().toArray();
		String[] results = new String[keys.length];
		for(int i = 0; i < keys.length; i++) {
			System.out.println(keys[i]);
		}
	}
}
