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
			Node node = new Node(nodes[i], bitspace);
			node.setSuccessor(findsuccessor(nodes[i], nodes));
			nodelist.add(node);
		}

		char ch;
		do {
			System.out.println("\nHashmap Operations\n");
			System.out.println("1. Insert ");
			System.out.println("2. Find");
			System.out.println("3. Delete");
			System.out.println("4. List");

			int choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 1:

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
				int found = -1;
				System.out.println("Enter key to search:");
				int search = scan.nextInt();
				System.out.println("Enter starting node:");
				home = scan.nextInt();
				int nodestart = findsuccessor(home, nodes);
				for (int i = 0; i < nodelist.size(); i++) {
					if (nodelist.get(i).getKey() == nodestart) {
						found = nodelist.get(i).find(search);
						if (found != -1) {
							result = nodelist.get(i).getData(found);
							break;
						}
					}
				}
				if (found == -1) {

				}

				System.out.println("Results: " + result);
				break;
			case 3:

				break;
			case 4:
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

	public static int findsuccessor(int key, int[] array) {
		int successor = -1;
		for (int i = array.length - 1; i >= 0; i--) {
			if (key == array[i]) {
				if (key == array[array.length - 1]) {
					successor = array[0];
				} else {
					successor = array[i + 1];
				}
				break;
			} else if (key > array[array.length - 1]) {
				successor = array[0];
				break;
			} else if (key >= array[i]) {
				successor = array[i+1];
				break;
			}

		}

		return successor;
	}

}
