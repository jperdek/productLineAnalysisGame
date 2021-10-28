package derivator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

import configurationManagement.Configuration;
import configurationManagement.ConfigurationLoader;


public class DerivationManager {
	
	private DerivationVariableProcessor derivationVariableProcesor;
	private DerivationAnnotationManager derivationAnnotationManager;
	private FileCopy fileCopy;

	public DerivationManager() {
		this.derivationVariableProcesor = new DerivationVariableProcessor(createConfigurableVariableManager());
		this.derivationAnnotationManager = new DerivationAnnotationManager(this.derivationVariableProcesor);
		this.fileCopy = new FileCopy(this.derivationAnnotationManager);
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
	
	public void processDerivation(String inputPath, String outputPath) {
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
					Files.createDirectory(outputDirectoryPath);
				} else {
					String baseInputPath = inputPath.substring(inputPath.indexOf("C:/"));
					String baseOutputPath = outputPath.substring(inputPath.indexOf("C:/"));
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

		configurationVariableManager.addVariable("playerNames", Boolean.toString(Configuration.playerNames));
		configurationVariableManager.addVariable("computerOpponent", Boolean.toString(Configuration.computerOpponent));
		configurationVariableManager.addVariable("statistics", Boolean.toString(Configuration.statistics));
		configurationVariableManager.addVariable("challenge", Boolean.toString(Configuration.challenge));
		configurationVariableManager.addVariable("difficulty", Configuration.difficulty);
		
		return configurationVariableManager;
	}
	
	public static void test() {
		String newProjectName = "NewProject";
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/battleshipConfig.json");
		DerivationManager derivationManager = new DerivationManager();
		ProjectCopier.copyExistingProject("file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Java-Battleship/projectSkeleton", 
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/" + newProjectName + "/");
		derivationManager.processDerivation("file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Java-Battleship/src",
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Generated/src/");
	}
	
	public static void test1() {
		String newProjectName = "NewProject";
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/battleshipConfig.json");
		DerivationManager derivationManager = new DerivationManager();
		DerivationManager.createSoftwareDerivation(
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/Java-Battleship/",
				"file:///C://Users/perde/OneDrive/Desktop/tutorials/aspekty/allAspectApp/", 
				newProjectName, derivationManager);
	}
	
	public static void createSoftwareDerivation(String inputPath, String outputPath, 
			String projectName, DerivationManager derivationManager) {
		String baseProjectSkeletonPath = inputPath + "projectSkeleton";
		String baseProjectSrcPath = inputPath + "src";
		String newProjectName = outputPath + projectName + "/";
		String targetProjectSrcPath = newProjectName + "src/";
		ProjectCopier.copyExistingProject(baseProjectSkeletonPath, newProjectName);
		derivationManager.processDerivation(baseProjectSrcPath, targetProjectSrcPath);
	}
	
	public static void main(String[] args) {
		DerivationManager.test1();
	}
}
