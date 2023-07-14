package com.example.versteckt.modules;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;
import java.io.IOException;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;

import java.security.spec.*;
import javax.crypto.Cipher;
import java.security.*;

@RequiresApi(api = Build.VERSION_CODES.S)
public class RSA
{
    public static String publicKeyFileName = "PUBLIC.KEY";
    public static String privateKeyFileName = "PRIVATE.KEY";

    public static String RSAEncrypt(PublicKey publicKey, String data)
    { return stringToStringPublicKey(publicKey, data); }
    public static String RSADecrypt(PrivateKey privateKey, String data)
    { return stringToStringPrivateKey(privateKey, data); }

    public static String RSASign(PrivateKey privateKey, String data)
    { return stringToStringPrivateKey(privateKey, data); }
    public static String RSAConfirmSignature(PublicKey publicKey, String data)
    { return stringToStringPublicKey(publicKey, data); }


    public static void main(String[] args)
    {
        System.out.println("\n");
        if (args.length == 0)
        {
            System.out.println("No arguments.");
            System.out.println("\n");
            System.exit(1);
        }
        if (Objects.equals(args[0], "--gen"))
        {
            generateKeyPairToLocalFile(publicKeyFileName, privateKeyFileName);
        }
        if (Objects.equals(args[0], "--dec"))
        {
            KeyPair keys = readKeyPairLocalFile(publicKeyFileName, privateKeyFileName);
            if (keys == null)
            { return; }
            StringBuilder input = new StringBuilder("");
            for (String arg : args)
            {
                if (Objects.equals(arg, args[0]))
                {
                    continue;
                }
                input.append(arg);
            }
            String encrypted = input.toString();
            String data = stringToStringPrivateKey(keys.getPrivate(), encrypted);
            System.out.println(data);
        }
        if (Objects.equals(args[0], "--enc"))
        {
            KeyPair keys = readKeyPairLocalFile(publicKeyFileName, privateKeyFileName);
            if (keys == null)
            { return; }
            StringBuilder input = new StringBuilder("");
            for (String arg : args)
            {
                if (Objects.equals(arg, args[0]))
                {
                    continue;
                }
                input.append(arg);
                input.append(" ");
            }
            String data = input.toString();
            String encrypted = stringToStringPublicKey(keys.getPublic(), data);
            System.out.println(encrypted);
        }
        System.out.println("\n");
    }

