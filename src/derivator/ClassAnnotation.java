package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import derivator.features.IncorrectFeaturesEntryUsageException;

public class ClassAnnotation extends DerivationAnnotation {

	private DerivationVariableProcessor derivationVariableProcessor;
	
	public ClassAnnotation(DerivationVariableProcessor derivationVariableProcessor) {
		this.derivationVariableProcessor = derivationVariableProcessor;
	}
	
	protected boolean checkAnnotation(String stringToCheck) {
		return stringToCheck.contains("class") || stringToCheck.contains("aspect") || stringToCheck.contains("interface");
	}
	
	public boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter, 
			StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException {
		String descriptionJSON = (String) bufferedReader.readLine();

		boolean shouldParse = this.derivationVariableProcessor.shouldProcessDerivationVariablesANDRecursive(
				descriptionJSON);
		if(shouldParse) {
			this.parse(bufferedReader, bufferedWriter, stringBuilder);
		}
		return !shouldParse;
	}

	@Override
	public boolean process(BufferedReader bufferedReader, StringBuilder stringBuilder, StringBuilder content)
			throws ParseException, IOException, IncorrectAnnotationUsageException {
		// NOT IMPLEMENTED
		return false;
	}
}
