package derivator.generator;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import derivator.ConfigurationVariableManager;
import java.util.Iterator;

public class VariableBoundariesLoader {
	
	public static GSample[] loadVariableBoundaries(
			ConfigurationVariableManager configurationVariableManager, 
			String pathToBoundaries) {
		int j;
		try {
	        JSONParser parser = new JSONParser();
	        JSONObject variableBoundariesObject = (JSONObject) parser.parse(new FileReader(pathToBoundaries));
	        
	        String[] variableNames = configurationVariableManager.getAllVariableNames();
	        GSample[] gsamples = new GSample[variableNames.length];
	        for(int i = 0; i < variableNames.length; i++) {
	        	JSONObject variableObject = (JSONObject) variableBoundariesObject.get(variableNames[i]);
	       
	        	String type = (String) variableObject.get("type");
	        	int numberCases = ((Long) variableObject.get("numberCases")).intValue();
	        	if(!type.equals("String")) {
	        		gsamples[i] = new GSample(variableNames[i], type, numberCases);
	        	} else {
	        		JSONArray casesJSONArray = (JSONArray) variableObject.get("cases");
	        		String[] cases = new String[casesJSONArray.size()];
	        		
	        		Iterator<Object> caseJSON = casesJSONArray.iterator();
	        		j = 0;
	        		while(caseJSON.hasNext()) {
	        			cases[j] = (String) caseJSON.next();
	        			System.out.println(cases[j]);
	        			j++;
	        		}
	        		
	        		gsamples[i] = new GSample(variableNames[i], type, numberCases, cases);
	        	}
	        }
	        return  gsamples;
	    } catch (IOException | ParseException e) {
	    	System.out.print(e);
	        e.printStackTrace();
	    }
		return null;
	}
}
