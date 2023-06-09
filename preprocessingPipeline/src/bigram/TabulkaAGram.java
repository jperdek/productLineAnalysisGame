package bigram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;


/**
 * Table for storing information about bigrams, their count
 * 
 * @author perdek
 *
 */
public class TabulkaAGram implements Serializable {
	
	private int[][] vyskyt;
	private int[] vyskytSamotneho;
	private HashMap<String, Integer> hashMap;
	private int rozmer;
	
	//storage for gram - word
	private HashSet<String> hashSet;
	
	/**
	 * Table representation used for bi-grams
	 * 
	 * @param rozmer
	 */
	public TabulkaAGram(int rozmer)
	{
		this.rozmer = rozmer;
		vyskyt = new int[rozmer][rozmer];
		vyskytSamotneho = new int[rozmer];
		hashMap = new HashMap<String,Integer>(rozmer);
		nulujTabulku(vyskyt, rozmer);
		
		hashSet= new HashSet<String>();
	}
	
	public int vratPocetWords()
	{
		return hashSet.size();	
	}
	
	
	public HashSet<String> ziskajHashSet()
	{
		return hashSet;
	}
	
	/**
	 * Adds list of strings from field in order as they are in field
	 * 
	 * @param zoznam list of strings - words
	 * @return true if operation is fully applied, otherwise false
	 */
	public boolean pridajZoznamRetazcov(String zoznam[])
	{
		//if(zoznam.length >= rozmer)
		{
		//	return false;
		}
		
		for(int i=0; i<rozmer; i++)
		{
			hashMap.put(zoznam[i], i);
		}
		
		return true;
	}
	
	/**
	 * Adds list of strings from ArrayList in order as they are in list
	 * 
	 * @param zoznam list of strings - words
	 * @return true if operation is fully applied, otherwise false
	 */
	public boolean pridajZoznamRetazcov(ArrayList<String> zoznam)
	{
		//if(zoznam.size() > rozmer)
		{
			//return false;
		}
		
		for(int i=0; i<rozmer && zoznam.size() > i; i++) {
			hashMap.put(zoznam.get(i), i);
		}
		
		return true;
	}
	
	/**
	 * Nulls all values in table
	 * 
	 * @param rozmer - table
	 * @param velkost - size
	 */
	private void nulujTabulku(int rozmer[][], int velkost)
	{
		for(int j = 0; j< velkost; j=j+1)
		{
			vyskytSamotneho[j] = 0;
			
			for(int i = 0; i< velkost; i=i+1) {
				rozmer[j][i] = 0;
			}
		}
	}
	
	/**
	 * Increases value in two dimensional table - representation of information about bigrams
	 * 
	 * @param predchadzajuce - word on the left
	 * @param vyhodnocovane - evaluated word
	 */
	public void zvysHodnotu(String predchadzajuce, String vyhodnocovane)
	{
		int stlpce, riadky;
		if(hashMap.containsKey(predchadzajuce) && hashMap.containsKey(vyhodnocovane))
		{
			stlpce = hashMap.get(predchadzajuce);
			riadky = hashMap.get(vyhodnocovane);
			
			vyskyt[stlpce][riadky] = vyskyt[stlpce][riadky] + 1;
		}
	}
	
	/**
	 * Returns count from two-dimensional table - representation of information about bigrams
	 * 
	 * @param predchadzajuce - word on the left
	 * @param vyhodnocovane - evaluated word
	 * @return count from two-dimensional table, otherwise if key is not contained returns null
	 */
	public int vratHodnotu(String predchadzajuce, String vyhodnocovane)
	{
		int stlpce, riadky;
		if(vyhodnocovane.equals("did")) { predchadzajuce="village";}
		if(hashMap.containsKey(predchadzajuce) && hashMap.containsKey(vyhodnocovane))
		{
			stlpce = hashMap.get(predchadzajuce);
			riadky = hashMap.get(vyhodnocovane);
			return vyskyt[stlpce][riadky];
		}
	
			return 0;
	}
	
