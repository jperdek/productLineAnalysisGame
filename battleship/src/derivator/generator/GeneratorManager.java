package derivator.generator;

import derivator.ConfigFilePersistance;
import derivator.ConfigurationVariableManager;
import derivator.DerivationBaseConfig;
import derivator.DerivationManager;
import derivator.features.Features;
import derivator.features.IncorrectFeaturesEntryUsageException;
import derivator.features.SingleFeature;

import java.io.IOException;
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
	
	public void generateProjectsFromSamples(String inputPath, String outputPath, String projectName, String inputConfigPath) throws IncorrectFeaturesEntryUsageException, IOException {
		Iterator<ConfigSample> generatedSamples = this.generatedSamples.iterator();
		ConfigurationVariableManager configurationVariableManagerForSample;
		DerivationManager derivationManager;
		ConfigFilePersistance configFilePersistance;
		String actualProjectName;
		int j = 1;
		
		while(generatedSamples.hasNext()) {
			actualProjectName = projectName + j;
			configurationVariableManagerForSample = GeneratorManager.createConfigurableVariableManager(
					this.gsamples, generatedSamples.next());
			derivationManager = new DerivationManager(configurationVariableManagerForSample);
			DerivationManager.createSoftwareDerivation(inputPath, outputPath, actualProjectName, derivationManager);
			System.out.println(outputPath.replace("file:///", "") + actualProjectName + "/" + DerivationBaseConfig.RESOURCES_CONFIG_PATH);
			configFilePersistance = new ConfigFilePersistance(
					configurationVariableManagerForSample, inputConfigPath, 
					outputPath.replace("file:///", "") + actualProjectName + "/" + DerivationBaseConfig.RESOURCES_CONFIG_PATH);
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
		Features newConfigFeature;
		for(int i = 0; i < gsample.length; i++) {
			String observedValue = configSample.getSample(i).toString();
			if (observedValue.equals("true") || observedValue.equals("false")) {
				newConfigFeature = new SingleFeature(configSample.getSample(i).toString(), Boolean.parseBoolean(observedValue));
			} else {
				newConfigFeature = new SingleFeature(configSample.getSample(i).toString(), observedValue);
			}
			configurationVariableManager.addVariable(gsample[i].getVariableName(), newConfigFeature);
		}
		
		return configurationVariableManager;
	}
	
	public static void main(String args[]) throws IncorrectFeaturesEntryUsageException, IOException {
		DerivationManager derivationManager = new DerivationManager();
		GeneratorManager generatorManager = new GeneratorManager(
				derivationManager.getConfigurationVariableManager(), DerivationBaseConfig.VARIABLE_RESTRICTIONS_FILE_PATH);
		generatorManager.generateProjectsFromSamples(
				DerivationBaseConfig.BASE_PROJECT_SPL_PATH,
				DerivationBaseConfig.NEW_DERIVATIONS_FOLDER_PATH, "game",
				DerivationBaseConfig.RESOURCES_CONFIG_PATH);
	}
}
