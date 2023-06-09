package bigram;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * Random text is generated from: http://randomtextgenerator.com/
 * Test based on counting number of words as here: http://www.writewords.org.uk/word_count.asp
 * Bigrams are generated and verified from here https://onlinetexttools.com/generate-text-bigrams
 * 
 * @author perdek
 *
 */
@DisplayName("Verify bigrams and neccesary frequencies and calculations")
class BigramTest {

	private static SpracovanieBiGram spracovanie;
	
	
	@BeforeAll
	static void setUpClass() {
		
		try
		{
			spracovanie = new SpracovanieBiGram("unitTest1Clean.txt",900);
		}
		catch(FileNotFoundException ee)
		{
			ee.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Frequency of all words found")
	void testNumberWords() {
		assertEquals(367, spracovanie.vratPocetWords());
	}
	
	
	@ParameterizedTest(name="{index}: Frequency={0}, Word={1}")
	@CsvFileSource(resources= {"/unitTest1Results.txt"}, delimiter= ' ')
	@DisplayName("Basic words")
	void test1WordFrequencies(int frequency, String word) {
			assertAll("Overovane slovo ",
					()-> {assertTrue(spracovanie.maPrvokSamotny(word));},
					()-> {assertEquals(frequency,spracovanie.vratVyskytSamotneho(word));}
					);
	}
	
	@Test
	@DisplayName("Not used words")
	void testForNoneWords() {
		assertAll("Overenie nepritomnych slov",
				()-> { assertFalse(spracovanie.maPrvokSamotny("fail"));},
				()-> { assertFalse(spracovanie.maPrvokSamotny("fraud"));},
				()-> { assertFalse(spracovanie.maPrvokSamotny("tail"));},
				()-> { assertFalse(spracovanie.maPrvokSamotny("misuse"));},
				()-> { assertFalse(spracovanie.maPrvokSamotny("odveci"));}
				);
	}
	
	
	@ParameterizedTest(name="{index}: frequency={0}, firstPart={1}, secondPart={2}")
	@CsvFileSource(resources={"/unitTest1BigramFrequencies.txt"}, delimiter= ' ')
	@DisplayName("Bigrams")
	void testForBigrams(int frequency, String firstPart, String secondPart) {
		assertAll("Overenie najdenych bigramov",
				() -> { assertTrue(spracovanie.maPrvokBigram(firstPart, secondPart));},
				() -> { assertEquals(frequency, spracovanie.vratVyskytBigramu(firstPart, secondPart));}
		);
	}
	
}
