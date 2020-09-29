package code;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IP_Address {
	public static void main(String[] args) {
		lookupByName();
		lookupByIP();
		lookupAllByName();
		findHostName();
		findLocalHostName();
		listNetworkInterfaces();
		getNetworkInterface("eth1");
		getNetworkAddress("localhost");
	}

	public static void lookupByName() {
		try {
			InetAddress address = InetAddress.getByName("www.google.com");
			System.out.println("--------------------------");
			System.out.println("lookupByName");
			System.out.println(address);
		} catch (UnknownHostException e) {
			System.out.println("Could not find www.google.com");
		}
	}

	public static void lookupByIP() {
		try {
			InetAddress address = InetAddress.getByName("216.58.196.132"); // Google address
			System.out.println("--------------------------");
			System.out.println("lookupByIP");
			System.out.println(address);
		} catch (UnknownHostException e) {
			System.out.println("Could not find 216.58.196.132");
		}
	}

	public static void lookupAllByName() {
		try {
			System.out.println("--------------------------");
			System.out.println("lookupAllByName");
			InetAddress[] addresses = InetAddress.getAllByName("microsoft.com");

			for (InetAddress address : addresses)
				System.out.println(address);
		} catch (UnknownHostException e) {
			System.out.println("Unable to find any address from microsoft.com");
		}
	}

	public static void findHostName() {
		try {
			System.out.println("--------------------------");
			System.out.println("findHostName");
			InetAddress inetAddress = InetAddress.getByName("13.239.94.229"); // An AWS address
			System.out.println(inetAddress.getHostName());
		} catch (UnknownHostException e) {
			System.out.println("13.239.94.229");
		}
	}

	public static void findLocalHostName() {
		try {
			System.out.println("--------------------------");
			System.out.println("findLocalHostName");
			InetAddress inetAddress = InetAddress.getLocalHost(); // Local address
			System.out.println(inetAddress.getHostName());
		} catch (UnknownHostException e) {
			System.out.println("Could not find local host address");
		}
	}

	public static void listNetworkInterfaces() {
		try {
			System.out.println("--------------------------");
			System.out.println("listNetworkInterfaces");
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
				System.out.println(networkInterface);
			}
		} catch (SocketException e) {
			System.out.println("Could not access network Interfaces");
		}
	}

	public static void getNetworkInterface(String interfaceName) {
		try {
			System.out.println("--------------------------");
			System.out.println("getNetworkInterface");
			NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);

			if (networkInterface == null)
				System.out.println("No such interface: " + interfaceName);
			else
				System.out.println("Interface was found: " + interfaceName);
		} catch (SocketException e) {
			System.out.println("Could not locate interface");
		}
	}

	public static void getNetworkAddress(String address) {
		try {
			System.out.println("--------------------------");
			System.out.println("getNetworkAddress");
			InetAddress local = InetAddress.getByName(address);
			NetworkInterface networkInterface = NetworkInterface.getByInetAddress(local);

			if (networkInterface == null)
				System.out.println("No local loopback, shouldn't see this");
			else
				System.out.println("Local loopback found");
		} catch (SocketException e) {
			System.out.println("Could not locate interface");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
