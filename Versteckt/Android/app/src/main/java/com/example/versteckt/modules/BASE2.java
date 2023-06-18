package com.example.versteckt.modules;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.math.BigInteger;

public class BASE2 
{ /* This class only deals with strings. It's currently of no use to me to make functions that use actual byte objects */
    public static String toByte(int number_int) { return toBinary(number_int, 8); }
    public static String toBinary(int number_int, int digits) 
    { 
        StringBuilder binary = new StringBuilder("");
        for (int index = 0; index < digits; index++)
        {
            int power = (digits - 1) - index;
            int bitValue = (int)Math.pow(2, power);
            if (number_int >= bitValue)
            {
                number_int = number_int - bitValue;
                binary.append("1");
                continue;
            }
            binary.append("0");
        }
        return binary.toString();
    }
    public static int toInt(String binary)
    {
        int number = 0;
        int index = 1;
        int length = binary.length();
        char[] bitArray = binary.toCharArray();
        for (char bit : bitArray)
        {
            if (bit == '1')
            {
                int power = length - index;
                int bitValue = (int)Math.pow(2, power);
                number = number + bitValue;
            }
            index++;
        }
        return ((int)number);
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    public static String autoBinary(int number_int)
    {
        int digits = DataHandler.autoDigit(number_int, 2);
        String result = toBinary(number_int, digits);
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public static String autoBinary(BigInteger number_bigint)
    {
        int digits = DataHandler.autoDigit(number_bigint, 2);
        String result = toBinary(number_bigint, digits);
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public static int toInt_UNSURE(String binary)
    {
        int result;
        try
        { result = toBigInteger(binary).intValueExact(); }
        catch(ArithmeticException e)
        { result = 0; }
        return result;
    }


    public static String toByte(BigInteger number_bigint) { return toBinary(number_bigint, 8); }
    public static String toBinary(BigInteger number_bigint, int digits)
    {
        StringBuilder binary = new StringBuilder("");
        BigInteger two = big.n(2);
        for (int index = 0; index < digits; index++)
        {
            int power = (digits - 1) - index;
            BigInteger bitValue = two.pow(power);
            if (big.eqmore(number_bigint, bitValue))
            {
                number_bigint = number_bigint.subtract(bitValue);
                binary.append("1");
                continue;
            }
            binary.append("0");
        }
        return binary.toString();
    }
    public static BigInteger toBigInteger(String binary)
    {
        BigInteger number = big.n(0);
        BigInteger two = big.n(2);
        int index = 1;
        int length = binary.length();
        char[] bitArray = binary.toCharArray();
        for (char bit : bitArray)
        {
            if (bit == '1')
            {
                int power = length - index;
                BigInteger bitValue = two.pow(power);
                number = number.add(bitValue);
            }
            index++;
        }
        return number;
    }
}
