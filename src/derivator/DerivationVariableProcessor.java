package derivator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DerivationVariableProcessor {

	ConfigurationVariableManager configurationVariableManager;
	
	public DerivationVariableProcessor(ConfigurationVariableManager configurationVariableManager) {
		this.configurationVariableManager = configurationVariableManager;
	}
	
	public boolean shouldProcessDerivationVariablesAND(String derivationVariablesJSONLine) throws ParseException {
		JSONParser parser = new JSONParser();
        JSONObject configurationObject = (JSONObject) parser.parse(derivationVariablesJSONLine);
        return shouldProcessDerivationVariablesAND(configurationObject);
	}
	
	public boolean shouldProcessDerivationVariablesAND(JSONObject configurationObject) throws ParseException {
        String[] keys = (String[]) configurationObject.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
        	String featureValue = this.configurationVariableManager.getVariable(keys[i]);
        	String configValue = (String) configurationObject.get(keys[i]);
        	
        	if(featureValue != configValue) {
        		return false;
        	}
        }
        return true;
	}
	
	public boolean shouldProcessDerivationVariablesOR(JSONObject configurationObject) throws ParseException {
        String[] keys = (String[]) configurationObject.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
        	String featureValue = this.configurationVariableManager.getVariable(keys[i]);
        	String configValue = (String) configurationObject.get(keys[i]);
        	
        	if(featureValue == configValue) {
        		return true;
        	}
        }
        return false;
	}
	
	public boolean shouldProcessDerivationVariablesANDRecursive(String derivationVariablesJSONLine) throws ParseException {
		JSONParser parser = new JSONParser();
        JSONObject configurationObject = (JSONObject) parser.parse(derivationVariablesJSONLine);
        return shouldProcessDerivationVariablesANDRecursive(configurationObject);
	}
	
	public boolean shouldProcessDerivationVariablesANDRecursive(JSONObject configurationObject) throws ParseException {
        Object[] keys =  configurationObject.keySet().toArray();
        //System.out.println((String) keys[0]);
        System.out.println(keys.length);
        for(int i=0; i<keys.length; i++) {
        	String configValue = (String) configurationObject.get(keys[i]);
        	if(keys[i] != "AND" && keys[i] != "OR") {
        		String featureValue = this.configurationVariableManager.getVariable((String) keys[i]);
        	
        		// if results not matches - whole part of AND will be false
        		if(featureValue != configValue) {
        			return false;
        		}	
        	}
        	if(keys[i] == "AND") {
        		// if result is false - whole part of AND will be false
        		if(shouldProcessDerivationVariablesANDRecursive(configurationObject) == false) {
        			return false;
        		}
        	}
        	
        	if(keys[i] == "OR") {
        		// if result is false - whole part of AND will be false
        		if(shouldProcessDerivationVariablesORRecursive(configurationObject) == false) {
        			return false;
        		}
        	}	
        }
        System.out.println("HEre");
        // all results matched
        return true;
	}
	
	public boolean shouldProcessDerivationVariablesORRecursive(JSONObject configurationObject) throws ParseException {
        String[] keys = (String[]) configurationObject.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
        	
        	if(keys[i] != "AND" && keys[i] != "OR") {
        		String featureValue = this.configurationVariableManager.getVariable(keys[i]);
        		String configValue = (String) configurationObject.get(keys[i]);
        		
        		// if results matches - whole part of OR will be true
        		if(featureValue == configValue) {
            		return true;
            	}
        	}
        	if(keys[i] == "AND") {
        		// if result is true - whole part of OR will be true
        		if(shouldProcessDerivationVariablesANDRecursive(configurationObject)) {
        			return true;
        		}
        	}
        	
        	if(keys[i] == "OR") {
        		// if result is true - whole part of OR will be true
        		if(shouldProcessDerivationVariablesORRecursive(configurationObject)) {
        			return true;
        		}
        	}
        }
        // all results not matched
        return false;
	}
}
