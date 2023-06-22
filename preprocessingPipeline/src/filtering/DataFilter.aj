package filtering;

import configurationManagement.Configuration;


//@{"dataFiltering": "true"}
public aspect DataFilter {
	pointcut filterAccordingBooleanValue(boolean value): 
		call(* *.*filterBoolean(boolean)) && args(value) && !within(DataFilter) && if(Configuration.dataFiltering);
	
	boolean around(boolean value): filterAccordingBooleanValue(value) {
		if (!value) {
			return true;
		} else {
			return false;
		}
	}
}
