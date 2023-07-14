package com.example.versteckt.modules;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@RequiresApi(api = Build.VERSION_CODES.S)
public class DataHandler
{
    public static int autoDigit(int number, int base)
    {
        number += 1;
        int digit = 0;
        int max = 0;
        while ( number > max )
        {
            digit++;
            max = ((int)(Math.pow(base, digit))); 
        }
        return ((int)digit);
    }
    public static int autoDigit(BigInteger number, int base)
    {
        number = number.add(big.one);
        int digit = 0;
        BigInteger max = big.zero;
        BigInteger powerBase = big.n(base);
        while ( big.more(number, max) )
        {
            digit++;
            max = powerBase.pow(digit);
        }
        return digit;
    }


    public static String autoBinary(String numberString)
    {
        BigInteger large = big.n(numberString);
        String result;
        try
        {
            int num = large.intValueExact();
            result = BASE2.autoBinary(num);
        }
        catch (ArithmeticException e)
        {
            result = BASE2.autoBinary(large);
        }
        return result;
    }
    public static String autoHex(String numberString)
    {
        BigInteger large = big.n(numberString);
        String result;
        try
        {
            int num = large.intValueExact();
            result = BASE16.autoHex(num);
        }
        catch (ArithmeticException e)
        {
            result = BASE16.autoHex(large);
        }
        return result;
    }

    public static String autoNumString_hex(String hex)
    {
        BigInteger large = BASE16.toBigInteger(hex);
        String result;
        int num = BASE16.toInt_UNSURE(hex);
        if (num == -1)
        {
            result = large.toString();
        }
        else
        {
            result = String.valueOf(num);
        }
        return result;
    }
    public static String autoNumString_bin(String hex)
    {
        BigInteger large = BASE2.toBigInteger(hex);
        String result;
        int num = BASE2.toInt_UNSURE(hex);
        if (num == -1)
        {
            result = large.toString();
        }
        else
        {
            result = String.valueOf(num);
        }
        return result;
    }

    public static int highestAsciiInText(char[] characters)
    {
        int record = -1;
        for (char character : characters)
        {
            int ascii = (int)character;
            if (ascii > record)
            { record = ascii; }
        }
        return record;
    }

    public static String textToBinary(String text, String separator)
    {
        char[] characters = text.toCharArray();
        int[] asciis = new int[characters.length];
        int size = 0;
        int highestAscii = -1;
        for (int chr : characters ) { asciis[size] = (int)chr;  if (asciis[size] > highestAscii) highestAscii = asciis[size] ; size++; }
        int highestBit = (highestAscii > 255) ? (highestAscii > 65535) ? 32 : 16 : 8;
        StringBuilder binary = new StringBuilder("");
        for (int index = 0; index < size; index++)
        {
            binary.append(BASE2.toBinary(asciis[index], highestBit));
            if (index == (size-1)) { continue; }
            binary.append(separator);
        }
        return binary.toString();
    }
    public static String textToHex(String text, String separator, boolean prefix)
    {
        char[] characters = text.toCharArray();
        int[] asciis = new int[characters.length];
        int size = 0;
        int highestAscii = -1;
        for (int chr : characters ) { asciis[size] = (int)chr;  if (asciis[size] > highestAscii) highestAscii = asciis[size] ; size++; }
        int mostDigit = (highestAscii > 255) ? (highestAscii > 65535) ? 8 : 4 : 2;
        StringBuilder binary = new StringBuilder("");
        for (int index = 0; index < size; index++)
        {
            String hex = BASE16.toHex(asciis[index], mostDigit, prefix);
            binary.append(hex);
            if (index == (size-1)) { continue; }
            binary.append(separator);
        }
        String result = binary.toString();
        return result;
    }
    public static byte[] textToBase64(String inputText)
    {
        return BASE64.encryptData(inputText.getBytes(StandardCharsets.UTF_8));
    }
    public static String textToBase64String(String inputText)
    {
        return (new String(BASE64.encryptData(inputText.getBytes(StandardCharsets.UTF_8))));
    }

    public static String binaryToText(String text, String separator)
    {
        String[] bins = text.split(separator);
        char[] characters = new char[bins.length];
        int size = 0;
        for (String bin : bins) { characters[size] = ((char)BASE2.toInt(bin)) ; size++; }
        return String.copyValueOf(characters);
    }
    public static String hexToText(String text, String separator)
    {
        String[] hex = text.split(separator);
        char[] characters = new char[hex.length];
        int size = 0;
        for (String h : hex) { characters[size] = ((char)BASE16.toInt(h)) ; size++; }
        return String.copyValueOf(characters);
    }
    public static String stringBase64ToText(String encryptedText)
    {
        return (new String(BASE64.decryptData(encryptedText.getBytes(StandardCharsets.UTF_8))));
    }
    public static String base64ToText(byte[] encryptedText)
    {
        return (new String(BASE64.decryptData(encryptedText)));
    }
}
