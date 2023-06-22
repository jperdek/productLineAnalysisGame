package inputConverters;

import configurationManagement.Configuration;
import java.util.List;


//@{"dataConversion": "python"}
public aspect PythonConverter {
	
	pointcut correctlyParsePythonBoolean(String data): 
		call(* *.*parseBoolean(String)) && args(data)  && !within(PythonConverter) && if(Configuration.dataConversion.equals("python"));
	
	pointcut correctlyParsePythonArray(String data): 
		call(* *.*parseArray(String)) && args(data)  && !within(PythonConverter)  && if(Configuration.dataConversion.equals("python"));
	
	boolean around(String booleanValue): correctlyParsePythonBoolean(booleanValue) {
		if (booleanValue.toUpperCase().equals("TRUE")) {
			return true;
		} else {
			return false;
		}
	}
	
//%{"AND": {"dataConversion": "python", "standardQuotes": "false"}}
	List<String> around(String tweetArray): correctlyParsePythonArray(tweetArray) {
		return proceed(tweetArray.replace("[", "").replace("]", "").replace("'", ""));
	}
	
//%{"AND": {"dataConversion": "python", "standardQuotes": "true"}}
	List<String> around(String tweetArray): correctlyParsePythonArray(tweetArray) {
		return proceed(tweetArray.replace("[", "").replace("]", "").replace("'", ""));
	}
}
