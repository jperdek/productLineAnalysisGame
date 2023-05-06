package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import derivator.features.IncorrectFeaturesEntryUsageException;

public class ImportAnnotation extends DerivationAnnotation  {
	private DerivationVariableProcessor derivationVariableProcessor;
	
	public ImportAnnotation(DerivationVariableProcessor derivationVariableProcessor) {
		this.derivationVariableProcessor = derivationVariableProcessor;
	}
	
	protected boolean checkAnnotation(String stringToCheck) {
		return stringToCheck.contains("import");
	}
	
	public boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter, 
			StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException, IncorrectFeaturesEntryUsageException {
		String descriptionJSON = (String) bufferedReader.readLine();

		boolean shouldParse = this.derivationVariableProcessor.shouldProcessDerivationVariablesANDRecursive(
				descriptionJSON);
		
		String importLine = (String) bufferedReader.readLine();
		if(shouldParse) {
			bufferedWriter.write(importLine);
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
