package com.example.versteckt.modules;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.math.BigInteger;

public class BASE16
{
    public static final char[] hexadecimalArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};


    public static String toHex(int number_int, int digits, boolean prefix)
    {
        StringBuilder hex = new StringBuilder("");
        if (prefix)
        {
            hex.append("0x");
        }
        for (int digit = 1; digit <= digits; digit++)
        {
            int importance = digits - digit;
            int power = (int)Math.pow(16, importance);
            int value = number_int / power;
            if (value > 15)
            { value = 15; }
            number_int = number_int - (value * power);
            char hexDigit = hexadecimalArray[value];
            hex.append(hexDigit);
        } 
        return hex.toString();
    }
    public static int toInt(String hex)
    {
        hex = hex.replaceFirst("^0x", "").toUpperCase();
        int number = 0;
        int digits = hex.length();
        for (int digitIndex = 0; digitIndex < digits; digitIndex++)
        {
            char digit = hex.charAt(digitIndex);
            int digitPower = (new String(hexadecimalArray)).indexOf(digit);
            int importance = digits - (digitIndex + 1);
            int digitWorth = ((int)Math.pow(16, importance)) * digitPower;
            number += digitWorth;
        }
        return number;
    }


    public static String autoHex(int number_int)
    {
        int digits = DataHandler.autoDigit(number_int, 16);
        String result = toHex(number_int, digits, false);
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public static String autoHex(BigInteger number_bigint)
    {
        int digits = DataHandler.autoDigit(number_bigint, 16);
        String result = toHex(number_bigint, digits, false);
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public static int toInt_UNSURE(String hex)
    {
        int result;
        try
        { result = toBigInteger(hex).intValueExact(); }
        catch(ArithmeticException e)
        { result = 0; }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public static String toHex(BigInteger number_bigint, int digits, boolean prefix)
    {
        StringBuilder hex = new StringBuilder("");
        if (prefix)
        {
            hex.append("0x");
        }
        BigInteger sixteen = big.n(16);
        for (int digit = 1; digit <= digits; digit++)
        {
            int importance = digits - digit;
            BigInteger power = sixteen.pow(importance);
            BigInteger bigValue = number_bigint.divide(power);
            int value = 15;
            try
            {
                value = bigValue.intValueExact();                
            }
            catch (ArithmeticException e)
            {
                value = 15;
            }
            finally
            {
                if (value > 15)
                { 
                    value = 15; 
                }
            }
            number_bigint = number_bigint.subtract(bigValue.multiply(power));
            char hexDigit = hexadecimalArray[value];
            hex.append(hexDigit);
        } 
        return hex.toString();
    }
    public static BigInteger toBigInteger(String hex)
    {
        hex = hex.replaceFirst("^0x", "").toLowerCase();
        BigInteger sixteen = big.n(16);
        BigInteger number = big.zero;
        int digits = hex.length();
        for (int digitIndex = 0; digitIndex < digits; digitIndex++)
        {
            char digit = hex.charAt(digitIndex);
            BigInteger digitPower = big.n((new String(hexadecimalArray)).indexOf(digit));
            int importance = digits - (digitIndex + 1);
            BigInteger digitWorth = sixteen.pow(importance).multiply(digitPower);
            number = number.add(digitWorth);
        }
        return number;
    }
}