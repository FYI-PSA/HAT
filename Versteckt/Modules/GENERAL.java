import java.util.ArrayList;
import java.util.Scanner;
import java.security.SecureRandom;

public class GENERAL 
{
    public static String getInput(Scanner input)
    {
        String data = input.nextLine().strip();
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
    public static Integer[] integerArrayListToVanillaIntegerArray(ArrayList<Integer> originalArrayList)
    {
        int length = originalArrayList.size();
        Integer[] vanillaArray = new Integer[length];
        for (int index = 0; index < length; index ++)
        {
            vanillaArray[index] = originalArrayList.get(index);
        }
        return vanillaArray;
    }
    public static Integer findIndex(Character target, Character[] itemsArray)
    {
        int itemsLength = itemsArray.length;
        int targetIndex = -1;
        for (int itemsCharacterIndex = 0; itemsCharacterIndex < itemsLength; itemsCharacterIndex ++)
        {
            if (target == itemsArray[itemsCharacterIndex])
            {
                targetIndex = itemsCharacterIndex;
                break;
            }
        }
        return targetIndex;
    }
    public static String repeatStr(String repeatingString, int count)
    {
        String repeated = "";
        for (int repeat = 0; repeat < count; repeat ++)
        {
            repeated += repeatingString;
        }
        return repeated;
    }
    public static String[] makeChunksFromString(String originalString, Integer chunkSize)
    {
        int length = originalString.length();
        int index = 0;
        String[] newStringArray = new String[length];
        int characters = originalString.length(); 
        int bufferCount = 0;
        String bufferString = "";
        for (int characterIndex = 0; characterIndex < characters; characterIndex++)
        {
            char currentCharacter = originalString.charAt(characterIndex);
            if (bufferCount == (chunkSize.intValue()))
            {
                newStringArray[index] = bufferString;
                index ++;
                bufferString = "";
                bufferCount = 0;
            }
            bufferCount ++;
            bufferString += (String.valueOf(currentCharacter));
        }
        if (bufferCount != 0)
        {
            newStringArray[index] = bufferString;
            index ++;
            bufferString = "";
            bufferCount = 0;
        }  
        return newStringArray;
    }
    public static Character[] integerArrayToCharacterArray(Integer[] integerValues)
    {
        int length = integerValues.length;
        Character[] characters = new Character[length];
        for (int index = 0; index < length; index ++) 
        {
            characters[index] = (char)(integerValues[index].intValue());
        }
        return characters;
    }
    public static Integer[] characterArrayToIntegerArray(Character[] characters)
    {
        int length = characters.length;
        Integer[] integerValues = new Integer[length];
        for (int index = 0; index < length; index ++)
        {
            integerValues[index] = (int)(characters[index].charValue());
        }
        return integerValues;
    }
    public static Character[] makeCharacterArrayFromString(String originalString)
    {
        int length = originalString.length();
        Character[] characters = new Character[length];
        for (int index = 0; index < length; index ++)
        {
            characters[index] = originalString.charAt(index);
        }
        return characters;
    }
    public static String makeStringFromCharacterArray(Character[] characterArray)
    {
        int length = characterArray.length;
        String data = "";
        for (int index = 0; index < length; index ++)
        {
            data += String.valueOf(characterArray[index]);
        }
        return data;
    }
    public static Integer secureChooseInteger(Integer[] integerList, SecureRandom secureRandomInstance)
    {
        int arraySize = integerList.length;
        int randomIndex = secureRandomInstance.nextInt(arraySize);
        Integer randomItem = integerList[randomIndex];
        return randomItem;
    }
    public static pair<Integer, Integer> secureChoosePair(Integer[] integerList, SecureRandom secureRandomInstance)
    {
        int listSize = integerList.length;
        pair<Integer, Integer> items = new pair<Integer, Integer>();
        items.first = integerList[secureRandomInstance.nextInt(listSize)];
        items.second = integerList[secureRandomInstance.nextInt(listSize)];
        return items;
    }
    public static Integer SlowModularExponentiation(Integer X, Integer P, Integer M)
    {
        if (P < 3)
        {
            return (((int)(Math.pow(X.doubleValue(), P.doubleValue()))) % M);
        }
        int repeat = P - 1;
        int mainleftover = X % M;
        int answer = X;
        for (int cycle = 0; cycle < repeat; cycle ++)
        {
            answer = (answer % M);
            answer = answer * mainleftover;
        } 
        answer = answer % M;
        return answer;
    }
    public static Integer ModularExponentiation(Integer X, Integer P, Integer M)
    {
        int answer = 1;
        X = X % M;
        if (X == 0)
        {
            return 0;
        }
        while (P > 0)
        {
            if ((P & 1) != 0)
            {
                answer = (answer * X) % M;
            }
            P = P >> 1;
            X = (X * X) % M;
        }
        return answer;
    }
    public static Integer ModularMultiplicativeInverse(Integer X, Integer M)
    { // Infinite values of A exist with a distance of M, but it's worthless as the results and cracking of all of them is the same
        Integer C = X % M;
        Integer A = 0;
        for (A = 1; A < M; A ++)
        {
            Integer L = (A * C) % M;
            if (L == 1)
            {
                break;
            }
        }
        return A;
    }
    public static Integer GreatestCommonDevisor(Integer A, Integer B)
    { // Euclid's basic GCD algorithm
        if (B == 0)
        { 
            return A;
        }
        return GreatestCommonDevisor(B, A%B);
    }
    public static Integer[] strongValues(Integer[] allValues)
    {
        int length = allValues.length;
        int start = 2 * (length/3);
        int wantedLength = (int)(length - start);
        int i = 0;
        Integer[] wantedItems = new Integer[wantedLength];
        for (Integer index = start; index < length; index++)
        {
            Integer item = allValues[index];
            wantedItems[i] = item; i++;
        }
        return wantedItems;
    }
}