    public static String stringToStringPublicKey(PublicKey publicKey, String data)
    {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] b64data = BASE64.encryptData(dataBytes);
        byte[] encryptedData = encryptData(publicKey, b64data);
        byte[] b64encrypted = BASE64.encryptData(encryptedData);
        return new String(b64encrypted);
    }
    public static String stringToStringPrivateKey(PrivateKey privateKey, String encrypted)
    {
        byte[] b64encrypted = encrypted.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = BASE64.decryptData(b64encrypted);
        byte[] b64Bytes = decryptData(privateKey, encryptedBytes);
        byte[] decryptedBytes = BASE64.decryptData(b64Bytes);
        return new String(decryptedBytes);
    }

    public static byte[] decryptData(PrivateKey privateKey, byte[] encrypted)
    {
        byte[] data = null;
        try
        {
            Cipher decryptionCipher = Cipher.getInstance("RSA");
            decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
            data = decryptionCipher.doFinal(encrypted);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e)
        { throw new RuntimeException(e); }
        return data;
    }
    public static byte[] encryptData(PublicKey publicKey, byte[] data)
    {
        byte[] encrypted = null;
        try
        {
            Cipher encryptionCipher = Cipher.getInstance("RSA");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encrypted = encryptionCipher.doFinal(data);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e)
        { throw new RuntimeException(e); }
        return encrypted;
    }
    private static pair<byte[], byte[]> generateB64EncryptedKeysFiles()
    {
        try
        {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            keygen.initialize(4096);
            KeyPair keys = keygen.generateKeyPair();
            PrivateKey privateKey = keys.getPrivate();
            PublicKey publicKey = keys.getPublic();
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] privateKeyFileData = BASE64.encryptData(privateKeyBytes);
            byte[] publicKeyFileData = BASE64.encryptData(publicKeyBytes);
            return (new pair<byte[], byte[]>(publicKeyFileData, privateKeyFileData));
        }
        catch (NoSuchAlgorithmException e)
        { throw new RuntimeException(e); }
    }
    public static void generateKeyPairToLocalFile(String PublicFileName, String PrivateFileName)
    {
        try
        {
            pair<byte[], byte[]> filesData = generateB64EncryptedKeysFiles();
            byte[] publicKeyFileData = filesData.first;
            byte[] privateKeyFileData = filesData.second;
            FileOutputStream privateKeyFile = new FileOutputStream(PrivateFileName);
            privateKeyFile.write(privateKeyFileData);
            privateKeyFile.close();
            FileOutputStream publicKeyFile = new FileOutputStream(PublicFileName);
            publicKeyFile.write(publicKeyFileData);
            publicKeyFile.close();
            System.out.println("[$] SUCCESSFULLY GENERATED KEYPAIR AND SAVED TO FILE");
        }
        catch (IOException e)
        { throw new RuntimeException(e); }
        /* System.exit(0); */
    }
    public static void generateKeyPairToAndroidFile(String PublicFileName, String PrivateFileName, Context androidContext)
    {
        try
        {
            pair<byte[], byte[]> filesData = generateB64EncryptedKeysFiles();
            byte[] publicKeyFileData = filesData.first;
            byte[] privateKeyFileData = filesData.second;
            FileOutputStream privateKeyFile = androidContext.openFileOutput(PrivateFileName, Context.MODE_PRIVATE);
            privateKeyFile.write(privateKeyFileData);
            privateKeyFile.close();
            FileOutputStream publicKeyFile = androidContext.openFileOutput(PublicFileName, Context.MODE_PRIVATE);
            publicKeyFile.write(publicKeyFileData);
            publicKeyFile.close();
            System.out.println("[$] SUCCESSFULLY GENERATED KEYPAIR AND SAVED TO FILE");
        }
        catch (IOException e)
        { throw new RuntimeException(e); }
        /* System.exit(0); */
    }
    public static byte[] readLocalFile(String fileName)
    {
        try
        {
            File localFile = new File(fileName);
            return Files.readAllBytes(localFile.toPath());
        }
        catch (NoSuchFileException e)
        { return null; }
        catch (IOException e)
        { throw new RuntimeException(e); }
    }
    public static byte[] readAndroidFile(String fileName, Context androidContext)
    {
        try
        {
            File localFile = androidContext.getFileStreamPath(fileName);
            return Files.readAllBytes(localFile.toPath());
        }
        catch (NoSuchFileException e)
        { return null; }
        catch (IOException e)
        { throw new RuntimeException(e); }
    }
    public static KeyPair readKeyPairAndroidFile(String PublicFileName, String PrivateFileName, Context androidContext)
    {
        KeyPair keys = null;
        try
        {
            byte[] publicKeyFileData = readAndroidFile(PublicFileName, androidContext);
            byte[] privateKeyFileData = readAndroidFile(PrivateFileName, androidContext);
            if (publicKeyFileData == null || privateKeyFileData == null)
            { System.out.println("[#] NO KEYPAIR GENERATED. TRY GENERATING ONE FIRST."); /* System.exit(1); */ return null; }
            byte[] publicKeyBytes = BASE64.decryptData(publicKeyFileData);
            byte[] privateKeyBytes = BASE64.decryptData(privateKeyFileData);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(encodedPublicKey);
            EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(encodedPrivateKey);
            keys = new KeyPair(publicKey, privateKey);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        { throw new RuntimeException(e); }
        return keys;
    }
    public static KeyPair readKeyPairLocalFile(String PublicFileName, String PrivateFileName)
    {
        KeyPair keys = null;
        try
        {
            byte[] publicKeyFileData = readLocalFile(PublicFileName);
            byte[] privateKeyFileData = readLocalFile(PrivateFileName);
            if (publicKeyFileData == null || privateKeyFileData == null)
            { System.out.println("[#] NO KEYPAIR GENERATED. TRY GENERATING ONE FIRST."); /* System.exit(1); */ return null; }
            byte[] publicKeyBytes = BASE64.decryptData(publicKeyFileData);
            byte[] privateKeyBytes = BASE64.decryptData(privateKeyFileData);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(encodedPublicKey);
            EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(encodedPrivateKey);
            keys = new KeyPair(publicKey, privateKey);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        { throw new RuntimeException(e); }
        return keys;
    }
}
