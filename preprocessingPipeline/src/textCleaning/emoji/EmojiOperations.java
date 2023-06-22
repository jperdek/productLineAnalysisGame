package textCleaning.emoji;
import emoji4j.EmojiUtils;
import textCleaning.TextCleaning;


//@{"emojiCleaner": "true"}
public class EmojiOperations implements TextCleaning {

	public EmojiOperations(){
	}
	
	public String clean(String text){
		return EmojiUtils.removeAllEmojis(text);
	}
	
	public static String remove(String text) 
	{
		return EmojiUtils.removeAllEmojis(text);
	}
	
	public static String change(String text)
	{
		return EmojiUtils.shortCodify(text);
	}
	
	
	public static void test() {
		String emojiText = "A 🐱, 🐱 and a 🐭 became friends❤️. For 🐶's birthday party, they all had 🍔s, 🍟s, 🍪s and 🍰.";
		String anotherExample  = ":):-),:-):-]:-xP=*:*<3:P:p,=-)";
		String noemoji, changed;
		
		noemoji = EmojiUtils.removeAllEmojis(emojiText);
		changed = EmojiUtils.shortCodify(emojiText);
		System.out.println(noemoji);
		System.out.println(changed);
		
		noemoji = EmojiUtils.removeAllEmojis(anotherExample);
		System.out.println(noemoji);
	}
	
	public static void main(String args[]) {
		EmojiOperations.test();
	}
}
