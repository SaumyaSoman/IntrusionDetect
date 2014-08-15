package edu.rutgers.dsa.ac;

/**
 * 
 * This class represents edges of a Node
 *
 */

class EdgeList{    
	private Connections head;

	/**
	 * Constructor
	 */
	public EdgeList() {
		head = null;
	}

	/**
	 * Get node by character byte
	 * @param b :character byte
	 * @return Node
	 */
	public Node get(byte b) {
		Connections con = head;
		while (con != null) {
			if (con.b == b)
				return con.node;
			con = con.next;
		}
		return null;
	}

	/**
	 * Adds a character to a Node
	 * @param b : character byte
	 * @param node : Node
	 */
	public void put(byte b, Node node) {
		this.head = new Connections(b, node, head);
	}

	/**
	 * @return bytes of characters connected to edges of a node
	 */
	public byte[] keys() {
		int length = 0;
		Connections con = head;
		while (con != null) {
			length++;
			con = con.next;
		}
		byte[] result = new byte[length];
		con = head;
		int j = 0;
		while (con != null) {
			result[j] = con.b;
			j++;
			con = con.next;
		}
		return result;
	}

	/** 
	 * This class represents connections
	 */
	static private class Connections {
		byte b;
		Node node;
		Connections next;

		/**
		 * 
		 * @param b : character value represented by Node
		 * @param node :Node
		 * @param next :Connections
		 */
		public Connections(byte b, Node node, Connections next) {
			this.b = b;
			this.node = node;
			this.next = next;
		}
	}

}
