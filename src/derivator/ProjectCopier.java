package derivator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class ProjectCopier {
	public static void copyExistingProject(String pathToProjectTree, String pathToNewProject) {
		Stream<Path> s = null;
		try {
			Path inputPath1 = Path.of(URI.create(pathToProjectTree));
			int baseLength = inputPath1.toUri().getRawPath().length();
			s = Files.walk(inputPath1);
			Iterator<Path> paths = s.iterator();
			while(paths.hasNext()) {
				Path actualPath = paths.next();
				String newDirectoryOrFileString = actualPath.toUri().getRawPath().substring(baseLength);
				String outputDirectoryOrFileString = pathToNewProject + newDirectoryOrFileString;
				if(Files.isDirectory(actualPath)) {
					Path outputDirectoryPath = Path.of(URI.create(outputDirectoryOrFileString));
					Files.createDirectory(outputDirectoryPath);
				} else {
					Path outputDirectoryPath = Path.of(URI.create(outputDirectoryOrFileString));
					//String baseInputPath = pathToProjectTree.substring(pathToProjectTree.indexOf("C:/"));
					//String baseOutputPath = pathToNewProject.substring(pathToProjectTree.indexOf("C:/"));
					Files.copy(actualPath, outputDirectoryPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
}
