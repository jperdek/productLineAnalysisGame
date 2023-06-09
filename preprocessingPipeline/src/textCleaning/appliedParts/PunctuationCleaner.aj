package textCleaning.appliedParts;
import textCleaning.TextCleaning;
import configurationManagement.Configuration;


//@{"punctuationCleaner": "true"}
public aspect PunctuationCleaner implements TextCleaning {
	
	int mode = 1;
	
	pointcut cleanPunctuation(String data): 
			call(* *.*processText(String)) && args(data)  && !within(PunctuationCleaner) && if(Configuration.punctuationCleaner);
		
	String around(String text): cleanPunctuation(text) {
		String text2 = PunctuationCleaner.removePunctuationByRegexp(text);
		return proceed(text2);
	}
	
	public static String removePunctuationByRegexp(String text) {
		return text.replaceAll("[-,.!'~#@*+%{}<>\\[\\]|\"_^& ?]+", " ");
	}
	
	public String clean(String text){
		//return text.replaceAll("[^\\w+]", " ");
		if(this.mode == 0) {
			return text.replaceAll("[-1234567890,.!'~#@*+%{}<>\\[\\]|\"_^& ?]+", " ");
		}
		return text.replaceAll("[-,.!'~#@*+%{}<>\\[\\]|\"_^& ?]+", " ");
	}
	
	public void test() {
		this.mode = 0;
		String text = "Ajksd% Ghjskd& 1233456789cisla ^[a]+-! +6 -9 ako? stoj! ~\" \" | ff KO. email@email.com";
		System.out.println(clean(text));
	}
}
