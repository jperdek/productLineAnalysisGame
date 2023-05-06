package derivator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import derivator.features.IncorrectFeaturesEntryUsageException;
import derivator.features.Features;

public class DerivationVariableProcessor {
	
	private ConfigurationVariableManager configurationVariableManager;
	private String applicationType = "all";
	
	public DerivationVariableProcessor(ConfigurationVariableManager configurationVariableManager) {
		this("all", configurationVariableManager);
	}
	
	public DerivationVariableProcessor(String applicationType, ConfigurationVariableManager configurationVariableManager) {
		this.applicationType = applicationType;
		this.configurationVariableManager = configurationVariableManager;
	}
	
	public boolean shouldProcessDerivationVariablesAND(String derivationVariablesJSONLine) throws ParseException, IncorrectFeaturesEntryUsageException {
		JSONParser parser = new JSONParser();
        JSONObject configurationObject = (JSONObject) parser.parse(derivationVariablesJSONLine);
        return shouldProcessDerivationVariablesAND(configurationObject);
	}
	
	public boolean shouldProcessDerivationVariablesAND(JSONObject configurationObject) throws ParseException, IncorrectFeaturesEntryUsageException {
        String[] keys = (String[]) configurationObject.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
        	Features featureValue = this.configurationVariableManager.getVariable(keys[i]);
        	String configValue = (String) configurationObject.get(keys[i]);
        	
        	if(!featureValue.compare(this.applicationType, configValue)) {
        		return false;
        	}
        }
        return true;
	}
	
	public boolean shouldProcessDerivationVariablesOR(JSONObject configurationObject) throws ParseException, IncorrectFeaturesEntryUsageException {
        String[] keys = (String[]) configurationObject.keySet().toArray();
        for(int i=0; i<keys.length; i++) {
        	Features featureValue = this.configurationVariableManager.getVariable(keys[i]);
        	String configValue = (String) configurationObject.get(keys[i]);
        	
        	if(featureValue.compare(this.applicationType, configValue)) {
        		return true;
        	}
        }
        return false;
	}
	
	public boolean shouldProcessDerivationVariablesANDRecursive(String derivationVariablesJSONLine) throws ParseException, IncorrectFeaturesEntryUsageException {
		JSONParser parser = new JSONParser();
        JSONObject configurationObject = (JSONObject) parser.parse(derivationVariablesJSONLine);
        return shouldProcessDerivationVariablesANDRecursive(configurationObject);
	}
	
	public boolean shouldProcessDerivationVariablesANDRecursive(JSONObject configurationObject) throws ParseException, IncorrectFeaturesEntryUsageException {
		String configValue;
		String key_i;
		Object[] keys =  configurationObject.keySet().toArray();
   
        for(int i=0; i<keys.length; i++) {
        	key_i = (String) keys[i];
        	if(!key_i.equals("AND") && !key_i.equals("OR")) {
        		configValue = (String) configurationObject.get(keys[i]);
        		Features featureValue = this.configurationVariableManager.getVariable((String) keys[i]);
        	
        		//this.configurationVariableManager.print();
        		//System.out.println(keys[i]);
        		if(!featureValue.compare(key_i, configValue)) {
        			return false;
        		}	
        	}
        	if(key_i.equals("AND")) {
        		// if result is false - whole part of AND will be false
        		if(shouldProcessDerivationVariablesANDRecursive((JSONObject) configurationObject.get(keys[i])) == false) {
        			return false;
        		}
        	}
        	
        	if(key_i.equals("OR")) {
        		// if result is false - whole part of AND will be false
        		if(shouldProcessDerivationVariablesORRecursive((JSONObject) configurationObject.get(keys[i])) == false) {
        			return false;
        		}
        	}	
        }
        // all results matched
        return true;
	}
	
	public boolean shouldProcessDerivationVariablesORRecursive(JSONObject configurationObject) throws ParseException, IncorrectFeaturesEntryUsageException {
		Object[] keys =  configurationObject.keySet().toArray();
        String configValue;
		String key_i;
		
        for(int i=0; i<keys.length; i++) {
        	key_i = (String) keys[i];
        	if(!key_i.equals("AND") && !key_i.equals("OR")) {
        		
        		configValue = (String) configurationObject.get(keys[i]);
        		Features featureValue = this.configurationVariableManager.getVariable((String) keys[i]);
        	
        		//this.configurationVariableManager.print();
        		//System.out.println(keys[i]);
        		if(featureValue.compare(key_i, configValue)) {
        			return true;
        		}
        	}
        	if(key_i.equals("AND")) {
        		// if result is true - whole part of OR will be true
        		if(shouldProcessDerivationVariablesANDRecursive((JSONObject) configurationObject.get(keys[i]))) {
        			return true;
        		}
        	}
        	
        	if(key_i.equals("OR")) {
        		// if result is true - whole part of OR will be true
        		if(shouldProcessDerivationVariablesORRecursive((JSONObject) configurationObject.get(keys[i]))) {
        			return true;
        		}
        	}
        }
        // all results not matched
        return false;
	}
}
