package code;
import java.util.ArrayList;

public class Hashmap<T> {
	private ArrayList<KeyValue<T>> hashArray;
	private int size;
	private KeyValue<T> nonData;

	public Hashmap(int size) {
		this.size = size;
		hashArray = new ArrayList<KeyValue<T>>(size);

		for (int i = 0; i < size; i++)
			hashArray.add(new KeyValue<>(-1));

		nonData = new KeyValue<T>(-1);
	}

	public int hash(int key) {
		return key % size;
	}

	public int hash2(int key) {
		return 5 - (key % 5);
	}

	public void add(KeyValue<T> data) {
		int key = data.getKey();
		int hash = hash(key);
		int count = 0;
		boolean isset = false;

		while (isset == false) {
			// if no spots located after 5 attempts switch to linear probing 
			if (count > 5) {
				if (hashArray.get(hash) != null) {
					if (hashArray.get(hash).getKey() == hash) {
						hash++;
					} else {
						hashArray.set(hash, data);
						isset = true;
						data.setKey(hash);
					}
				}
			// attempt double hashing to resolve collision
			} else {
				if (hashArray.get(hash) != null) {
					if (hashArray.get(hash).getKey() == hash) {
						hash = hash2(hash);
					} else {
						hashArray.set(hash, data);
						isset = true;
						data.setKey(hash);
					}
				}
			}
			count++;
		}
	}

	public KeyValue<T> find(int key) {
		int hash = hash(key);
		KeyValue<T> hash1 = null;
		while (hashArray.get(hash) != null) {
			if (hashArray.get(hash).getKey() == key) {
				hash1 = hashArray.get(hash);
			}

			hash++;
			hash %= size;
		}

		return hash1;

	}

	public void delete(int key) {
		int hash = hash(key);

		while (hashArray.get(hash) != null) {
			if (hashArray.get(hash).getKey() == key) {
				hashArray.set(hash, nonData);
			}

			hash++;
			hash %= size;
		}

	}

	public void display() {
		System.out.println("Map");

		for (int i = 0; i < size; i++) {
			if (hashArray.get(i).getValue() != null) {
				System.out.print(hashArray.get(i).getKey()+" ");
				System.out.println(hashArray.get(i).getValue());
			}else {
				System.out.println("* *");
				
			}
		}
	}
}