package textCleaning.appliedParts;

import configurationManagement.Configuration;


//@{"lowerCasting": "true"}
public aspect LowerCastingCleaner {
	
	int option = 0;
	
	public String clean(String text) {
		if(option==0) {
			return text.toLowerCase();
		}
		return text.toUpperCase();
	}

	public static String lowerCast(String text) {
		return text.toLowerCase();
	}
	
	pointcut lowerCastPointcut(String data): 
		call(String *.*processText(String)) && args(data)  && !within(LowerCastingCleaner) && if(Configuration.lowerCasting);
	
	String around(String text): lowerCastPointcut(text) {
		return proceed(LowerCastingCleaner.lowerCast(text));
	}
}
