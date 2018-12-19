package com.dms.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class CryptoUtil {

	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
    
    public static byte[] generateSalt() {
		byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt;
	}
    
    public static byte[] hashPassword(String password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		
	    try {
	      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      return skf.generateSecret(spec).getEncoded();
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	    } finally {
	      spec.clearPassword();
	    }
	}
    
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(2048, random);
		return keyGen.generateKeyPair();
    }

}