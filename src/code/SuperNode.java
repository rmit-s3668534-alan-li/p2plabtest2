package code;

public class SuperNode {

	private int key;
	private String ip; 
	
	public SuperNode(int key, double bitspace) {
		this.key = key;
		ip = IpAddressHelper.createRandom();
	}
	
	public double getKey() {
		return key;
	}
	
	public String getip() {
		return ip;
	}

}
