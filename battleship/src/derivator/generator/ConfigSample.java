package derivator.generator;

import java.util.List;
import java.util.ArrayList;


public class ConfigSample {
	private List<Object> samples;
	
	public ConfigSample() {
		this.samples = new ArrayList<Object>();
	}
	
	public ConfigSample(List<Object> sample) {
		this.samples = new ArrayList<Object>(sample);
	}
	
	public List<Object> getSamples() {
		return this.samples;
	}
	
	public Object getSample(int index) {
		return this.samples.get(index);
	}
	
	public void appendSampleValue(Object value) {
		this.samples.add(value);
	}
}
