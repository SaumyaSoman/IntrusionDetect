package edu.rutgers.dsa.ac;

import java.util.HashSet;

/**
 * A node represents an element in the Aho-Corasick tree.
 */

class Node {

	int depth; //depth of the tree
	private EdgeList edgeList; // edges connected to this node
	private Node fail; // fail transition node
	private HashSet<String> outputs; // set of outputs associated with this node.

	/**
	 * Constructor
	 * @param depth
	 */
	public Node(int depth) {
		this.depth = depth;
		this.edgeList = new EdgeList();
		this.fail = null;
		this.outputs = new HashSet<String>();
	}
	/**
	 * Adds child to node
	 * @param b
	 * @return root node
	 */
	public Node extend(byte b) {
		if (this.edgeList.get(b) != null)
			return this.edgeList.get(b);
		Node nextState = new Node(this.depth + 1);
		this.edgeList.put(b, nextState);
		return nextState;
	}

	/**
	 * Add child nodes to a node
	 * @param bytes
	 * @return root node
	 */
	public Node extendAll(byte[] bytes) {
		Node state = this;
		for (int i = 0; i < bytes.length; i++) {
			if (state.edgeList.get(bytes[i]) != null)
				state = state.edgeList.get(bytes[i]);
			else
				state = state.extend(bytes[i]);
		}
		return state;
	}

	/**
	 * This method gives size of the tree rooted at this node.
	 * @return size
	 */
	public int size() {
		byte[] keys = edgeList.keys();
		int result = 1;
		for (int i = 0; i < keys.length; i++)
			result += edgeList.get(keys[i]).size();
		return result;
	}

	/**
	 * Get a Node by character byte
	 * @param b :character byte
	 * @return Node
	 */
	public Node get(byte b) {
		return this.edgeList.get(b);
	}

	/**
	 * Adds a character to a Node
	 * @param b : character byte
	 * @param node : Node
	 */
	public void put(byte b, Node node) {
		this.edgeList.put(b, node);
	}
	/**
	 * 
	 * @return bytes of characters connected to edges of a node
	 */
	public byte[] keys() {
		return this.edgeList.keys();
	}
	/**
	 * Get failure tranistion node
	 * @return Node
	 */
	public Node getFail() {
		return this.fail;
	}

	/**
	 * Method to set failure transition node
	 * @param fail : failure transition node
	 */
	public void setFail(Node fail) {
		this.fail = fail;
	}

	/**
	 * Add output to set of outputs
	 * @param output
	 */
	public void addOutput(String output) {
		this.outputs.add(output);
	}

	/**
	 *  Return set of patterns matched
	 * @return set of patterns matched
	 */
	public HashSet<String> getOutputs() {
		return this.outputs;
	}


}
