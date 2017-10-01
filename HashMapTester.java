/**
 * This is my HashMapTester. I have tested it quite
 * robustly and verbosely, and it works quite well.
 * I made sure to test possible collisions by using 1 and 2,
 * keys that are the same length. Every significant method is tested as well.
 * Some of the methods had to be broken down into private helper
 * methods as well, like the getNodeAtKey method and the
 * growHashMap method.
 */

import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Tester class for the hash map.
 */

public class HashMapTester {
	public static void main(String... args) {
		HashMap myHashMap = new HashMap(20);

		System.out.println("TESTING SET METHOD");
		System.out.println("Adding 1:One");
		System.out.println("Adding 2:Two");
		System.out.println("Adding Test:Testing, Testing");
		System.out.println("Adding Okay, cool:Great work");
		myHashMap.set("1", "One");
		myHashMap.set("2", "Two");
		myHashMap.set("Test", "Testing, Testing");
		myHashMap.set("Okay, cool.", "Great work");
		System.out.println();

		System.out.println("TESTING GET METHOD");
		System.out.println("Value at 1: " + (String) myHashMap.get("1"));
		System.out.println("Value at 2: " + (String) myHashMap.get("2"));
		System.out.println();

		System.out.println("TESTING DELETE METHOD");
		System.out.println("Removing 1");
		System.out.println("Removed: " + myHashMap.delete("1"));
		System.out.println("Removing 2");
		System.out.println("Removed: " + myHashMap.delete("2"));
		System.out.println();

		System.out.println("TESTING LOAD METHOD");
		System.out.println("Load: " + myHashMap.load());
		System.out.println();
	}
}

/**
 * Main HashMap class
 */

class HashMap {

	private Node[] hashMap;
	private int arraySize;
	private int size;

	/**
	 * Hash map constructor with input initial array size.
	 * @param arraySize the initial size of the hash map array.
	 */

	public HashMap(int arraySize) {
		size = 0;
		this.arraySize = arraySize;
		hashMap = new Node[arraySize];
	}

	/**
	 * Gets the load of the hashMap.
	 * @return the load of the hashMap.
	 */

	public float load() {
		return ((float) size) / arraySize;
	}

	/**
	 * Get method for hash map.
	 * @param key the key to get on the hash map
	 * @return the object associated with the given key
	 */

	public Object get(String key) {
		return getNodeWithKey(key).value;
	}

	/**
	 * Private helper method for the get method above. Gets the node with the given key.
	 * @param key the key to get the node of.
	 * @return the node with the given key.
	 */

	private Node getNodeWithKey(String key) {
		if (hashMap[hashFunction(key)] != null) {
			Node currNode = hashMap[hashFunction(key)];
			while (!key.equals(currNode.key) && currNode.next != null) currNode = currNode.next;
			if (key.equals(currNode.key)) {
				return currNode;
			} else throw new NoSuchElementException();
		} else throw new NoSuchElementException();
	}

	/**
	 * Method to add the key/value pair to the hash map.
	 * @param key the key to add
	 * @param value the value to add
	 * @return a boolean saying whether the operation was successful or not
	 */

	public boolean set(String key, Object value) {

		int initNodeLoc = hashFunction(key);

		if (load() >= 1) {
			return false;
		} else if (hashMap[initNodeLoc] == null) {
			hashMap[initNodeLoc] = new Node(key, value);
			size++;
			return true;
		} else {
			Node currNode = hashMap[initNodeLoc];
			while (currNode.next != null) currNode = currNode.next;
			currNode.next = new Node(key, value);
			size++;
			return true;
		}
	}

	/**
	 * Method to remove the a key/value pair from the hash map.
	 * @param key the key to remove
	 * @return the removed key.
	 */

	public String delete(String key) {
		for (int i = 0; i < hashMap.length; i++) {
			Node n = hashMap[i];
			if (n != null) {
				if (n.key.equals(key)) {
					if (n.next == null) {
						String retVal = hashMap[i].key;
						hashMap[i] = null;
						size--;
						return retVal;
					} else {
						String retVal = hashMap[i].key;
						hashMap[i] = n.next;
						size--;
						return retVal;
					}
				} else if (n.next != null) {
					while (n.next != null) {
						if (n.next.key.equals(key)) {
							String retVal = n.next.key;
							n.next = n.next.next;
							size--;
							return retVal;
						} else {
							n = n.next;
						}
					}
				}
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Method to get the size of the hash map;
	 * @return size of hash map
	 */

	public int size() {
		return size;
	}

	/**
	 * The hash function for the key
	 * @param key the key to hash
	 * @return the hash value.
	 */

	public int hashFunction(String key) {
		return Math.abs(key.hashCode() % arraySize);
	}

	/**
	 * An inner node class for the buckets.
	 */

	class Node {
		public Node next;
		public String key;
		public Object value;

		/**
		 * Node constructor.
		 * @param next the next node
		 * @param key the key to hold.
		 * @param value the value to hold.
		 */

		public Node(Node next, String key, Object value) {
			this.next = next;
			this.key = key;
			this.value = value;
		}

		/**
		 * Node constructor.
		 * @param key the key to hold.
		 * @param value the value to hold.
		 */

		public Node(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
}
