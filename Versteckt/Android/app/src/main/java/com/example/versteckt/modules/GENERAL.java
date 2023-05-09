package com.example.versteckt.modules;

import java.util.ArrayList;
import java.util.Scanner;
import java.security.SecureRandom;

public class GENERAL 
{
    public static String getInput(Scanner input)
    {
        String data = input.nextLine();
        return data;
    }
    public static void print(pair<Integer, Integer> data)
    {
        print(data.first); print(" , "); print(data.second);
    }
    public static void print(Integer data)
    {
        print(String.valueOf(data));
    }
    public static void print(Character data)
    {
        print(String.valueOf(data));
    }
    public static void print(Double data)
    {
        print(String.valueOf(data));
    }
    public static void print(String data)
    {
        System.out.print(data);
        System.out.flush();
        return;
    }
    /*
    public static ArrayList<Integer> addNToIntegerArray(ArrayList<Integer> integerArray, int n)
    {
        ArrayList<Integer> newArray = new ArrayList<Integer>();
        for (Integer item : integerArray)
        {
            newArray.add((item + n));
        } 
        return newArray;
    }
    */
    public static String repeatStr(String repeatingString, int count)
    {
        String repeated = "";
        for (int repeat = 0; repeat < count; repeat ++)
        {
            repeated += repeatingString;
        }
        return repeated;
    }
    public static ArrayList<String> makeChunksFromString(String originalString, int chunkSize)
    {
        ArrayList<String> newStringArray = new ArrayList<String>();
        int characters = originalString.length(); 
        int bufferCount = 0;
        String bufferString = "";
        for (int characterIndex = 0; characterIndex < characters; characterIndex++)
        {
            char currentCharacter = originalString.charAt(characterIndex);
            if (bufferCount == chunkSize)
            {
                newStringArray.add(bufferString);
                bufferString = "";
                bufferCount = 0;
            }
            bufferCount ++;
            bufferString += (String.valueOf(currentCharacter));
        }
        if (bufferCount != 0)
        {
            newStringArray.add(bufferString);
            bufferString = "";
            bufferCount = 0;
        }  
        return newStringArray;
    }
    public static ArrayList<Character> integerArrayToCharacterArray(ArrayList<Integer> integerValues)
    {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (Integer integerValue : integerValues) 
        {
            characters.add(((char)(integerValue.intValue())));
        }
        return characters;
    }
    public static ArrayList<Integer> characterArrayToIntegerArray(ArrayList<Character> characters)
    {
        ArrayList<Integer> integerValues = new ArrayList<Integer>();
        for (Character character : characters) 
        {
            integerValues.add((int)(character.charValue()));
        }
        return integerValues;
    }
    public static ArrayList<Character> makeCharacterArrayFromString(String originalString)
    {
        ArrayList<Character> characters = new ArrayList<Character>();
        int dataLength = originalString.length();
        for (int characterIndex = 0; characterIndex < dataLength; characterIndex ++)
        {
            characters.add(originalString.charAt(characterIndex));
        }
        return characters;
    }
    public static String makeStringFromCharacterArray(ArrayList<Character> characterArray)
    {
        String data = "";
        int dataLength = characterArray.size();
        for (int characterIndex = 0; characterIndex < dataLength; characterIndex++)
        {
            data += (String.valueOf(characterArray.get(characterIndex)));
        }
        return data;
    }
    public static Integer secureChooseInteger(ArrayList<Integer> integerList, SecureRandom secureRandomInstance)
    {
        int listSize = integerList.size();
        int randomIndex = secureRandomInstance.nextInt(listSize);
        Integer randomItem = integerList.get(randomIndex);
        return randomItem;
    }
    /*
    public static pair<Integer, Integer> secureChoosePair(ArrayList<Integer> integerList)
    {
        int listSize = integerList.size();
        pair<Integer, Integer> items = new pair<Integer, Integer>();
        pair<Integer, Integer> itemsIndex = new pair<Integer, Integer>();
        Random seed = new Random();
        itemsIndex.first = seed.nextInt(listSize);
        itemsIndex.second = seed.nextInt(listSize);
        items.first = integerList.get(itemsIndex.first);
        items.second = integerList.get(itemsIndex.second);
        return items;
    }
    */
    public static Integer GCD(Integer A, Integer B)
    { // euclid's algorithm
        if (B == 0)
        { 
            return A;
        }
        return GCD(B, A%B);
    }
    public static ArrayList<Integer> strongValues(ArrayList<Integer> allValues)
    {
        int length = allValues.size();
        ArrayList<Integer> wantedItems = new ArrayList<Integer>(){};
        for (Integer index = (2*(length/3)); index < (length); index++)
        {
            Integer item = allValues.get(index);
            wantedItems.add(item);
        }
        return wantedItems;        
    }
}
