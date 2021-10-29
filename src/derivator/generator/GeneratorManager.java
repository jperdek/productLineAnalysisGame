package derivator.generator;

import derivator.ConfigurationVariableManager;
import derivator.DerivationManager;

import java.util.Iterator;
import java.util.List;

public class GeneratorManager {

	private GSample[] gsamples;
	private WholeSamplesGenerator wholeSamplesGenerator;
	private List<ConfigSample> generatedSamples;
	
	public GeneratorManager(ConfigurationVariableManager configurationVariableManager, 
			String pathToBoundaries) {
		gsamples = VariableBoundariesLoader.loadVariableBoundaries(configurationVariableManager, 
				pathToBoundaries);
		wholeSamplesGenerator = new WholeSamplesGenerator();
		generatedSamples = wholeSamplesGenerator.generateSamples(gsamples);
	}
	
	public void generateProjectsFromSamples(String inputPath, String outputPath, String projectName) {
		Iterator<ConfigSample> generatedSamples = this.generatedSamples.iterator();
		ConfigurationVariableManager configurationVariableManagerForSample;
		DerivationManager derivationManager;
		int j = 1;
		
		while(generatedSamples.hasNext()) {
			configurationVariableManagerForSample = GeneratorManager.createConfigurableVariableManager(
					this.gsamples, generatedSamples.next());
			derivationManager = new DerivationManager(configurationVariableManagerForSample);
			DerivationManager.createSoftwareDerivation(inputPath, outputPath, projectName + j, derivationManager);
			j++;
		}
	}
	
	private void printSamples() {
		for(int i = 0; i < gsamples.length; i++) {
			System.out.println(gsamples[i].getVariableName());
			System.out.println(gsamples[i].getNumberCases());
		}
	}
	
	public static ConfigurationVariableManager createConfigurableVariableManager(
			GSample gsample[], ConfigSample configSample) {
		ConfigurationVariableManager configurationVariableManager = new ConfigurationVariableManager();
		for(int i = 0; i < gsample.length; i++) {
			configurationVariableManager.addVariable(gsample[i].getVariableName(), configSample.getSample(i).toString());
		}
		
		return configurationVariableManager;
	}
	
	public static void main(String args[]) {
		DerivationManager derivationManager = new DerivationManager();
		GeneratorManager generatorManager = new GeneratorManager(
				derivationManager.getConfigurationVariableManager(), "resources/variableRestrictions.json");
		generatorManager.generateProjectsFromSamples(
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Java-Battleship/",
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/", "game");
	}
}
