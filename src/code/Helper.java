package code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Helper {
	public static void main(String[] args) {

	}

	public static long hashSocketAddress(InetSocketAddress addr) {
		// converts a ip address into a number
		return 0l;
	}

	public static long hashString(String s) {
		// converts a hash string into a number
		return 0l;
	}

	public static long hashHashCode(int i) {

		// 32 bit regular hash code -> byte[4]
		byte[] hashbytes = new byte[4];
		hashbytes[0] = (byte) (i >> 24);
		hashbytes[1] = (byte) (i >> 16);
		hashbytes[2] = (byte) (i >> 8);
		hashbytes[3] = (byte) (i /* >> 0 */);

		// try to create SHA1 digest
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// successfully created SHA1 digest
		// try to convert byte[4]
		// -> SHA1 result byte[]
		// -> compressed result byte[4]
		// -> compressed result in long type
		if (md != null) {
			md.reset();
			md.update(hashbytes);
			byte[] result = md.digest();

			byte[] compressed = new byte[4];
			for (int j = 0; j < 4; j++) {
				byte temp = result[j];
				for (int k = 1; k < 5; k++) {
					temp = (byte) (temp ^ result[j + k]);
				}
				compressed[j] = temp;
			}

			long ret = (compressed[0] & 0xFF) << 24 | (compressed[1] & 0xFF) << 16 | (compressed[2] & 0xFF) << 8
					| (compressed[3] & 0xFF);
			ret = ret & (long) 0xFFFFFFFFl;
			return ret;
		}
		return 0;
	}

	public static InetSocketAddress createSocketAddress(String addr) {

		// Convert a string address to an Java ip address

		// Need to split a string address

		// Need to use InetAddress

		// Need to use InetSocketAddress

		return null; // Replace an InetSocketAddress
	}
}
