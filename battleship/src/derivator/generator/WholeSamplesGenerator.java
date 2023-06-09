package derivator.generator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class WholeSamplesGenerator {

	public WholeSamplesGenerator() {}
	
	public List<ConfigSample> generateSamples(GSample[] gsamples) {
		List<ConfigSample> resultSamples = new ArrayList<ConfigSample>();
		List<ConfigSample> configSamples = new ArrayList<ConfigSample>();
		for(int j = 0; j < gsamples.length; j++) {
			System.out.println("Generating for: " + gsamples[j].getVariableName() + " number: " + j);
			configSamples = new ArrayList<ConfigSample>(resultSamples);
			resultSamples = appendAccordingType(gsamples[j], configSamples);
		}
		System.out.println("NUMBER RESULTS: ");
		System.out.println(resultSamples.size());
		return resultSamples;
	}
	
	private List<ConfigSample> appendAccordingType(GSample gsample,List<ConfigSample> existingSamples) {
		switch(gsample.getType()) {
			case "boolean":
				return addBooleanValues(existingSamples);
			case "String":
				return addStringValues(existingSamples, gsample.getCases());
			default:
				System.out.println("Unknown type: " + gsample.getType());
				return existingSamples;
		}
	}
	
	private List<ConfigSample> addBooleanValues(List<ConfigSample> existingSamples) {
		ConfigSample oldSample;
		List<ConfigSample> newSamples = new ArrayList<ConfigSample>();
		Iterator<ConfigSample> configSample = existingSamples.iterator();
		boolean[] allValues = { true, false };
		
		if(existingSamples.size() == 0) {
			for(int i = 0; i < allValues.length; i++) {
				ConfigSample newSample  = new ConfigSample();
				newSample.appendSampleValue(allValues[i]);
				newSamples.add(newSample);
			}
		} else {
			while(configSample.hasNext()) {
				oldSample = configSample.next();
				for(int i = 0; i < allValues.length; i++) {
					ConfigSample copyConfigSample = new ConfigSample(oldSample.getSamples());
					copyConfigSample.appendSampleValue(allValues[i]);
					newSamples.add(copyConfigSample);
				}
			}
		}
		existingSamples.clear();
		return newSamples;
	}
	
	private List<ConfigSample> addStringValues(List<ConfigSample> existingSamples, String[] stringValues) {
		ConfigSample oldSample;
		List<ConfigSample> newSamples = new ArrayList<ConfigSample>();
		Iterator<ConfigSample> configSample = existingSamples.iterator();
		
		if(existingSamples.size() == 0) {
			for(int i = 0; i < stringValues.length; i++) {
				ConfigSample newSample  = new ConfigSample();
				newSample.appendSampleValue(stringValues[i]);
				newSamples.add(newSample);
			}
		} else {
			while(configSample.hasNext()) {
				oldSample = configSample.next();
				for(int i = 0; i < stringValues.length; i++) {
					ConfigSample copyConfigSample = new ConfigSample(oldSample.getSamples());
					copyConfigSample.appendSampleValue(stringValues[i]);
					newSamples.add(copyConfigSample);
				}
			}
		}
		existingSamples.clear();
		return newSamples;
	}
}
