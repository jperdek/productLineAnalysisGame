package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DerivationAnnotationManager {

	private ClassAnnotation classAnnotation;
	private MethodAnnotation methodAnnotation;
	
	public DerivationAnnotationManager(DerivationVariableProcessor derivationVariableProcessor) {
		this.classAnnotation = new ClassAnnotation(derivationVariableProcessor);
		this.methodAnnotation = new MethodAnnotation(derivationVariableProcessor);
	}
	
	public boolean searchForAnnotation(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IncorrectAnnotationUsageException {
		boolean shouldRemove = true;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			char potentialAnnotationMark, startCommentChar, startCommentChar2;
			while(bufferedReader.ready()) {  
				 while(bufferedReader.ready() && (startCommentChar = (char) bufferedReader.read()) != '/') {
					 bufferedWriter.write((int) startCommentChar);
				 }
				if(bufferedReader.ready()) {
					startCommentChar2 = (char) bufferedReader.read();
					if(startCommentChar2 == '/') {
						stringBuilder.append('/'); stringBuilder.append('/');
						potentialAnnotationMark = skipWhiteSpace(bufferedReader, bufferedWriter);
						if(potentialAnnotationMark != '@' && potentialAnnotationMark != '#') {
							bufferedWriter.write((int) '/'); bufferedWriter.write((int) '/');
						}
						stringBuilder.append(potentialAnnotationMark);
						shouldRemove = this.chooseAndAnnotationMethod(bufferedReader, potentialAnnotationMark, 
								bufferedWriter, stringBuilder);
					} else {
						bufferedWriter.write((int) startCommentChar2);
					}
				}
			}
		} catch(ParseException pe) {
			pe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shouldRemove;
	}
	
	private boolean chooseAndAnnotationMethod(BufferedReader bufferedReader, char potentialAnnotationMark,
			BufferedWriter bufferedWriter, StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException {
		switch(potentialAnnotationMark) {
			case '@':
				return this.classAnnotation.process(bufferedReader, bufferedWriter, stringBuilder);
			case '#':
				return this.methodAnnotation.process(bufferedReader, bufferedWriter, stringBuilder);
			default:
				System.out.println("Unknown option: " + Character.toString(potentialAnnotationMark));
				break;
		}
		return true;
	}
	
	private char skipWhiteSpace(BufferedReader bufferedReader, 
			BufferedWriter bufferedWriter) throws IOException {
		char rodeChar = ' ';
		while(bufferedReader.ready() && Character.isWhitespace(rodeChar = (char) bufferedReader.read())) {
			bufferedWriter.write((int) rodeChar);
		}
		return rodeChar;
	}
}
