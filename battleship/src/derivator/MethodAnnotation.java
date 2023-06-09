package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import derivator.features.IncorrectFeaturesEntryUsageException;

public class MethodAnnotation extends DerivationAnnotation{
	
	private DerivationVariableProcessor derivationVariableProcessor;
	
	public MethodAnnotation(DerivationVariableProcessor derivationVariableProcessor) {
		this.derivationVariableProcessor = derivationVariableProcessor;
	}
	
	protected boolean checkAnnotation(String stringToCheck) {
		return true;
	}
	
	public boolean process(BufferedReader bufferedReader, 
			StringBuilder stringBuilder,  StringBuilder content) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException {
		String descriptionJSON = (String) bufferedReader.readLine();

		boolean shouldParse = this.derivationVariableProcessor.shouldProcessDerivationVariablesANDRecursive(
				descriptionJSON);
		// loads content all the time - if neccessary in future it will be dropped
		this.parse(bufferedReader, stringBuilder, content);
		return !shouldParse;
	}

	@Override
	public boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter, StringBuilder stringBuilder)
			throws ParseException, IOException, IncorrectAnnotationUsageException {
		// NOT IMPLEMENTED
		return false;
	}
 
}
