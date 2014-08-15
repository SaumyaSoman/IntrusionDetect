package edu.rutgers.dsa.ac;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Implementation of Aho-Corasick multipattern string matching algorithm
 */

public class AhoCorasick {

	int patternLength;
	ACTree tree;
	String[] pattern;


	/**
	 * Constructor
	 * @param len- pattern length
	 * @param pattern- String of patterns
	 */
	public AhoCorasick(int len, String[] pattern) {
		tree=new ACTree();
		this.patternLength=len;
		this.pattern=pattern;

	}

	/**
	 * Pre-processing method-Phase I
	 */
	public void preprocess(){

		// Adds all patterns to tree
		for (int i = 0; i < pattern.length; i++) {
			tree.add(pattern[i].getBytes(), pattern[i]);
		}

		// Converts trie into automata by adding failure and output functions
		tree.prepare();
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
		return tree.search(text.getBytes());
	}

}

