package derivator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

import configurationManagement.Configuration;
import configurationManagement.ConfigurationLoader;
import derivator.features.IncorrectFeaturesEntryUsageException;
import derivator.features.SingleFeature;


public class DerivationManager {
	
	private DerivationVariableProcessor derivationVariableProcesor;
	private DerivationAnnotationManager derivationAnnotationManager;
	private ConfigurationVariableManager configurationVariableManager;
	private FileCopy fileCopy;

	public DerivationManager() {
		this(createConfigurableVariableManager());
	}
	
	public DerivationManager(ConfigurationVariableManager configurationVariableManager) {
		this.configurationVariableManager = configurationVariableManager;
		this.derivationVariableProcesor = new DerivationVariableProcessor(this.configurationVariableManager);
		this.derivationAnnotationManager = new DerivationAnnotationManager(this.derivationVariableProcesor);
		this.fileCopy = new FileCopy(this.derivationAnnotationManager);
	}
	
	public ConfigurationVariableManager getConfigurationVariableManager() {
		return this.configurationVariableManager;
	}
	
	public DerivationVariableProcessor getDerivationVriableProcesor() { 
		return this.derivationVariableProcesor; 
	}
	
	public DerivationAnnotationManager getDerivationAnnotationManager() {
		return this.derivationAnnotationManager;
	}
	
	public FileCopy getFileCopy() {
		return this.fileCopy;
	}
	
	public void processDerivation(String inputPath, String outputPath) throws IncorrectFeaturesEntryUsageException {
		Stream<Path> s = null;
		try {
			Path inputPath1 = Path.of(URI.create(inputPath));
			int baseLength = inputPath1.toUri().getRawPath().length();
			Files.isDirectory(inputPath1);
			s = Files.walk(inputPath1);
			Iterator<Path> paths = s.iterator();
			while(paths.hasNext()) {
				Path actualPath = paths.next();
				String newDirectoryOrFileString = actualPath.toUri().getRawPath().substring(baseLength);
				String outputDirectoryOrFileString = outputPath + newDirectoryOrFileString;
				if(Files.isDirectory(actualPath)) {
					Path outputDirectoryPath = Path.of(URI.create(outputDirectoryOrFileString));
				try {
					Files.createDirectory(outputDirectoryPath);
				} catch(Exception e) {
				}
				} else {
					String baseInputPath = inputPath.substring(inputPath.indexOf(DerivationBaseConfig.USED_FILE_SYSTEM));
					String baseOutputPath = outputPath.substring(inputPath.indexOf(DerivationBaseConfig.USED_FILE_SYSTEM));
					this.fileCopy.processFile(baseInputPath + "/" + newDirectoryOrFileString,
							baseOutputPath + "/" + newDirectoryOrFileString);
				}
			}
			
			//this.fileCopy.processFile(inputPath, outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
	
	public static ConfigurationVariableManager createConfigurableVariableManager() {
		ConfigurationVariableManager configurationVariableManager = new ConfigurationVariableManager();

		configurationVariableManager.addVariable("playerNames", new SingleFeature(Boolean.toString(Configuration.playerNames), true));
		configurationVariableManager.addVariable("computerOpponent", new SingleFeature(Boolean.toString(Configuration.computerOpponent), true));
		configurationVariableManager.addVariable("statistics", new SingleFeature(Boolean.toString(Configuration.statistics), true));
		configurationVariableManager.addVariable("challenge", new SingleFeature(Boolean.toString(Configuration.challenge), true));
		configurationVariableManager.addVariable("difficulty", new SingleFeature(Configuration.difficulty, true));
		
		return configurationVariableManager;
	}
	
	//DEPRECATED
	public static void test() throws IncorrectFeaturesEntryUsageException {
		String newProjectName = "NewProject";
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/battleshipConfig.json");
		DerivationManager derivationManager = new DerivationManager();
		ProjectCopier.copyExistingProject(
				DerivationBaseConfig.BASE_PROJECT_SPL_PATH + "\\projectSkeleton", 
				DerivationBaseConfig.NEW_DERIVATIONS_FOLDER_PATH + newProjectName + "/");
		derivationManager.processDerivation(DerivationBaseConfig.BASE_PROJECT_SPL_PATH + "/src",
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Generated/src/");
	}
	
	public static void test1() throws IncorrectFeaturesEntryUsageException {
		String newProjectName = "NewProject";
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/battleshipConfig.json");
		DerivationManager derivationManager = new DerivationManager();
		DerivationManager.createSoftwareDerivation(
				DerivationBaseConfig.BASE_PROJECT_SPL_PATH,
				DerivationBaseConfig.NEW_DERIVATIONS_FOLDER_PATH, 
				newProjectName, derivationManager);
	}
	
	public static void createSoftwareDerivation(String inputPath, String outputPath, 
			String projectName, DerivationManager derivationManager) throws IncorrectFeaturesEntryUsageException {
		String baseProjectSkeletonPath = inputPath + "projectSkeleton";
		String baseProjectSrcPath = inputPath + "src";
		String newProjectName = outputPath + projectName + "/";
		String targetProjectSrcPath = newProjectName + "src/";
		ProjectCopier.copyExistingProject(baseProjectSkeletonPath, newProjectName);
		derivationManager.processDerivation(baseProjectSrcPath, targetProjectSrcPath);
	}
	
	public static void main(String[] args) throws IncorrectFeaturesEntryUsageException {
		DerivationManager.test1();
	}
}
