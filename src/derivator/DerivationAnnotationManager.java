package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import derivator.features.IncorrectFeaturesEntryUsageException;

public class DerivationAnnotationManager {

	private ClassAnnotation classAnnotation;
	private MethodAnnotation methodAnnotation;
	private ImportAnnotation importAnnotation;
	
	public DerivationAnnotationManager(DerivationVariableProcessor derivationVariableProcessor) {
		this.classAnnotation = new ClassAnnotation(derivationVariableProcessor);
		this.methodAnnotation = new MethodAnnotation(derivationVariableProcessor);
		this.importAnnotation = new ImportAnnotation(derivationVariableProcessor);
	}
	
	public boolean searchForAnnotation(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException {
		boolean shouldRemove = true;
		boolean resultRemove = true;
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
						if(potentialAnnotationMark != '@' && potentialAnnotationMark != '#' && potentialAnnotationMark != '%') {
							bufferedWriter.write((int) '/'); bufferedWriter.write((int) '/');
						}
						stringBuilder.append(potentialAnnotationMark);
						resultRemove = this.chooseAndAnnotationMethod(bufferedReader, potentialAnnotationMark, 
								bufferedWriter, stringBuilder);
						if(resultRemove == false) {
							shouldRemove = false;
						}
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
			BufferedWriter bufferedWriter, StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException {
		boolean result;
		switch(potentialAnnotationMark) {
			case '@':
				return this.classAnnotation.process(bufferedReader, bufferedWriter, stringBuilder);
			case '#':
				 StringBuilder content = new StringBuilder();
				 result = this.methodAnnotation.process(bufferedReader, stringBuilder, content);
 
				 if(!result) { //if content should be written - shouldParse negated in previous method
					 bufferedWriter.append(content.toString());
				 }
				 return result;
			case '%':
				return this.importAnnotation.process(bufferedReader, bufferedWriter, stringBuilder);
			default:
				// none annotation has been observed
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
