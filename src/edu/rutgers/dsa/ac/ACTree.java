package edu.rutgers.dsa.ac;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is the root of the Aho-Corasick automata
 *
 */
public class ACTree {

	private Node root;
	private boolean prepared; // to check if trie is converted to automata
	HashSet<String> termsthatHit=new HashSet<String>();

	public ACTree() {
		this.root = new Node(0);
		this.prepared = false;
	}

	/**
	 * This methods adds the given pattern to the trie. During phase II i.e.
	 * search, if pattern is matched, output will be one of the yielded
	 * elements in LastState.getOutputs()  * 
	 * @param pattern : pattern to be added to the trie
	 * @param output
	 */
	public void add(byte[] pattern, String output) {
		if (this.prepared)
			throw new IllegalStateException("can't add patterns after prepare() is called");
		Node lastState = this.root.extendAll(pattern);
		lastState.addOutput(output);
	}



	/**
    Converts the trie to an automaton for searching. Before phase II(search) this must be called
	 */
	public void prepare() {
		this.setFailTransitions();
		this.prepared = true;
	}



	/**
	 * This method searches for patterns in the text bytes.
	 * @param bytes : bytes of the text that has to be searchd for patterns
	 * @return : Set of patterns found in the text
	 */

	public HashSet<String> search(byte[] bytes) {
		for (Iterator iter = this.startSearch(bytes); iter!=null && iter.hasNext(); ) {
			LastState result = (LastState) iter.next();
			termsthatHit.addAll(result.getOutputs());
		}
		return termsthatHit;
	}

	/**
	 *  Method to initialize failure transitions of all nodes(states) except
	 *  for the root. BFS is used t traverse through the states.
	 */
	private void setFailTransitions() {
		Queue<Node> q=new LinkedList<Node>();
		for(int i = 0; i < 256; i++)
			if (this.root.get((byte) i) != null) {
				this.root.get((byte) i).setFail(this.root);
				q.add(this.root.get((byte) i));
			}
		this.prepareRoot();
		while (!q.isEmpty()) {
			Node state = q.poll();
			byte[] keys = state.keys();
			for (int i = 0; i < keys.length; i++) {
				Node r = state;
				byte a = keys[i];		
				Node s = r.get(a);
				q.add(s);
				r = r.getFail();		
				while (r.get(a) == null)
					r = r.getFail();
				s.setFail(r.get(a));
				s.getOutputs().addAll(r.get(a).getOutputs());
			}
		}
	}

	/**
	 * If no transition yet exists at this point, this method sets all 
	 * the out transitions of the root to itself  
	 */
	private void prepareRoot() {
		for(int i = 0; i < 256; i++){
			if (this.root.get((byte) i) == null)
				this.root.put((byte) i, this.root);
		}

	}

	/**
	 * Returns root of the trie
	 * @return root of the trie
	 */
	Node getRoot() {
		return this.root;
	}

	/**
	 * To start a new search
	 * @param bytes : text bytes to be searched
	 * @return last search state
	 */
	LastState startSearch(byte[] bytes) {
		if (!this.prepared){
			throw new IllegalStateException("call prepare() before search");
		}else{
			LastState ls=continueSearch(new LastState(this.root, bytes, 0));
			if(ls!=null && ls.getOutputs()!=null){
				termsthatHit.addAll(ls.getOutputs());
				ls.setTree(this);
				ls.setCurrentResult(ls);
			}
			
			return ls;
		}	    

	}

	/**
	 * To continue search
	 * @param lastResult: last search state
	 * @return last search state
	 */

	LastState continueSearch(LastState lastResult) {
		byte[] bytes = lastResult.bytes;
		Node state = lastResult.lastMatchedState;
		for (int i = lastResult.lastIndex; i < bytes.length; i++) {
			byte b = bytes[i];
			while (state.get(b) == null)
				state = state.getFail();
			state = state.get(b);
			if (state.getOutputs().size() > 0){
				LastState ls=new LastState(state, bytes, i+1);
				termsthatHit.addAll(ls.getOutputs());
				return ls;
			}

		}
		return null;
	}



}

