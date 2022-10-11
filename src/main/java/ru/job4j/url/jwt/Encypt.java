package ru.job4j.url.jwt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

public class Encypt {

    private final ThreadLocalRandom randomNumber = ThreadLocalRandom.current();

    public String generateExecuteLogin(String pass) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        StringBuilder m5dHex = new StringBuilder(bigInteger.toString(16));
        while (m5dHex.length() < 32) {
            m5dHex.insert(0, "0");
        }
        return m5dHex.toString();
    }

    public String generatorExecutePassword(int wordLength) {
        StringBuilder word = new StringBuilder();
        int exitLoop = 0;
        while (exitLoop < wordLength) {
            int number = randomNumber.nextInt(40, 130);
            if ((number <= 60 || number >= 70) && (number <= 90 || number >= 100)) {
                word.appendCodePoint(number);
                exitLoop++;
            }
        }
        return word.toString();
    }

}
