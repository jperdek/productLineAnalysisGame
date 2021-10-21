package derivator;

import java.util.Map;
import java.util.HashMap;

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
}
