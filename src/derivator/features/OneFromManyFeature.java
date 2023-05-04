package derivator.features;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class OneFromManyFeature implements Features {
	private Map<String, InstanceType> instances;
	private Set<String> allValidValues;
	
	public OneFromManyFeature() {
		this.instances = new HashMap<String, InstanceType>();
		this.allValidValues = new HashSet<String>();
	}
	
	public OneFromManyFeature(Map<String, InstanceType> instances) {
		this.instances = instances;
		this.allValidValues = this.collectAllValidValues(instances); 
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
	
	public void addInstanceType(InstanceType instanceType) {
		String instanceName= instanceType.getName();
		this.instances.put(instanceName, instanceType);
	}
	
	public void addActualInstanceValue(String instanceName, String actualValue) {
		InstanceType instanceType = this.instances.get(instanceName);
		instanceType.addActualType(actualValue);
	}
	
	public void addPossibleInstanceValue(String instanceName, String possibleValue) {
		InstanceType instanceType = this.instances.get(instanceName);
		instanceType.addPossibleType(possibleValue);
	}

	@Override
	public boolean compare(String instanceName, String inputValue) throws IncorrectFeaturesEntryUsageException {
		inputValue = inputValue.strip();
		if (!inputValue.startsWith("[") && !inputValue.endsWith("]")) {
			throw new IncorrectFeaturesEntryUsageException("One From Many Feature should have one supported value in []. One from [ or ] is missing.");
		}
		inputValue = inputValue.substring(1, inputValue.length() - 1);
		String[] inputValues = inputValue.split(",");
		if (inputValues.length > 1) {
			throw new IncorrectFeaturesEntryUsageException("Only one feature value should be selected!");
		}
		
		if (!instanceName.equals("all")) {
			System.out.println("Checking for instance type unimplemented!");
		}
		for (String partialValue: inputValues) {
			if(!this.allValidValues.contains(partialValue.strip())) {
				return false;
			}
		}
		return true;
	}
}
