package code;

import java.util.*;

public class Chord {

	public static void main(String[] args) {

		System.out.println("Initalizing Hashmap");
		Scanner scan = new Scanner(System.in);
		int size = (int) Math.round(Math.pow(2, 9));
		int[] nodes = { 0, 85, 133, 182, 210, 245, 279, 324, 395, 451 };

		Hashmap<String> hashMap = new Hashmap<>(size);
		System.out.println("Hashmap of size " + size + " created.");
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
				hashMap.add(new KeyValue<String>(key, input));
				break;
			case 2:
				System.out.println("Enter key to search:");
				int search = scan.nextInt();
				KeyValue<String> result = hashMap.find(search);
				System.out.println("Results: " + result);
				break;
			case 3:
				System.out.println("Enter key to delete:");
				int del = scan.nextInt();
				hashMap.delete(del);
				break;
			case 4:
				hashMap.display();
				break;
			default:
				System.out.println("Wrong Entry \n ");
				break;
			}

			System.out.println("\n\nDo you want to continue y/n? \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');

	}

}
