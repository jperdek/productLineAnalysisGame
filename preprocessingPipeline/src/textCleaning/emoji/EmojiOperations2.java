package textCleaning.emoji;
import com.sigpwned.emoji4j.core.GraphemeMatcher;
import textCleaning.TextCleaning;


//@{"emojiCleaner": "true"}
public class EmojiOperations2 implements TextCleaning {

	public EmojiOperations2(){
	}
	
	public String clean(String text){
		return text;
	}
	
	public static String remove(String text) 
	{
		return new GraphemeMatcher(text).replaceAll(mr -> "");
	}
	
	public static String change(String text)
	{
		return new GraphemeMatcher(text).replaceAll(mr -> mr.grapheme().getName());
	}
	
	public static void test() {
		String emojiText = "A 🐱, 🐱 and a 🐭 became friends❤️. For 🐶's birthday party, they all had 🍔s, 🍟s, 🍪s and 🍰.";
		String anotherExample  = ":):-),:-):-]:-xP=*:*<3:P:p,=-)";
		String noemoji, changed;
		
		noemoji =  new GraphemeMatcher(emojiText).replaceAll(mr -> "");
		changed = new GraphemeMatcher(emojiText).replaceAll(mr -> mr.grapheme().getName());
		System.out.println(noemoji);
		System.out.println(changed);
		
		noemoji = new GraphemeMatcher(anotherExample).replaceAll(mr -> "");
		changed = new GraphemeMatcher(anotherExample).replaceAll(mr -> mr.grapheme().getName());
		System.out.println(noemoji);
		System.out.println(changed);
	}
	
	public static void main(String args[]) {
		EmojiOperations2.test();
	}
}
