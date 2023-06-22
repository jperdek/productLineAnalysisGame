package derivator.features;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class InstanceType {
	private List<String> possibleTypes;
	private List<String> actualTypes;
	private String name;
	private boolean multipleSelection = false;

	public InstanceType(String name, boolean multipleSelection) {
		this.possibleTypes = new ArrayList<String>();
		this.actualTypes = new ArrayList<String>();
		this.name = name;
		this.multipleSelection = multipleSelection;
	}
	
	public void changeToOneOption() {
		this.multipleSelection = false;
	}

	public void addPossibleType(String possibleType) {
		this.possibleTypes.add(possibleType);
	}
	
	public void addActualType(String actualType) {
		if (this.multipleSelection && this.actualTypes.size() > 1) {
			System.out.println("Cannot add new type. Only one is allowed!");
			return;
		}
		this.actualTypes.add(actualType);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Set<String> getAllPosibleTypes() {
		return new HashSet<String>(this.possibleTypes);
	}
	
	public Set<String> getAllActualTypes() {
		return new HashSet<String>(this.actualTypes);
	}
}
