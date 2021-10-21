package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public abstract class DerivationAnnotation {
	public abstract boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter,
			StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException;

	protected abstract boolean checkAnnotation(String stringToCheck);
	
	protected void parse(BufferedReader bufferedReader, BufferedWriter bufferedWriter, 
			StringBuilder stringBuilder) throws IOException, IncorrectAnnotationUsageException {
		int depth = 1;
		char rodeChar;
		parseToStartChar(bufferedReader, bufferedWriter, '{', stringBuilder);
		while(depth != 0 && bufferedReader.ready()) {
			rodeChar = (char) bufferedReader.read();
			if(rodeChar == '{') { depth++; };
			if(rodeChar == '}') { depth--; };
			bufferedWriter.write((int) rodeChar);
		}
	}
	
	protected void parseToStartChar(BufferedReader bufferedReader, BufferedWriter bufferedWriter, 
			char startChar, StringBuilder stringBuilder) throws IOException, IncorrectAnnotationUsageException {
		char rodeChar;
		while(bufferedReader.ready() && (rodeChar = (char) bufferedReader.read()) != startChar) {
			bufferedWriter.write((int) rodeChar);
			stringBuilder.append((char) rodeChar);
		}
		if(checkAnnotation(stringBuilder.toString()) == false) {
			throw new IncorrectAnnotationUsageException("This is incorrect: " + stringBuilder.toString());
		}
	}
}