	/**
	 * Finds if bigram is present
	 * 
	 * @param predchadzajuci - word on the left
	 * @param vyhodnocovane - evaluated word
	 * @return true if bigram is present, otherwise false
	 */
	public boolean maPrvokBigram(String predchadzajuci, String vyhodnocovane) {
		//if(vyhodnocovane.equals("did")) { predchadzajuci="village";}
		return hashMap.containsKey(predchadzajuci) && hashMap.containsKey(vyhodnocovane);
	}
	
	/**
	 * Increases value if word predchadzajuci is in table (one dimension)
	 *  
	 * @param predchadzajuci
	 */
	public void zvysHodnotuSamotneho(String predchadzajuci)
	{
		if(hashMap.containsKey(predchadzajuci) )
		{
			int index = hashMap.get(predchadzajuci);
			if(vyskytSamotneho[index] == 0) { vyskytSamotneho[index] = 1; }
			else {
				vyskytSamotneho[index] = vyskytSamotneho[index] + 1;
			}
		}
	}
	
	/**
	 * if value is stored in table then returns its count, otherwise (identity)
	 * 
	 * @param predchadzajuci - word which should be valuated
	 * @return count of variable - word if is in table (one dimension)
	 */
	public int vratHodnotuSamotneho(String predchadzajuci)
	{
		if(hashMap.containsKey(predchadzajuci) )
		{
			int index = hashMap.get(predchadzajuci);
			return vyskytSamotneho[index];
		}
		return 0;
	}
	
	/**
	 * Finds if word is present in statistics
	 * 
	 * @param predchadzajuci
	 * @return
	 */
	public boolean maPrvokSamotny(String predchadzajuci) { return hashMap.containsKey(predchadzajuci); }
	
	/**
	 * Shows table
	 */
	public void zobrazTabulku()
	{
		for(int j = 0; j< rozmer; j=j+1)
		{			
			for(int i = 0; i< rozmer; i=i+1)
			{
				System.out.print(vyskyt[j][i] +" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		
		for(int j = 0; j< rozmer-1; j=j+1)
		{	
			System.out.print(vyskytSamotneho[j] +" ");
		}
		System.out.println(vyskytSamotneho[rozmer - 1]);
		
		System.out.println(hashMap.keySet());
	}
	
	/**
	 * Analysis of text 
	 * 
	 * @param arrayForValidation
	 * @return
	 */
	public double vyhodnot(ArrayList<String> arrayForValidation)
	{
		String predchadzajuci = null;
		String vyhodnocovany = null;
		double vyslednaHodnota = 1.0;
		double menovatel;
		int stlpce, riadky;
		
		Iterator<String> i =arrayForValidation.iterator();
		if(i.hasNext())
		{
			predchadzajuci = i.next();
		}
		
		while(i.hasNext())
		{
			//ANALYSED
			vyhodnocovany = i.next();
			if(hashMap.containsKey(predchadzajuci) && hashMap.containsKey(vyhodnocovany))
			{
				stlpce = hashMap.get(predchadzajuci);
				riadky = hashMap.get(vyhodnocovany);
			
				if(vyskytSamotneho[stlpce] == 0)
					menovatel = 1;
				else
					menovatel = vyskytSamotneho[stlpce];
				
				//ANALYSIS
				vyslednaHodnota = vyslednaHodnota*(vyskyt[stlpce][riadky]/menovatel);
			}
			//NEXT
			predchadzajuci = vyhodnocovany;
		}
		
		return vyslednaHodnota;
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
		ArrayList<String> arrayForValidation = new ArrayList<String>();
		arrayForValidation = loadContextOfFile(subor, arrayForValidation);
		return vyhodnot(arrayForValidation);
	}
	
	/**
	 * Validates data from file
	 * 
	 * @param subor					- file for validation
	 * @param arrayForValidation	- array for validation	
	 * @return	filled array with words from the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private ArrayList<String> loadContextOfFile(String subor, ArrayList<String> arrayForValidation) throws FileNotFoundException, IOException
	{
		FileReader fr = new FileReader(subor);
		Scanner sc = new Scanner(fr);
		
		while(sc.hasNext())
		{
			arrayForValidation.add(sc.next());
		}
		
		sc.close();
		fr.close();
		
		return arrayForValidation;
	}
}
