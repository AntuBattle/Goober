package org.goober.client.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class KeyUtil {

    private static final int SALT_LENGTH = 16; // 16 bytes for salt
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256; // 256 bits

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


    /**
     *
     * @param username user's registration username
     * @param password user's registration password
     * @param salt generated salt for encryption
     * @return the generated Vault Key encoded in Base64.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String deriveVaultKey(String username, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] key = deriveKey(username, password, salt).getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     *
     * @param vaultKey the user's personal Vault Key
     * @param password the user's Password
     * @param salt generated salt
     * @return returns the Auth Key that will be used to retrieve the vault from the server in Base64 encoding.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String deriveAuthKey(String vaultKey, String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] key = deriveKey(vaultKey, password, salt).getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

    public static SecretKey deriveKey(String string1, String string2, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String concatenatedParams = string1 + string2;
        PBEKeySpec spec = new PBEKeySpec(concatenatedParams.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        //byte[] key = factory.generateSecret(spec).getEncoded();
        //return Base64.getEncoder().encodeToString(key);
        return factory.generateSecret(spec);
    }
}

