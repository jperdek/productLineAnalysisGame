package textCleaning.appliedParts;

import configurationManagement.Configuration;
import textCleaning.emoji.EmojiOperations;
import textCleaning.emoji.EmojiOperations2;


//@{"emojiCleaner": "true"}
public aspect EmojiCleaner {
	pointcut cleanEmojis1(String data): 
		call(* *.*processText(String)) && args(data) && !within(EmojiCleaner) && if(Configuration.emojiCleaner);
	
	pointcut cleanEmojis2(String data2): 
		call(* *.*processText(String)) && args(data2) && !within(EmojiCleaner) && if(Configuration.emojiCleaner);
	
	String around(String text): cleanEmojis1(text) {
		return proceed(EmojiOperations.remove(text));
	}
	
	String around(String text): cleanEmojis2(text) {
		return proceed(EmojiOperations2.remove(text));
	}
}

