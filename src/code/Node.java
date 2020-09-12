package code;

import java.util.ArrayList;

public class Node<T> {

	private int key;
	private String ip;
	private int successor;
	private ArrayList<KeyValue<T>> hashArray;
	private int size;
	private KeyValue<T> nonData;

	public Node(int key, int space) {
		this.key = key;
		ip = IpAddressHelper.createRandom();
		size = space;
		hashArray = new ArrayList<KeyValue<T>>(size);

		for (int i = 0; i < size; i++)
			hashArray.add(new KeyValue<>(-1));

		nonData = new KeyValue<T>(-1);
	}

	public double getKey() {
		return key;
	}

	public String getip() {
		return ip;
	}

	public void setSuccessor(int successor) {
		this.successor = successor;
	}

	public int getSuccessor() {
		return successor;
	}

	public boolean add(KeyValue<T> data, int hash) {
		boolean isset = false;

		if (hashArray.get(hash) != null) {
			if (hashArray.get(hash).getKey() != hash) {
				hashArray.set(hash, data);
				isset = true;
				data.setKey(hash);
			}
		}

		return isset;
	}

	public int find(int key) {
		int found = -1;
		for (int i = 0; i < hashArray.size(); i++) {
			if (hashArray.get(i).getKey() == key) {
				return key;
			}
		}
		return found;
	}

	public String getData(int key) {
		for (int i = 0; i < hashArray.size(); i++) {
			if (hashArray.get(i).getKey() == key) {
				return hashArray.get(i).getValue();
			}
		}
		return null;
	}

	public void display() {
		System.out.println("Map of node" + key + ":");

		for (int i = 0; i < size; i++) {
			if (hashArray.get(i).getValue() != null) {
				System.out.print(hashArray.get(i).getKey() + " ");
				System.out.println(hashArray.get(i).getValue());

			}
		}
	}

}
