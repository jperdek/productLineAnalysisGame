package derivator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.stream.Stream;


public class ProjectCopier {
	public static void copyExistingProject(String pathToProjectTree, String pathToNewProject) {
		Stream<Path> s = null;
		try {
			System.out.println("Getting and applying config from: " + pathToProjectTree);
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
					try {
						Files.createDirectory(outputDirectoryPath);
					} catch(Exception e) {
					}
				} else {
					Path outputDirectoryPath = Path.of(URI.create(outputDirectoryOrFileString));
		
					Files.copy(actualPath, outputDirectoryPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch(Exception e) {
			}
		}
	}
}
