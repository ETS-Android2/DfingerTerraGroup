package com.example.iamliterallymalding.Encryption;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPass {

    private String password;

    public HashPass(String password){
        this.password = password;
    }

    public String generateHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] passChars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(passChars, salt, 1000, 512);
        SecretKeyFactory secret = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = secret.generateSecret(spec).getEncoded();
        System.out.println(toHex(salt) + ":" + toHex(hash));
        return 1000 + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public boolean validatePassword(String storedPass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPass.split(":");

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),
                salt, Integer.parseInt(parts[0]),hash.length * 8);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = keyFactory.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;

        for(int i = 0; i < hash.length && i < testHash.length; i++){
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException{
        SecureRandom randomByte = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        randomByte.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] toConvert){
        BigInteger bigGus = new BigInteger(1, toConvert);
        String output = bigGus.toString(16);
        int paddingLength = (toConvert.length * 2) - output.length();
        if(paddingLength > 0){
            return String.format("%0" + paddingLength + "d", 0) + output;
        }
        else{
            return output;
        }
    }

    private byte[] fromHex (String hex){
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }


}

