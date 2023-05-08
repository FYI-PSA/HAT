package com.example.versteckt.rsamodules;

import java.util.ArrayList;
import java.util.Scanner;

public class BASE64 
{   

    /* -------------------------------------------------------------------------------------- */
    /* BASE64-TABLE-------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static ArrayList<Character> getB64Table()
    {
        ArrayList<Character> table = new ArrayList<Character>(){};
        for (char uppercase = 'A'; uppercase < ('Z' + 1); uppercase++)
        {
            table.add(uppercase);
        }
        for (char lowercase = 'a'; lowercase < ('z' + 1); lowercase++)
        {
            table.add(lowercase);
        }
        for (char number = '0'; number < ('9' + 1); number++)
        {
            table.add(number);
        }
        table.add('+');
        table.add('/');
        return table;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN---------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        GENERAL.print("[*] Base64 Converter\n\n");
        GENERAL.print("[@] Enter 'D' to turn base64 encrypted content into a string\n");
        GENERAL.print("[@] Enter 'E' to encrypt a string into base64\n");
        GENERAL.print("\n[?] > ");
        char choice = GENERAL.getInput(input).charAt(0);
        GENERAL.print("\n\n");
        if (choice == 'E')
        {
            GENERAL.print("[@] Enter your text\n");
            GENERAL.print("[?] > ");
            String data = GENERAL.getInput(input);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(String.valueOf(encrypt(data)));
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'D')
        {
            GENERAL.print("[@] Enter your encrypted base64 string\n");
            GENERAL.print("[?] > ");
            String data = GENERAL.getInput(input);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(String.valueOf(decrypt(data)));
            GENERAL.print("\n\n[!] Goodbye!\n\n");        
        }
        input.close();
        return;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN-BASE64-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static String encrypt(String data)
    {
        String encrypted = "";
        ArrayList<Character> characters = GENERAL.makeCharacterArrayFromString(data);
        ArrayList<Integer> characterCodes = GENERAL.characterArrayToIntegerArray(characters);
        ArrayList<String> byteArray = makeByteArrayFromIntegerArray(characterCodes);
        int paddingCount = calculatePaddingCount(byteArray);
        ArrayList<String> paddedByteArray = addPaddingBits(byteArray, paddingCount);
        String allBitsString = joinBits(paddedByteArray);
        ArrayList<String> sixBitBinaryArray = GENERAL.makeChunksFromString(allBitsString, 6);
        ArrayList<Integer> sixBitAsciiArray = makeIntegerArrayFromBinaryArray(sixBitBinaryArray);
        ArrayList<Character> newCharacterArray = lookupOnTable(sixBitAsciiArray);
        ArrayList<Character> paddedCharacterArray = fixB64Array(newCharacterArray, paddingCount);
        encrypted = GENERAL.makeStringFromCharacterArray(paddedCharacterArray);
        return encrypted;
    }
    public static String decrypt(String data)
    {
        String decrypted = "";
        ArrayList<Character> paddedCharacterArray = GENERAL.makeCharacterArrayFromString(data);
        int paddingCount = countPaddings(paddedCharacterArray);
        ArrayList<Character> noPaddingCharacterArray = replacePaddings(paddedCharacterArray, paddingCount);
        ArrayList<Integer> characterCodeArray = indexLookupOnTable(noPaddingCharacterArray);
        ArrayList<String> sixBitBinaryArray = makeBinaryArrayFromIntegerArray(characterCodeArray, 6);
        String allBitsString = joinBits(sixBitBinaryArray);
        ArrayList<String> paddedByteArray = GENERAL.makeChunksFromString(allBitsString, 8);
        ArrayList<String> byteArray = removePaddingBytes(paddedByteArray);
        ArrayList<Integer> originalAsciiArray = makeIntegerArrayFromBinaryArray(byteArray);
        ArrayList<Character> originalCharacterArray = GENERAL.integerArrayToCharacterArray(originalAsciiArray);
        decrypted = GENERAL.makeStringFromCharacterArray(originalCharacterArray);
        return decrypted;
    }

    /* -------------------------------------------------------------------------------------- */
    /* CALCULATION-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    private static ArrayList<String> removePaddingBytes(ArrayList<String> paddedBytes)
    {
        ArrayList<String> normalByteArray = new ArrayList<String>();
        for (String byteString : paddedBytes)
        {
            if ((byteString.length()) == 8)
            {
                Boolean notZero = false;
                for (int bitIndex = 0; bitIndex < 8; bitIndex++)
                {
                    if (byteString.charAt(bitIndex) != '0')
                    {
                        notZero = true;
                        break;
                    }
                }
                if (notZero) 
                {
                    normalByteArray.add(byteString);
                }
            }
        }
        return normalByteArray;
    }
    private static ArrayList<Integer> indexLookupOnTable(ArrayList<Character> characterArray)
    {
        ArrayList<Character> table = getB64Table();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (Character character : characterArray)
        {
            int index = table.indexOf(character);
            indices.add(index);
        }
        return indices;
    }
    private static ArrayList<Character> replacePaddings(ArrayList<Character> paddedCharacterArray, int paddings)
    {
        ArrayList<Character> normalCharacters = paddedCharacterArray;
        int lastIndex = (normalCharacters.size()) - 1;
        for (int paddingIndex = 0; paddingIndex < paddings; paddingIndex++)
        {
            int padding = lastIndex - paddingIndex;
            normalCharacters.set(padding, 'A');
        }
        return normalCharacters;
    }
    private static int countPaddings(ArrayList<Character> paddedCharacterArray)
    {
        int paddings = 0;
        for (int characterIndex = ((paddedCharacterArray.size()) - 1); characterIndex >= 0; -- characterIndex)
        {
            char currentCharacter = paddedCharacterArray.get(characterIndex);
            if (currentCharacter == '=')
            {
                paddings ++;
            }
        }
        return paddings;
    }
    private static ArrayList<Character> fixB64Array(ArrayList<Character> characterArray, int paddingCount)
    {
        ArrayList<Character> fixedCharacters = characterArray;
        int lastIndex = (fixedCharacters.size()) - 1;
        for (int paddingIndex = 0; paddingIndex < paddingCount; paddingIndex++)
        {
            int padding = lastIndex - paddingIndex;
            fixedCharacters.set(padding, '=');
        }
        return fixedCharacters;
    }
    private static ArrayList<Character> lookupOnTable(ArrayList<Integer> indexArray)
    {
        ArrayList<Character> table = getB64Table();
        ArrayList<Character> characterArray = new ArrayList<Character>();
        for (Integer index : indexArray)
        {
            characterArray.add(table.get(index));
        }
        return characterArray;
    }
    private static ArrayList<Integer> makeIntegerArrayFromBinaryArray(ArrayList<String> binaryArray)
    {
        ArrayList<Integer> integerValues = new ArrayList<Integer>();
        for (String binaryValue : binaryArray)
        {
            Integer integerItem = BASE2.binaryToDecimal(binaryValue);
            integerValues.add(integerItem);
        }
        return integerValues;
    }
    private static String joinBits(ArrayList<String> binaryArray)
    {
        String joinedBinary = "";
        for (String binary : binaryArray)
        {
            joinedBinary += binary;
        }
        return joinedBinary;
    }
    private static ArrayList<String> addPaddingBits(ArrayList<String> byteArray, int paddingCount)
    {
        ArrayList<String> paddedByteArray = byteArray;
        int paddingBits = paddingCount * 6;
        String extras = GENERAL.repeatStr("0", paddingBits);
        paddedByteArray.add(extras);
        return paddedByteArray;
    }
    private static int calculatePaddingCount(ArrayList<String> byteArray)
    {
        int bytes = byteArray.size();
        int paddingCount = (3 - ((bytes) % 3)) % 3; //the last mod action ensures a full section wont get extras
        return paddingCount;
    }
    private static ArrayList<String> makeByteArrayFromIntegerArray(ArrayList<Integer> integerArray)
    {
        return makeBinaryArrayFromIntegerArray(integerArray, 8);
    }
    private static ArrayList<String> makeBinaryArrayFromIntegerArray(ArrayList<Integer> integerArray, int digits)
    {
        ArrayList<String> binaryValues = new ArrayList<String>();
        for (Integer integerValue : integerArray)
        {
            String binaryItem = BASE2.decimalToBinary((integerValue.intValue()), digits);
            binaryValues.add(binaryItem);
        }
        return binaryValues;
    }
    
}
