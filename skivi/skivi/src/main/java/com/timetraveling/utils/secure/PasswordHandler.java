package com.timetraveling.utils.secure;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Clasa aceasta se ocupa cu hashing-ul parolelor prin SHA.
 */
public class PasswordHandler {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
     * Un salt este un numar pe multi biti, generat aleator,
     * folosit in generarea parolelor mai sigure
     * @param length
     * @return
     */
    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    /**
     * Aici se cripteaza parola. Am citit ca este best practice sa folosim
     * char[] in loc de String pentru a tine in memorie parola, fiindca String-urile
     * raman in memorie mai mult timp
     * @param password
     * @param salt
     * @return
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            /**
             * Acum scapam de ceea ce am generat din memorie
             */
            spec.clearPassword();
        }
    }
        public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt)
    {
        boolean returnValue = false;

        String newSecurePassword = generateSecurePassword(providedPassword, salt);


        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}
