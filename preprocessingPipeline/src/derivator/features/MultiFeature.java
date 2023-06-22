package derivator.features;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class MultiFeature{
	private Map<String, Features> features;
	private Map<String, MultiFeature> multiFeatures;
	private String name;
	
	public MultiFeature(String multiFeatureName) {
		this.features = new HashMap<String, Features>();
		this.multiFeatures = new HashMap<String, MultiFeature>();
		this.name = multiFeatureName;
	}
	
	public String getFeatureName() {
		return this.name;
	}

	public void addFeature(String featureName, Features feature) {
		this.features.put(featureName, feature);
	}
	
	public Features getFeature(String featureName) {
		return this.features.get(featureName);
	}
	
	public Set<Entry<String, Features>> getAllFeatures() {
		return this.features.entrySet();
	}
	
	public void addMultiFeature(String featureName, MultiFeature feature) {
		this.multiFeatures.put(featureName, feature);
	}
	
	public MultiFeature getMultiFeature(String featureName) {
		return this.multiFeatures.get(featureName);
	}
	
	public Set<Entry<String, MultiFeature>> getAllMultiFeatures() {
		return this.multiFeatures.entrySet();
	}
}
