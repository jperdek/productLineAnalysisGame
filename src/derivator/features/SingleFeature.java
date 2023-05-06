package derivator.features;

public class SingleFeature implements Features{
	private String name;
	private String selectedValue = "";
	private boolean applied = false;
	
	public SingleFeature(String featureName, boolean applied) {
		this.applied = applied;
		this.name = featureName;
	}
	
	public SingleFeature(String featureName, String selectedFeatureName) {
		this.selectedValue = selectedFeatureName;
		this.name = featureName;
	}
	
	public boolean getApplicability() {
		return this.applied;
	}
	
	public String getName() {
		return this.name;
	}

	public String convertToString() {
		return this.name;
	}

	@Override
	public boolean compare(String instanceName, String inputValue) {
		if (inputValue.equals("true") || inputValue.equals("false")) {
			return this.applied == Boolean.parseBoolean(inputValue);
		}
		return this.selectedValue.equals(inputValue);
	}
}
