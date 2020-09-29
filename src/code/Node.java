package code;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class Node<T> {

	private int key;
	private String ip;
	private int successor;
	private ArrayList<KeyValue<T>> hashArray;
	private int size;
	@SuppressWarnings("unused")
	private KeyValue<T> nonData;
	private ArrayList<Finger> fingerTable;
	private int predecessor;

	public Node(int key, int space) {
		this.key = key;
		ip = IpAddressHelper.createRandom();
		size = space;
		hashArray = new ArrayList<KeyValue<T>>(size);

		for (int i = 0; i < size; i++)
			hashArray.add(new KeyValue<>(-1));

		nonData = new KeyValue<T>(-1);
	}

	public void createFingerTable(int size, int[] nodes) {
		size = size - 1;
		fingerTable = new ArrayList<Finger>(size);
		for (int i = 0; i < size + 1; i++) {
			Finger finger = new Finger((int) Chord.fingersuccessor((int) (key + Math.pow(2, i)), nodes));
			fingerTable.add(finger);
		}
	}

	public void printFingerTable() {
		System.out.println("Finger Table for Node: " + key);
		for (int i = 0; i < fingerTable.size(); i++) {
			System.out.println(fingerTable.get(i).getKey());
		}
	}

	public int searchFinger(int search) {
		int[] nodes = new int[fingerTable.size()];
		for (int i = 0; i < fingerTable.size(); i++) {
			nodes[i] = fingerTable.get(i).getKey();
		}

		nodes = Chord.sortAscending(nodes);
		nodes = IntStream.of(nodes).distinct().toArray();

		if (search <= nodes[0]) {
			return nodes[0];
		} else if (search >= nodes[nodes.length - 1]) {
			return nodes[nodes.length - 1];
		} else {
			for (int i = 0; i < nodes.length - 1; i++) {
				if (search > nodes[i] && search < nodes[i + 1]) {
					return nodes[i + 1];
				}
			}
		}
		return -1;
	}

	public int getKey() {
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

	public void setPredecessor(int key) {
		this.predecessor = key;
	}
	
	public int getPredecessor() {
		return predecessor;
	}
	
	public int getTableSize() {
		return size;
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

	public void delete(int key) {
		for (int i = 0; i < hashArray.size(); i++) {
			if (hashArray.get(i).getKey() == key) {
				hashArray.set(key, nonData);
			}
		}
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

	public void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(0);

			Socket connection = null;

			try {
				connection = serverSocket.accept();
				Writer writer = new OutputStreamWriter(connection.getOutputStream());

				String now = "Connection Established with node " + key + " on Port: " + serverSocket.getLocalPort();

				writer.write(now.toString() + "\r\n");
				writer.flush();
				connection.close();
			} catch (IOException e) {
				System.out.println("An error has occurred with the server operations");
			} finally {
				try {
					if (connection != null)
						connection.close();
				} catch (IOException e) {
				}

				serverSocket.close();
			}
		} catch (IOException e) {
			System.out.println("An error has occurred with the server socket");
		}
	}

	public void clientConnect() {

	}
}
