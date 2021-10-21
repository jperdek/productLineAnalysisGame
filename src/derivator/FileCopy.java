package derivator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class FileCopy {

	private DerivationAnnotationManager derivationAnnotationManager;
	
	public FileCopy(DerivationAnnotationManager derivationAnnotationManager) {
		this.derivationAnnotationManager = derivationAnnotationManager;
	}
	
	public void processFile(String inputPath, String outputPath) throws IOException {
		boolean shouldRemove = true;
		boolean resultRemove = true;
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(inputPath);
			br = new BufferedReader(fr);
			
			fw = new FileWriter(outputPath);
			bw = new BufferedWriter(fw);
			
			shouldRemove = this.derivationAnnotationManager.searchForAnnotation(br, bw);
			while(br.ready()) {
				resultRemove = this.derivationAnnotationManager.searchForAnnotation(br, bw);
				
				if(resultRemove == false) {
					shouldRemove = false;
				}
			}
		
		} catch (IncorrectAnnotationUsageException e) {
			e.printStackTrace();
		} catch(FileNotFoundException fnf) {
			fnf.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) { br.close(); };
			if(fr != null) { fr.close(); };
			if(bw != null) { bw.close(); };
			if(fw != null) { fw.close(); };
			if (shouldRemove) {
				Files.deleteIfExists(Path.of(URI.create("file:///" + outputPath)));
			}
		}
	}
}
