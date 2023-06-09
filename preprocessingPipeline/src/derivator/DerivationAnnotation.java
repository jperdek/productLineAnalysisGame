package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import derivator.features.IncorrectFeaturesEntryUsageException;

public abstract class DerivationAnnotation {
	public abstract boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter,
			StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException;

	public abstract boolean process(BufferedReader bufferedReader, StringBuilder stringBuilder, 
			StringBuilder content) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException;
	
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
		char rodeChar = '{';
		while(bufferedReader.ready() && (rodeChar = (char) bufferedReader.read()) != startChar) {
			bufferedWriter.write((int) rodeChar);
			stringBuilder.append((char) rodeChar);
		}
		if(bufferedReader.ready()) {
			bufferedWriter.write((int) rodeChar);
		}
	
		if(checkAnnotation(stringBuilder.toString()) == false) {
			throw new IncorrectAnnotationUsageException("This is incorrect: " + stringBuilder.toString());
		}
	}
	
	protected void parse(BufferedReader bufferedReader, StringBuilder stringBuilder,
			StringBuilder content) throws IOException, IncorrectAnnotationUsageException {
		int depth = 1;
		char rodeChar;
		parseToStartChar(bufferedReader, '{', stringBuilder, content);

		while(depth != 0 && bufferedReader.ready()) {
			rodeChar = (char) bufferedReader.read();
			if(rodeChar == '{') { depth++; };
			if(rodeChar == '}') { depth--; };
			content.append(rodeChar);
		}
	}
	
	protected void parseToStartChar(BufferedReader bufferedReader, char startChar,
			StringBuilder stringBuilder, StringBuilder content) throws IOException, IncorrectAnnotationUsageException {
		char rodeChar = '{';
		while(bufferedReader.ready() && (rodeChar = (char) bufferedReader.read()) != startChar) {
			content.append(rodeChar);
			stringBuilder.append(rodeChar);
		}
		if(bufferedReader.ready()) {
			content.append(rodeChar);
		}
	
		if(checkAnnotation(stringBuilder.toString()) == false) {
			throw new IncorrectAnnotationUsageException("This is incorrect: " + stringBuilder.toString());
		}
	}
}
