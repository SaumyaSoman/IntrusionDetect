package edu.rutgers.dsa.boyer;

import java.util.HashSet;

/**
 * 
 * Implemenation of Boyer-Moore Horspool Algorithm
 *
 */
public class BoyerMoore {

	int R = 256; // the radix
	int patternLength;
	String[] pattern;
	int[][] right = null; // the bad-character skip array where pattern information is stored.

	/**
	 * Constructor: Initializes 2D array
	 * @param len- pattern length
	 * @param pattern- String of patterns
	 */
	public BoyerMoore(int len, String[] pattern){
		patternLength=len;
		right = new int[patternLength][R];
		this.pattern=pattern;
		for (int i = 0; i < len; i++)
			for (int c = 0; c < R; c++)
				right[i][c] = -1;
	}

	/**
	 * Pre-processing method-Phase I
	 */
	public void preprocess(){

		// For each pattern, information is stored in the 2D array
		for (int i = 0; i < patternLength; i++) {
			int M = pattern[i].length();
			for (int j = 0; j < M; j++)
				right[i][pattern[i].charAt(j)] = j;
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
		int N = text.length(); // input text length
		int skip = 0;
		HashSet<String> termsThatHit = new HashSet<String>();
		// Start of Search
		int i = patternLength;
		for (int count = i; count > 0; count--) {
			int M = pattern[i - count].length();
			for (int k = 0; k <= N - M; k += skip) {
				skip = 0;
				for (int j = M - 1; j >= 0; j--)
					if (pattern[i - count].charAt(j) != text.charAt(k + j)) {
						skip = j - right[i - count][text.charAt(k + j)];
						if (skip < 1)
							skip = 1;
						break;
					}
				if (skip == 0) {
					k = k + 1;
					termsThatHit.add(pattern[i - count]);
					break;
				}
			}
		}
		return termsThatHit;

	}

}