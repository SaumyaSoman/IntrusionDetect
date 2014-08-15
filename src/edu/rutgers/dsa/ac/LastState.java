package edu.rutgers.dsa.ac;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class holds the last search state. It has the outputs where
 * the search finished and the last index of the matching.
 * It implements Iterator to return a list of search matches
 */
public class LastState implements Iterator {
    Node lastMatchedState;
    byte[] bytes;
    int lastIndex;
    
    ACTree tree=new ACTree();
    private LastState currentResult;
    
    /**
     * Constructor
     * @param s  : Node
     * @param bs : character bytes
     * @param i  : last index
     */
    LastState(Node s, byte[] bs, int i) {
	this.lastMatchedState = s;
	this.bytes = bs;
	this.lastIndex = i;
    }
    
    /**
	 * @param tree the tree to set
	 */
	public void setTree(ACTree tree) {
		this.tree = tree;
	}

	/**
	 * @param currentResult the currentResult to set
	 */
	public void setCurrentResult(LastState currentResult) {
		this.currentResult = currentResult;
	}

	/**
	 * 
	 * @return  a list of the outputs of this match.
	 */
    public HashSet<String> getOutputs() {
	return lastMatchedState.getOutputs();
    }

    /**
     * 
     * @return the index where search terminates
     */
    public int getLastIndex() {
	return lastIndex;
    }


	@Override
	public boolean hasNext() {
		return (this.currentResult != null);
	}


	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if (!hasNext())
		    throw new NoSuchElementException();
		Object result = this.currentResult;
		this.currentResult = tree.continueSearch(currentResult);
		return result;	}


	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
