package com.example.iamliterallymalding;

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

    private String generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] passChars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(passChars, salt, 1000, 512);
        SecretKeyFactory secret = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = secret.generateSecret(spec).getEncoded();
        return 1000 + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private byte[] getSalt() throws NoSuchAlgorithmException{
        SecureRandom randomByte = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        randomByte.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] toConvert) throws NoSuchAlgorithmException{
        BigInteger bigGus = new BigInteger(toConvert);
        String output = bigGus.toString(16);
        int paddingLength = (toConvert.length*2)-output.length();
        if(paddingLength > 0){
            return String.format("%0" + paddingLength + "d", 0) + output;
        }
        else{
            return output;
        }
    }

}

