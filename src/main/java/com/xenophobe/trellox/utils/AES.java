package com.xenophobe.trellox.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AES {

    private static final Logger LOG = LoggerFactory.getLogger(AES.class);


    private final SecretKeySpec secretKey;
    private  byte[] key;

    public  AES() throws UnsupportedEncodingException {
        MessageDigest sha=null;
        String myKey="XenoPhobe99";
        try {
            key = myKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("NoSuchAlgorithmException {}",e);
            throw new UnsupportedEncodingException();
        }
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("UnsupportedEncodingException {}",e);
            throw new UnsupportedEncodingException();
        }
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");
    }


    public  String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            LOG.error("Exception {}",e);
        }
        return null;
    }

    public  String decrypt(String strToDecrypt) {
        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            LOG.error("Exception {}",e);
        }
        return null;
    }


}


