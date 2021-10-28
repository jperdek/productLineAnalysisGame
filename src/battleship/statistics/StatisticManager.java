package battleship.statistics;

import java.util.Map;
import java.util.HashMap;

//@{"statistics": "true"}
public class StatisticManager {
	private Map<String, VariableObject> variableAmount;
	
	public StatisticManager() {
		this.variableAmount = new HashMap<String, VariableObject>();
	}
	
	public void addVariable(String objectIdentifier, VariableObject variableObject) {
		this.variableAmount.put(objectIdentifier, variableObject);
	}
	
	public VariableObject getVariable(String objectIdentifier) {
		return this.variableAmount.get(objectIdentifier);
	}
	
}
