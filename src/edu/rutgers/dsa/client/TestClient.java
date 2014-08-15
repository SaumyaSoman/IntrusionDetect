package edu.rutgers.dsa.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;


import edu.rutgers.dsa.ac.AhoCorasick;
import edu.rutgers.dsa.boyer.BoyerMoore;
import edu.rutgers.dsa.kmp.KnuthMorrisPratt;

/**
 *  This class  tests the three string algorithms for 
 *  input text files of size 8Kb, 16Kb, 32Kb and 64Kb for different pattern sizes.
 *	The java application must be run from this class.
 */

public class TestClient {

	static int len=4000;	// its the number of words that should be read from the file.

	/**
	 * This function reads the pattern file and the text file.
	 */
	public static void main(String args[]){	

		/*
		 *  Scan the input text file
		 */
		String text = "";
		//File textFile = new File("src/text/8kb.txt");
		//File textFile = new File("src/text/16kb.txt");
		//File textFile = new File("src/text/32kb.txt");
		File textFile = new File("src/text/64kb.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(textFile);
			text = scanner.nextLine();
			while (scanner.hasNextLine())
				text = text + scanner.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			scanner.close();
		}

		/*
		 *  Scan the pattern file
		 */

		/*
		 * Pattern files with same number of characters
		 * len must be changed for each run.
		 * For 10 letter pattern file len=1493, for 6 letter pattern file len=1866 and so on.
		 */
		
		//File patternFile = new File("src/patterns/10letter-1493.txt");
		// File patternFile = new File("src/patterns/8letter-1866.txt");
		// File patternFile = new File("src/patterns/6letter-2488.txt");
		//File patternFile = new File("src/patterns/4letter-3732.txt");
		
		/*
		 * Pattern files that should be used to get same number of words- 
		 * we ran with len=1000,2000,3000,4000
		 */
		
		
		File patternFile = new File("src/patterns/10letter-14888.txt");
		//File patternFile = new File("src/patterns/8letter-29766.txt");
		//File patternFile = new File("src/patterns/6letter-15788.txt");
		//File patternFile = new File("src/patterns/4letter-4030.txt");		
		
		String[] pattern = new String[len];
		Scanner scanner1 = null;
		try {			
			scanner1 = new Scanner(patternFile);
			for (int i = 0; i < len; i++) {
				pattern[i] = scanner1.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			scanner1.close();
		}	

		//Tests

		testBoyerMoore(text,pattern);
		testKnuthMorrisPratt(text, pattern);
		testAhoCorasick(text, pattern);

	}

	/**
	 * Method to test Boyer Moore
	 * @param text : Text string that has to be scanned
	 * @param pattern : String of patterns
	 */
	public static void testBoyerMoore(String text,String[] pattern){
		long start = 0L, end = 0L, end2 = 0L;
		BoyerMoore bm=new BoyerMoore(len,pattern);
		//--- Start of pre-processing phase ---

		start = System.nanoTime();
		bm.preprocess();
		end = System.nanoTime();
		System.out.println("time for preprocessing in BM..." + (end - start));

		//--- End of pre-processing phase ---

		//--- Start of Search Phase ---

		HashSet<String> termsThatHit=bm.search(text);
		end2 = System.nanoTime();
		System.out.println("time for search in BM..." + (end2 - end));
		System.out.println("time for preprocessing and search in BM..."+ (end2 - start));

		//--- End of Search phase ---

		//--- Patterns found and the number of hits ---

		Iterator<String> iter = termsThatHit.iterator();
		System.out.println("Hits BM: ");
		System.out.println(termsThatHit.toString());
		System.out.println(termsThatHit.size());
	}

	/**
	 * Method to test Knuth-Morris-Pratt
	 * @param text : Text string that has to be scanned
	 * @param pattern : String of patterns
	 */
	public static void testKnuthMorrisPratt(String text,String[] pattern){
		long start = 0L, end = 0L, end2 = 0L;
		KnuthMorrisPratt kmp=new KnuthMorrisPratt(len,pattern);
		//--- Start of pre-processing phase ---

		start = System.nanoTime();
		kmp.preprocess();
		end = System.nanoTime();
		System.out.println("time for preprocessing in KMP..." + (end - start));

		//--- End of pre-processing phase ---

		//--- Start of Search Phase ---


		HashSet<String> termsThatHit=kmp.search(text);
		end2 = System.nanoTime();
		System.out.println("time for search in KMP..." + (end2 - end));
		System.out.println("time for preprocessing and search in KMP..."+ (end2 - start));

		//--- End of Search phase ---

		//--- Patterns found and the number of hits ---

		Iterator<String> iter = termsThatHit.iterator();
		System.out.println("Hits in KMP: ");
		System.out.println(termsThatHit.toString());
		System.out.println(termsThatHit.size());
	}


	/**
	 * Method to test Aho-Corasick
	 * @param text : Text string that has to be scanned
	 * @param pattern : String of patterns
	 */
	public static void testAhoCorasick(String text,String[] pattern){
		long start = 0L, end = 0L, end2 = 0L;
		AhoCorasick ac=new AhoCorasick(len,pattern);

		//--- Start of pre-processing phase ---
		start = System.nanoTime();
		ac.preprocess();
		end = System.nanoTime();
		System.out.println("time for preprocessing in AC..." + (end - start));

		//--- End of pre-processing phase ---

		//--- Start of Search Phase ---

		HashSet<String> termsThatHit=ac.search(text);
		end2 = System.nanoTime();
		System.out.println("time for search in AC..." + (end2 - end));
		System.out.println("time for preprocessing and search in AC..."+ (end2 - start));

		//--- End of Search phase ---

		//--- Patterns found and the number of hits ---
		Iterator<String> iter = termsThatHit.iterator();
		System.out.println("Hits in AC: ");
		System.out.println(termsThatHit.toString());
		System.out.println(termsThatHit.size());
	}
}
