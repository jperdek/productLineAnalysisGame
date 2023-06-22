package base;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.Arrays;


//@{}
public class RecordLoader<T> {

	public RecordLoader() {
		
	}
	
	public List<T> readData(String fileName, String delimiter, GenericFactory<T> factory, DataFilter<T> filter) throws FileNotFoundException {
		List<T> collectedData = new ArrayList<T>();
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String[] line;
		T resultingRecord;
		String parsedPart;
		int row = 0;
		int dataSize = 0;
		try {
			while(br.ready()) {
				parsedPart = br.readLine();
				line = parsedPart.split(delimiter, -1);
				if (row==0) {
					row++;
					dataSize = line.length;
					continue;
				}
				if(line.length != dataSize) {
					continue;
				}
				resultingRecord = factory.create(line);
				if (filter.passedFiltering(resultingRecord)) {
					collectedData.add(resultingRecord);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return collectedData;
	}

	public List<T> readData2(String fileName, String delimiter, GenericFactory<T> factory, DataFilter<T> filter) throws IOException, CsvException {
		List<T> collectedData = new ArrayList<T>();
		RFC4180Parser parser = new RFC4180ParserBuilder().build();
		//CSVParser parser = new CSVParserBuilder().withStrictQuotes(true)
        //        .withQuoteChar('"').withSeparator(delimiter).withEscapeChar('\\').build();


		T resultingRecord;
		String[] array;
		int index = 0;
		

		CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(parser).withSkipLines(1).build();
		while((array = csvReader.readNext()) != null) {
			System.out.println(Arrays.toString(array));
			resultingRecord = factory.create(array);
			if (filter.passedFiltering(resultingRecord)) {
				collectedData.add(resultingRecord);
			}
		}
		return collectedData;
	}
}