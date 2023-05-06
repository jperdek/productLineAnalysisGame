package derivator.features;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ManyOfManyFeature implements Features{
	private boolean restrictedMode = false;
	private Map<String, InstanceType> instances;
	private Set<String> allValidValues;
	private Set<String> chosenValidValues;
	
	public ManyOfManyFeature() {
		this.instances = new HashMap<String, InstanceType>();
		this.allValidValues = new HashSet<String>();
		this.chosenValidValues = new HashSet<String>();
	}
	
	public ManyOfManyFeature(Map<String, InstanceType> instances) {
		this.instances = instances;
		this.allValidValues = this.collectAllValidValues(instances); 
		this.chosenValidValues = this.collectAllChosenValues(instances); 
	}
	
	public Set<String> collectAllValidValues(Map<String, InstanceType> instances) {
		Set<String> allValidValues = new HashSet<String>();
		Iterator<InstanceType> instanceTypesIterator = instances.values().iterator();
		while(instanceTypesIterator.hasNext()) {
			InstanceType processedInstanceType = instanceTypesIterator.next();
			allValidValues.addAll(processedInstanceType.getAllPosibleTypes());
		}
		return allValidValues;
	}
	
	public Set<String> collectAllChosenValues(Map<String, InstanceType> instances) {
		Set<String> allValidValues = new HashSet<String>();
		Iterator<InstanceType> instanceTypesIterator = instances.values().iterator();
		while(instanceTypesIterator.hasNext()) {
			InstanceType processedInstanceType = instanceTypesIterator.next();
			allValidValues.addAll(processedInstanceType.getAllActualTypes());
		}
		return allValidValues;
	}
	
	public void addInstanceType(InstanceType instanceType) {
		String instanceName= instanceType.getName();
		this.instances.put(instanceName, instanceType);
	}

	@Override
	public boolean compare(String instanceName, String inputValue) throws IncorrectFeaturesEntryUsageException {
		inputValue = inputValue.strip();
		boolean negate = false;
		if (inputValue.toUpperCase().startsWith("NOT")) {
			negate = true;
			inputValue = inputValue.substring(3).strip();
		}

		if (!inputValue.startsWith("[") && !inputValue.endsWith("]")) {
			throw new IncorrectFeaturesEntryUsageException("Many of Many Feature should have supported values in []. One from [ or ] is missing.");
		}
		
		if (!instanceName.equals("all")) {
			System.out.println("Checking for instance type unimplemented!");
		}

		inputValue = inputValue.substring(1, inputValue.length() - 1);
		String[] inputValues = inputValue.split(",");
		boolean found = false;
		for (String partialValue: inputValues) {
			partialValue = partialValue.strip();
			if (partialValue.startsWith("'") || partialValue.startsWith("\"")) {
				partialValue = partialValue.substring(1);
			}
			if (partialValue.endsWith("'") || partialValue.endsWith("\"")) {
				partialValue = partialValue.substring(0, partialValue.length() - 1);
			}

			// TO CHECK SUPPORT BY ALL POSSIBLE FEATURES/VARIABLES
			if(!this.allValidValues.contains(partialValue)) {
				throw new IncorrectFeaturesEntryUsageException("Not supported value: " + partialValue + 
						"  for many of many variability called: " + inputValue);
			}
			
			// TO CHECK SUPPORT BY ALL CHOSEN FEATURES/VARIABLES
			if(this.chosenValidValues.contains(partialValue)) {
				// here expression can be parsed further to allow more advanced configurations
				found = true;
			}
		}
		
		if (negate) { found = !found; }
		return found;
	}
}
