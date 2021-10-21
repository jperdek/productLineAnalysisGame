package derivator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class MethodAnnotation extends DerivationAnnotation{
	
	private DerivationVariableProcessor derivationVariableProcessor;
	
	public MethodAnnotation(DerivationVariableProcessor derivationVariableProcessor) {
		this.derivationVariableProcessor = derivationVariableProcessor;
	}
	
	protected boolean checkAnnotation(String stringToCheck) {
		return !stringToCheck.contains("class");
	}
	
	public boolean process(BufferedReader bufferedReader, BufferedWriter bufferedWriter,
			StringBuilder stringBuilder) throws ParseException, IOException, IncorrectAnnotationUsageException {
		String descriptionJSON = (String) bufferedReader.readLine();
		boolean shouldParse = this.derivationVariableProcessor.shouldProcessDerivationVariablesANDRecursive(
				descriptionJSON);
		if(shouldParse) {
			this.parse(bufferedReader, bufferedWriter, stringBuilder);
		}
		return !shouldParse;
	}
}
