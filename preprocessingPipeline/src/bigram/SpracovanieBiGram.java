package bigram;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Manages operation to count probability of bi-grams only
 * 
 * @author perdek
 *
 */
public class SpracovanieBiGram {
	
	private TabulkaAGram tabulka;
	private HashSet<String> hashSet;
	
	/**
	 * Manages operation to count probability of bi-grams only
	 * 
	 * @param subor			- file with data for training
	 * @param velkostK		- number of words which should be used to train 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public SpracovanieBiGram(String subor, int velkostK) throws FileNotFoundException, IOException
	{
		tabulka = new TabulkaAGram(velkostK);
		hashSet = tabulka.ziskajHashSet();
		nacitajObsahSuboruANatrenuj(subor, velkostK, false);	
	}
	
	/**
	 * Manages operation to count probability of bi-grams only
	 * 
	 * @param subor			- file with data for training
	 * @param velkostK		- number of words which should be used to train 
	 * @param kSlov			-first k-words which should be used to train 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public SpracovanieBiGram(String subor, int velkostK, String kSlov[]) throws FileNotFoundException, IOException
	{
		tabulka = new TabulkaAGram(velkostK);
		tabulka.pridajZoznamRetazcov(kSlov);
		hashSet = new HashSet<String>();
		nacitajObsahSuboruANatrenuj(subor, velkostK, true);
	}
	
	/**
	 * Validates data
	 * 
	 * @param subor		- file for validation
	 * @return			- result of validation
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public double validuj(String subor) throws FileNotFoundException, IOException
	{
		return tabulka.validuj(subor);
	}
	
	
	/**
	 * Returns number of words
	 * 
	 * @return number of used words (not bi-grams)
	 */
	public int vratPocetWords()
	{
		return tabulka.vratPocetWords();
	}
	
	/**
	 * 
	 * @param text	  - representative candidates for bi-grams		
	 * @param velkost - number of first words
	 * @return
	 */
	private String[] vyberPrvychVelkost(final String[] text,final int velkost)
	{
		String[] pole= new String[velkost];
		for(int i=0; i<velkost; i++)
		{
			pole[i]= text[i];
		}
		return pole;
	}
	
	/**
	 * Trains bi-grams and creates set of training words and bigrams if is not created
	 * 
	 * @param subor					- file for training
	 * @param velkostK				- number of learned bi-grams
	 * @param pridaneElementy		- if training set was not specified
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void nacitajObsahSuboruANatrenuj(final String subor,final int velkostK, boolean pridaneElementy) throws FileNotFoundException, IOException
	{
		Scanner sc;
		FileReader fr;
		
		if(!pridaneElementy)
		{
			fr = new FileReader(subor);
			sc = new Scanner(fr);
			
			pridajElementy(sc, velkostK);
			
			sc.close();
			fr.close();
		}
		

		fr = new FileReader(subor);
		sc = new Scanner(fr);
		
		spracujZoSuboru(sc, velkostK);
		
		sc.close();
		fr.close();
	}
	
	/**
	 * Shows table, prints its content
	 */
	public void zobrazTabulku()
	{
		tabulka.zobrazTabulku();
	}
	
	/**
	 * Adds elements to the set
	 * 
	 * @param sc		- scanner for reading from file
	 * @param velkostK  - number of learned bi-grams
	 */
	private void pridajElementy(Scanner sc, final int velkostK)
	{
		int pridane = 0;
		ArrayList<String> pridavanePrvky;
		pridavanePrvky = new ArrayList<String>();
		
		while(sc.hasNext() && pridane < velkostK)
		{
			pridavanePrvky.add(sc.next());
			pridane= pridane + 1;
			//System.out.println("MAM");
		}
		
		tabulka.pridajZoznamRetazcov(pridavanePrvky);
		sc.close();
	}
	
	/**
	 * Counts occurrences of words and bi-grams
	 * 
	 * @param sc 		- scanner for reading from file
	 * @param velkostK	- number of learned bi-grams
	 */
	private void spracujZoSuboru(Scanner sc,final int velkostK)
	{
		String predchadzajuci = null;
		String vyhodnocovany = null;
	
		
		//PREPARATION
		if(sc.hasNext()){	predchadzajuci = sc.next();}
		if(sc.hasNext()){	vyhodnocovany = sc.next();}
	
		System.out.println(predchadzajuci +" "+vyhodnocovany);
		
		while(sc.hasNext())
		{
			tabulka.zvysHodnotu(predchadzajuci, vyhodnocovany);
			
			//INSERT TO COUNT MAX WORDS TO APPROXIMATE
			tabulka.zvysHodnotuSamotneho(predchadzajuci);
			hashSet.add(predchadzajuci);
			
			//STEP
			predchadzajuci= vyhodnocovany;
			vyhodnocovany = sc.next();
		}

		if(predchadzajuci != null)
		{
			tabulka.zvysHodnotuSamotneho(predchadzajuci);
			hashSet.add(predchadzajuci);
		}
		if(vyhodnocovany != null)
		{
			tabulka.zvysHodnotuSamotneho(vyhodnocovany );
			hashSet.add(vyhodnocovany );
		}
		tabulka.zvysHodnotu(predchadzajuci, vyhodnocovany);
	}
	
	public int vratVyskytBigramu(String predchadzajuce, String vyhodnocovane) {
		return tabulka.vratHodnotu(predchadzajuce, vyhodnocovane);
	}
	
	public boolean maPrvokBigram(String predchadzajuce, String vyhodnocovane) {
		return tabulka.maPrvokBigram(predchadzajuce, vyhodnocovane);
	}
	
	public int vratVyskytSamotneho(String slovo) { return tabulka.vratHodnotuSamotneho(slovo); }
	
	public boolean maPrvokSamotny(String slovo) { return tabulka.maPrvokSamotny(slovo); }
}
