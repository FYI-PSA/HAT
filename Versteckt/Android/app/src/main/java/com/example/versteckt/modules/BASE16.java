package com.example.versteckt.modules;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BASE16 
{

    /* -------------------------------------------------------------------------------------- */
    /* BASE16-TABLE-------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static ArrayList<String> allHexadecimalDigits = new ArrayList<String>(Arrays.asList("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"));

    /* -------------------------------------------------------------------------------------- */
    /* MAIN---------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        GENERAL.print("[*] Hexadecimal Converter\n\n");
        GENERAL.print("[@] Enter 'H' to turn a hexadecimal value into a number\n");
        GENERAL.print("[@] Enter 'N' to turn a number into a hex byte\n");
        GENERAL.print("\n[?] > ");
        char choice = GENERAL.getInput(input).charAt(0);
        GENERAL.print("\n\n");
        if (choice == 'H')
        {
            GENERAL.print("[@] Enter the hex string\n");
            GENERAL.print("[?] > ");
            String hexadecimalValue = GENERAL.getInput(input);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(String.valueOf(hexToDecimal(hexadecimalValue)));
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'N')
        {
            GENERAL.print("[@] Enter the number\n");
            GENERAL.print("[?] > ");
            int decimalValue = Integer.parseInt(GENERAL.getInput(input));
            GENERAL.print("[@] How many digits of hex? (default: 2)\n");
            GENERAL.print("[@] Leave blank if you don't know\n");
            GENERAL.print("[?] > ");
            int hexDigits = 2;
            String digitInput = GENERAL.getInput(input);
            if (!(digitInput.equals("")))
            {
                hexDigits = Integer.parseInt(digitInput);
            }
            String hexadecimalValue = decimalToHex(decimalValue, hexDigits, true);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(hexadecimalValue);
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        input.close();
        return;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN-BASE16-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */


    public static String decimalToHex(int decimal)
    {
        return decimalToHex(decimal, 2, true);
    }
    public static String decimalToHex(int decimal, int hexadecimalDigits, boolean prefix)
    {
        String hexOutput = "";
        for (int currentDigitIndex = 0; currentDigitIndex < hexadecimalDigits; currentDigitIndex++)
        {
            int currentDigitImportance = hexadecimalDigits - (currentDigitIndex + 1);
            int currentHexadecimalPower = (int)(Math.pow((double)16, (double)currentDigitImportance));
            int currentHexadecimalDigitValue = (int)(decimal / currentHexadecimalPower);
            if (currentHexadecimalDigitValue > 15)
            {
                currentHexadecimalDigitValue = 15;
            }
            decimal = decimal - (currentHexadecimalDigitValue * currentHexadecimalPower);
            String currentDigit = allHexadecimalDigits.get(currentHexadecimalDigitValue);
            hexOutput = hexOutput + currentDigit;
        }
        if(prefix)
        {
            hexOutput = "0x" + hexOutput;
        }
        return hexOutput;
    }
    public static int hexToDecimal(String hexadecimal)
    {
        hexadecimal = hexadecimal.replaceFirst("^0x", "");
        int decimalOutput = 0;
        hexadecimal = hexadecimal.toUpperCase();
        int hexadecimalDigits = hexadecimal.length();
        for (int currentDigitIndex = 0; currentDigitIndex < hexadecimalDigits; currentDigitIndex++)
        {
            String currentDigit = String.valueOf((hexadecimal.charAt(currentDigitIndex)));
            int currentDigitValue = allHexadecimalDigits.indexOf(currentDigit);
            int currentDigitImportance = hexadecimalDigits - (currentDigitIndex + 1);
            int currentDigitPower = (int)(Math.pow((double)16, (double)currentDigitImportance));
            int currentDigitWorth = currentDigitValue * currentDigitPower;
            decimalOutput = decimalOutput + currentDigitWorth;
        }
        return decimalOutput;
    }
    public static String dataStringToHexadecimalString(String data, boolean prefix, int digits, String separator)
    {
        String encrypted = "";
        int dataLength = data.length();
        for (int characterIndex = 0; characterIndex < dataLength; characterIndex++)
        {
            char currentCharacter = data.charAt(characterIndex);
            int characterAsciiCode = ((int)currentCharacter);
            String currentHex = decimalToHex(characterAsciiCode, digits, prefix);
            encrypted += currentHex;
            if (characterIndex == (dataLength-1))
            {
                continue;
            }
            encrypted += separator;
        }
        return encrypted;
    }
    public static String hexadecimalStringToDataString(String hexadecimalString, String separator)
    {
        String data = "";
        String[] hexadecimalArray = hexadecimalString.split(separator);
        for (String hexadecimalItem : hexadecimalArray)
        {
            GENERAL.print(hexadecimalItem);
            int currentAsciiCode = hexToDecimal(hexadecimalItem);
            GENERAL.print(currentAsciiCode);
            char currentCharacter = (char)currentAsciiCode;
            String current = String.valueOf(currentCharacter);
            data += current;
        }
        return data;
    }

}
