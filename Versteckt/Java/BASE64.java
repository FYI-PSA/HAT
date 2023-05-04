import java.util.ArrayList;
import java.util.Scanner;

public class BASE64 
{   
    public static final ArrayList<Character> getB64Table()
    {
        ArrayList<Character> table = new ArrayList<>(){};
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
    private static String getInput(Scanner input)
    {
        String data = input.nextLine();
        return data;
    }
    private static void print(String data)
    {
        System.out.print(data);
        System.out.flush();
        return;
    }
    private static String repeatStr(String repeatingString, int count)
    {
        String repeated = "";
        for (int repeat = 0; repeat < count; repeat ++)
        {
            repeated += repeatingString;
        }
        return repeated;
    }
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        print("[*] Base64 Converter\n\n");
        print("[@] Press 'D' to turn base64 encrypted content into a string\n");
        print("[@] Press 'E' to encrypt a string into base64\n");
        print("\n[?] > ");
        char choice = getInput(input).charAt(0);
        print("\n\n");
        if (choice == 'E')
        {
            print("[@] Enter your text\n");
            print("[?] > ");
            String data = getInput(input);
            print("[$] Done.\n\n");
            print(String.valueOf(encrypt(data)));
            print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'D')
        {
            print("[@] Enter your encrypted base64 string\n");
            print("[?] > ");
            String data = getInput(input);
            print("[$] Done.\n\n");
            print(String.valueOf(decrypt(data)));
            print("\n\n[!] Goodbye!\n\n");        
        }
        input.close();
        return;
    }
    public static String encrypt(String data)
    {
        String encrypted = "";
        ArrayList<Character> characters = makeCharacterArrayFromString(data);
        ArrayList<Integer> characterCodes = characterArrayToIntegerArray(characters);
        ArrayList<String> byteArray = makeByteArrayFromIntegerArray(characterCodes);
        int paddingCount = calculatePaddingCount(byteArray);
        ArrayList<String> paddedByteArray = addPaddingBits(byteArray, paddingCount);
        String allBitsString = joinBits(paddedByteArray);
        ArrayList<String> sixBitBinaryArray = makeChunksFromString(allBitsString, 6);
        ArrayList<Integer> sixBitAsciiArray = makeIntegerArrayFromBinaryArray(sixBitBinaryArray);
        ArrayList<Character> newCharacterArray = lookupOnTable(sixBitAsciiArray);
        ArrayList<Character> paddedCharacterArray = fixB64Array(newCharacterArray, paddingCount);
        encrypted = makeStringFromCharacterArray(paddedCharacterArray);
        return encrypted;
    }
    public static String decrypt(String data)
    {
        String decrypted = "";
        return decrypted;
    }
    private static String makeStringFromCharacterArray(ArrayList<Character> characterArray)
    {
        String data = "";
        int dataLength = characterArray.size();
        for (int characterIndex = 0; characterIndex < dataLength; characterIndex++)
        {
            data += (String.valueOf(characterArray.get(characterIndex)));
        }
        return data;
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
        ArrayList<Character> characterArray = new ArrayList<>();
        for (Integer index : indexArray)
        {
            characterArray.add(table.get(index));
        }
        return characterArray;
    }
    private static ArrayList<Integer> makeIntegerArrayFromBinaryArray(ArrayList<String> binaryArray)
    {
        ArrayList<Integer> integerValues = new ArrayList<>();
        for (String binaryValue : binaryArray)
        {
            Integer integerItem = BASE2.binaryToDecimal(binaryValue);
            integerValues.add(integerItem);
        }
        return integerValues;
    }
    private static ArrayList<String> makeChunksFromString(String originalString, int chunkSize)
    {
        ArrayList<String> newStringArray = new ArrayList<>();
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
        String extras = repeatStr("0", paddingBits);
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
        ArrayList<String> binaryValues = new ArrayList<>();
        for (Integer integerValue : integerArray)
        {
            String binaryItem = BASE2.decimalToBinary((integerValue.intValue()), digits);
            binaryValues.add(binaryItem);
        }
        return binaryValues;
    }
    /*
    private static ArrayList<Integer> addNToIntegerArray(ArrayList<Integer> integerArray, int n)
    {
        ArrayList<Integer> newArray = new ArrayList<>();
        for (Integer item : integerArray)
        {
            newArray.add((item + n));
        } 
        return newArray;
    }
    */
    private static ArrayList<Integer> characterArrayToIntegerArray(ArrayList<Character> characters)
    {
        ArrayList<Integer> integerValues = new ArrayList<>();
        for (Character character : characters) 
        {
            integerValues.add((int)(character.charValue()));
        }
        return integerValues;
    }
    private static ArrayList<Character> makeCharacterArrayFromString(String originalString)
    {
        ArrayList<Character> characters = new ArrayList<>();
        int dataLength = originalString.length();
        for (int characterIndex = 0; characterIndex < dataLength; characterIndex ++)
        {
            characters.add(originalString.charAt(characterIndex));
        }
        return characters;
    }
}
