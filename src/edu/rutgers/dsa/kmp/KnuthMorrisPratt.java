package edu.rutgers.dsa.kmp;

import java.util.HashSet;

/**
 * 
 * Implemenation of Knuth-Morris-Pratt Algorithm
 *
 */
public class KnuthMorrisPratt {

	int R = 256; // the radix
	int patternLength;
	int[][][] dfa = null; // DFA to store information about each pattern
	String[] pattern;

	/**
	 * Constructor: Initializes DFA
	 * @param len- pattern length
	 * @param pattern- String of patterns
	 */
	public KnuthMorrisPratt(int len, String[] pattern){
		patternLength=len;
		this.pattern=pattern;
		dfa= new int[patternLength][][];
		for (int i = 0; i < patternLength; i++) {
			int M = pattern[i].length();
			dfa[i] = new int[R][M];
		}
	}

	/**
	 * Pre-processing method-Phase I
	 */
	public void preprocess(){

		// For each pattern, information is stored in the DFA
		for (int i = 0; i < patternLength; i++) {
			int M = pattern[i].length();
			dfa[i][pattern[i].charAt(0)][0] = 1;
			for (int X = 0, j = 1; j < M; j++) {
				for (int c = 0; c < R; c++)
					dfa[i][c][j] = dfa[i][c][X];
				dfa[i][pattern[i].charAt(j)][j] = j + 1;
				X = dfa[i][pattern[i].charAt(j)][X];
			}
		}
	}

	/**
	 * Method to search an input text for patterns
	 * @param text- the string that has to be searched for patterns
	 * @return patterns found in the text
	 */
	public HashSet<String> search(String text){

		if(text.length()==0){
			return null;
		}
		HashSet<String> termsThatHit = new HashSet<String>();
		int i = patternLength;
		for (int count = i; count > 0; count--) {
			int k, j, N = text.length();
			int P = pattern[i - count].length();
			for (k = 0, j = 0; k < N && j < P; k++)
				j = dfa[i - count][text.charAt(k)][j];
			if (j == P) {
				k = k - P + 1;
				termsThatHit.add(pattern[i - count]);
			} 
		}
		return termsThatHit;
	}

}