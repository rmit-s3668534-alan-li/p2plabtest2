package code;

import java.util.Random;

public class IpAddressHelper {

    public static String createRandom() {
        return randomNumber() + "." + randomNumber() + "." + randomNumber() + "." + randomNumber();
    }

    public static int randomNumber() {
        return new Random().nextInt((255 - 1) + 1) + 1;
    }
}