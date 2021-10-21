package derivator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

import configurationManagement.Configuration;


public class DerivationManager {
	
	private DerivationVariableProcessor derivationVariableProcesor;
	private DerivationAnnotationManager derivationAnnotationManager;
	private FileCopy fileCopy;

	public DerivationManager() {
		this.derivationVariableProcesor = new DerivationVariableProcessor(
				Configuration.createConfigurableVariableManager());
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
					System.out.println(actualPath.getFileName());
					System.out.println(actualPath.toUri().getRawPath().substring(baseLength));
					
					if (newDirectoryOrFileString == "") {
						//continue; // to not create base output directory
					}
					System.out.println(">" + outputDirectoryOrFileString + "<");
					Path outputDirectoryPath = Path.of(URI.create(outputDirectoryOrFileString));
					Files.createDirectory(outputDirectoryPath);
					//System.out.println(actualPath.toUri().getRawPath());
				} else {
					String baseInputPath = inputPath.substring(inputPath.indexOf("C:/"));
					String baseOutputPath = outputPath.substring(inputPath.indexOf("C:/"));
					System.out.println(baseInputPath);
					System.out.println(baseInputPath + "/" + newDirectoryOrFileString);
					System.out.println(baseOutputPath);
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
}
