package derivator.generator;

public class GSample {

	private int numberCases = 2;
	private String variableName;
	private String type = "boolean";
	private String[] cases = null;
	
	public GSample(String variableName) {
		this(variableName, "boolean" , 2);
	}
	
	public GSample(String variableName, String type, int numberCases) {
		this.variableName = variableName;
		this.type = type;
		this.numberCases = numberCases;
	}
	
	public GSample(String variableName, String type, int numberCases, String[] cases) {
		this.variableName = variableName;
		this.type = type;
		this.numberCases = numberCases;
		this.cases = cases;
	}
	
	public void includeCases(String[] cases) { this.cases = cases; }
	
	public String getVariableName() { return this.variableName; }
	
	public int getNumberCases() { return this.numberCases; }
	
	public String getCase(int caseNumber) { return this.cases[caseNumber]; }
	
	public String getType() { return this.type; }
	
	public String[] getCases() { return this.cases; }
}
