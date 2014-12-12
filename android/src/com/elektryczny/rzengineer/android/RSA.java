package com.elektryczny.rzengineer.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private KeyPairGenerator kpg;
    private KeyPair kp;
    private byte[] encryptedBytes, decryptedBytes;
    private Cipher cipher, cipher1;
    private String encrypted, decrypted;
    private String privateKeyPath;
    private String publicKeyPath;

    public RSA(String privKey, String pubKey) {
        this.privateKeyPath = MultimediaFileManager.RESOURCES_DIRECTORY + privKey;
        this.publicKeyPath = MultimediaFileManager.RESOURCES_DIRECTORY + pubKey;
    }

    public void generateNewKeys() {
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(2048);
        kp = kpg.genKeyPair();
        try {
            FileOutputStream keyPub = new FileOutputStream(publicKeyPath);
            keyPub.write(kp.getPublic().getEncoded());
            keyPub.close();

            FileOutputStream keyPriv = new FileOutputStream(privateKeyPath);
            keyPriv.write(kp.getPrivate().getEncoded());
            keyPriv.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PublicKey getPublicKeyFromStream() {
        FileInputStream keyfis = null;
        try {
            keyfis = new FileInputStream(publicKeyPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] encKey = null;
        try {
            encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        PublicKey publicKey = null;
        try {
            publicKey = kf.generatePublic(new X509EncodedKeySpec(encKey));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private PrivateKey getPrivateKeyFromStream() {
        FileInputStream keyfis = null;
        try {
            keyfis = new FileInputStream(privateKeyPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] encKey = null;
        try {
            encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        PrivateKey privateKey = null;
        try {
            privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(encKey));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public String Encrypt(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PublicKey publicKeyObj = getPublicKeyFromStream();
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyObj);
        encryptedBytes = cipher.doFinal(plain.getBytes());

        encrypted = bytesToString(encryptedBytes);
        return encrypted;

    }

    public String Decrypt(String result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey privateKeyObj = getPrivateKeyFromStream();
        cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKeyObj);
        decryptedBytes = cipher1.doFinal(stringToBytes(result));
        decrypted = new String(decryptedBytes);
        return decrypted;
    }

    public String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
}