package code;

import java.util.*;

public class Chord {

	public static void main(String[] args) {
		int bitspace;
		int succ2;
		int home;
		String result = "";
		System.out.println("Initalizing Hashmap");
		Scanner scan = new Scanner(System.in);
		bitspace = (int) Math.pow(2, 9);
		int[] nodes = { 0, 85, 133, 182, 210, 245, 279, 324, 395, 451 };
		ArrayList<Node> nodelist = new ArrayList<Node>();

		for (int i = 0; i < nodes.length; i++) {
			//each node manages its own hashtable
			Node node = new Node(nodes[i], bitspace);
			node.setSuccessor(findsuccessor(nodes[i], nodes));
			nodelist.add(node);
		}

		char ch;
		do {
			System.out.println("\nHashmap Operations\n");
			System.out.println("1. Insert ");
			System.out.println("2. Find");
			System.out.println("3. List");

			int choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 1:

				//main method handles hashing of each value as rehashing the value might store it in another hashtable
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
				//search function
				int found = -1;
				int nodestart = 0;
				System.out.println("Enter key to search:");
				int search = scan.nextInt();
				System.out.println("Enter starting node:");
				home = scan.nextInt();
				//finds search initiator and successor node
				for (int i = 0; i < nodes.length; i++) {
					if (home == nodes[i]) {
						nodestart = nodes[i];
						break;
					} else {
						nodestart = findsuccessor(home, nodes);
					}
				}
				int count = 0;
				//searches successor node for file. if not found gets successor node of that node and repeats process 
				//until all nodes in the network have been searched.
				do {
					for (int i = 0; i < nodelist.size(); i++) {
						//gets appropriate node
						if (nodelist.get(i).getKey() == nodestart) {
							count++;
							//searches node hashtable for key
							found = nodelist.get(i).find(search);
							//if found retrieve
							if (found != -1) {
								result = nodelist.get(i).getData(found);
								System.out.println("Search results for node " + home +": " + result + " in node " + nodelist.get(i).getKey());
								break;
							} else {
								//otherwise move to next node and repeat until all nodes searched
								nodestart = findsuccessor(nodestart, nodes);
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
			default:
				System.out.println("Wrong Entry \n ");
				break;
			}

			System.out.println("\n\nDo you want to continue y/n? \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');

	}

	public static int hash(int key, int bitspace) {
		return key % bitspace;
	}

	public static int hash2(int key) {
		return 5 - (key % 5);
	}

	//function to find the successor node of any node/machine in the network
	public static int findsuccessor(int key, int[] array) {
		int successor = -1;
		for (int i = array.length - 1; i >= 0; i--) {
			if (key > array[array.length - 1]) {
				successor = array[0];
				break;
			} else if (key >= array[i]) {
				if (key == array[array.length - 1]) {
					successor = array[0];
				} else {
					successor = array[i + 1];
					break;
				}
			}

		}

		return successor;
	}

}
