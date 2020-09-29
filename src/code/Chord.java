package code;

import java.util.*;

public class Chord {

	private static final int bitspace = 512;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {

		int home;
		String result = "";
		System.out.println("Initalizing Hashmap");
		Scanner scan = new Scanner(System.in);
		int[] nodes = { 0, 85, 133, 182, 210, 245, 279, 324, 395, 451 };
		ArrayList<Node> nodelist = new ArrayList<Node>();

		for (int i = 0; i < nodes.length; i++) {
			// each node manages its own hashtable
			Node<String> node = new Node<String>(nodes[i], bitspace);
			node.setSuccessor(findsuccessor(nodes[i], nodes));
			node.setPredecessor(findPred(nodes[i], nodes));
			nodelist.add(node);
		}

		for (int i = 0; i < nodelist.size(); i++) {
			nodelist.get(i).createFingerTable(9, nodes);
		}

		char ch;
		do {
			System.out.println("\nHashmap Operations\n");
			System.out.println("1. Insert ");
			System.out.println("2. Find");
			System.out.println("3. List");
			System.out.println("4. List Finger Tables");
			System.out.println("5. Delete Item");
			System.out.println("6. Insert Node");
			System.out.println("7. Delete Node");

			int choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 1:

				// main method handles hashing of each value as rehashing the value might store
				// it in another hashtable
				System.out.println("Enter element to insert:");
				String input = scan.nextLine();
				System.out.println("Enter key:");
				int key = scan.nextInt();
				boolean isset = false;

				int hash = hash(key, bitspace);
				int successor = findsuccessor(hash, nodes);
				for (int i = 0; i < nodelist.size(); i++) {
					if (nodelist.get(i).getKey() == successor) {
						isset = nodelist.get(i).add(new KeyValue<String>(key, input), hash);
						break;
					}
				}

				while (isset == false) {
					int count = 0;
					if (count < 5) {
						hash = hash2(key);
						successor = findsuccessor(hash, nodes);
						for (int i = 0; i < nodelist.size(); i++) {
							if (nodelist.get(i).getKey() == successor) {
								isset = nodelist.get(i).add(new KeyValue<String>(key, input), hash);
								break;
							}
						}
					} else {
						hash = hash++;
						successor = findsuccessor(hash, nodes);
						for (int i = 0; i < nodelist.size(); i++) {
							if (nodelist.get(i).getKey() == successor) {
								isset = nodelist.get(i).add(new KeyValue<String>(key, input), hash);
								break;
							}
						}
					}
					count++;
				}
				break;
			case 2:
				// search function
				int found = -1;
				int nodestart = 0;
				System.out.println("Enter key to search:");
				int search = scan.nextInt();
				System.out.println("Enter starting node:");
				home = scan.nextInt();
				// finds search initiator and successor node
				for (int i = 0; i < nodes.length; i++) {
					if (home == nodes[i]) {
						nodestart = nodes[i];
						break;
					} else {
						nodestart = findsuccessor(home, nodes);
					}
				}
				int count = 0;
				// searches successor node for file. if not found gets successor node of that
				// node and repeats process
				// until all nodes in the network have been searched.
				do {

					count++;
					for (int i = 0; i < nodelist.size(); i++) {

						// gets appropriate node
						if (nodelist.get(i).getKey() == nodestart) {
							// searches node hashtable for key
							found = nodelist.get(i).find(search);
							// if found retrieve
							if (found != -1) {
								result = nodelist.get(i).getData(found);
								System.out.println("Search results for node " + home + ": " + result + " in node "
										+ nodelist.get(i).getKey());
								break;
							} else {
								for (int x = 0; x < nodelist.size(); x++) {

									if (nodelist.get(i).getPredecessor() == nodelist.get(x).getKey()) {
										System.out.println("Recursing to node" + nodelist.get(x).getKey());
										found = nodelist.get(x).find(search);
										if (found != -1) {
											result = nodelist.get(x).getData(found);
											System.out.println("Search results for node " + home + ": " + result
													+ " in node " + nodelist.get(x).getKey());
											break;
										}

									}

								}
								if (found != -1) {
									break;
								}
								nodestart = nodelist.get(i).searchFinger(search);
								System.out.println("Hopping to Node: " + nodestart);
							}
						}
					}
				} while (count < nodelist.size() && found == -1);

				if (found == -1) {
					System.out.println("The requested file could not be found");
				}
				break;
			case 3:
				for (int i = 0; i < nodelist.size(); i++) {
					nodelist.get(i).display();
				}

				break;

			case 4:
				for (int i = 0; i < nodelist.size(); i++) {
					nodelist.get(i).printFingerTable();
				}
				break;

			case 5:
				System.out.println("Enter Key to Delete");
				int delete = scan.nextInt();
				int nodestart2 = 0;
				int count2 = 0;
				int found2 = -1;
				do {
					for (int i = 0; i < nodelist.size(); i++) {
						// gets appropriate node
						if (nodelist.get(i).getKey() == nodestart2) {
							count2++;
							// searches node hashtable for key
							found2 = nodelist.get(i).find(delete);
							// if found retrieve
							if (found2 != -1) {
								result = nodelist.get(i).getData(found2);
								nodelist.get(i).delete(delete);
								System.out.println("Deleting Entry " + delete + " in node: " + nodestart2);
								break;
							} else {
								// search predecessor node
								for (int x = 0; x < nodelist.size(); x++) {

									if (nodelist.get(i).getPredecessor() == nodelist.get(x).getKey()) {
										System.out.println("Recursing to node" + nodelist.get(x).getKey());
										found2 = nodelist.get(x).find(delete);
										if (found2 != -1) {
											result = nodelist.get(x).getData(found2);
											System.out.println("Deleting Entry " + delete + " in node: "
													+ nodelist.get(x).getKey());
											break;
										}

									}

								}
								if (found2 != -1) {
									break;
								}
								// move to new node according to finger table and repeat process
								nodestart2 = nodelist.get(i).searchFinger(delete);
								System.out.println("Hopping to Node: " + nodestart2);
							}
						}
					}
				} while (count2 < nodelist.size() && found2 == -1);

				if (found2 == -1) {
					System.out.println("The requested file could not be found");
				}
				break;

			case 6:
				System.out.println("Enter Node to Insert");
				int newNode = scan.nextInt();
				// updates node array
				nodes = arrAppend(newNode, nodes);
				// updates node ArrayList
				nodelist = addNode(newNode, nodelist, nodes);

				// rebuild finger tables
				for (int i = 0; i < nodelist.size(); i++) {
					nodelist.get(i).createFingerTable(9, nodes);
				}
				break;

			case 7:
				System.out.println("Enter Node to Delete");
				int delnode = scan.nextInt();
				boolean nodefound = false;
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i] == delnode) {
						nodefound = true;
					}
				}

				if (nodefound == false) {
					System.out.println("No Matching Node was Found");
				} else {
					arrDel(nodes, delnode);

					deleteNode(delnode, nodelist, nodes);
					
					for (int i = 0; i < nodelist.size(); i++) {
						nodelist.get(i).createFingerTable(9, nodes);
					}
				}

				break;
			default:
				System.out.println("Wrong Entry \n ");
				break;
			}

			System.out.println("\n\nDo you want to continue y/n? \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');
		scan.close();
	}

	public static int hash(int key, int bitspace) {
		return key % bitspace;
	}

	public static int hash2(int key) {
		return 5 - (key % 5);
	}

	// function to find the successor node of any node/machine in the network
	public static int findsuccessor(int key, int[] array) {
		int successor = -1;
		boolean suc = false;
		do {

			if (key > bitspace) {
				key = key - bitspace;
				System.out.println("KEY:" + key);
			}

			for (int i = array.length - 1; i >= 0; i--) {
				if (key > array[array.length - 1]) {
					key = key - bitspace;
					System.out.println("KEY1:" + key);
				} else if (key >= array[i]) {
					if (key == array[array.length - 1]) {
						successor = array[0];
						suc = true;
					} else {
						successor = array[i + 1];
						suc = true;
						break;
					}
				}
			}
		} while (suc == false);

		return successor;
	}

	public static int findPred(int key, int[] array) {
		int pred = -1;
		if (key == array[0]) {
			pred = array[array.length - 1];
		} else {
			for (int i = 0; i < array.length; i++) {
				if (key > array[i]) {
					pred = array[i];
				}
			}
		}
		return pred;
	}

	public static int fingersuccessor(int key, int[] array) {
		int successor = -1;
		boolean suc = false;
		do {
			if (key > bitspace) {
				key = key - bitspace;
			}
			if (key <= bitspace) {
				for (int i = array.length - 1; i >= 0; i--) {
					if (key >= array[i]) {
						if (key >= array[array.length - 1]) {
							successor = array[0];
							suc = true;
						} else if (key == array[i]) {
							successor = array[i];
							suc = true;
						} else {
							successor = array[i + 1];
							suc = true;
							break;
						}
					}
				}
			}
		} while (suc == false);
		return successor;
	}

	public static int[] sortAscending(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	public static int[] arrAppend(int add, int[] arr) {
		int size = arr.length;
		int[] newArr;
		newArr = new int[size + 1];
		for (int i = 0; i < size; i++) {
			newArr[i] = arr[i];
		}

		newArr[size] = add;

		newArr = sortAscending(newArr);
		return newArr;
	}

	public static int[] arrDel(int[] arr, int remove) {
		int index = -1;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == remove) {
				index = i;
				break;
			}
		}
		if (arr == null || index < 0 || index >= arr.length) {
			return arr;
		}
		int[] anotherArray = new int[arr.length - 1];
		for (int i = 0, k = 0; i < arr.length; i++) {
			if (i == index) {
				continue;
			}
			anotherArray[k++] = arr[i];
		}
		return anotherArray;
	}

	@SuppressWarnings("rawtypes")
	public static <T> ArrayList<Node> addNode(int node, ArrayList<Node> nodelist, int[] nodes) {
		int succ = findsuccessor(node, nodes);
		Node<String> temp = new Node<String>(1, bitspace);

		// captures all data to be transferred to new node
		for (int i = 0; i < nodelist.size(); i++) {
			if (nodelist.get(i).getKey() == succ) {
				for (int j = 0; j < nodelist.get(i).getTableSize(); j++) {
					if (nodelist.get(i).find(j) != -1) {
						if (nodelist.get(i).find(j) <= node) {
							temp.add(new KeyValue<String>(j, nodelist.get(i).getData(j)), j);
							System.out.println(nodelist.get(i).getData(j));
							nodelist.get(i).delete(j);
						}
					}
				}
			}
		}
		// creates new node
		Node<String> newnode = new Node<String>(node, bitspace);

		// adds data to new node
		for (int i = 0; i < bitspace; i++) {
			if (temp.find(i) != -1) {
				newnode.add(new KeyValue<String>(i, temp.getData(i)), i);
			}
		}
		nodelist.add(newnode);
		return nodelist;
	}

	@SuppressWarnings("unchecked")
	public static void deleteNode(int node, @SuppressWarnings("rawtypes") ArrayList<Node> nodelist, int[] nodes) {
		int succ = findsuccessor(node, nodes);
		Node<String> temp = new Node<String>(1, bitspace);
		//create temp storage for orphaned hashtable;
		for (int i =0; i<nodelist.size(); i++) {
			if (nodelist.get(i).getKey() == node) {
				for (int j = 0; j<bitspace; j++) {
					if (nodelist.get(i).find(j)!=-1) {
						temp.add(new KeyValue<String>(j, nodelist.get(i).getData(j)), j);
					}
				}
			}
		}
		//update successor node with new hashtable data
		for (int i = 0; i < bitspace; i++) {
			if (temp.find(i) != -1) {
				for (int j =0; j<nodelist.size(); j++) {
					if (nodelist.get(j).getKey() == succ) {
						nodelist.get(j).add(new KeyValue<String>(i, temp.getData(i)), i);
					}
				}
			}
		}
		//remove node from network
		for (int i =0; i<nodelist.size(); i++) {
			if(nodelist.get(i).getKey() == node) {
				nodelist.remove(i);
			}
		}
	}
}
