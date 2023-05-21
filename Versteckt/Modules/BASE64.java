import java.util.Scanner;

public class BASE64 
{   

    /* -------------------------------------------------------------------------------------- */
    /* BASE64-TABLE-------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static Character[] getB64Table()
    {
        Character[] table = new Character[64];
        int index = 0;
        for (char uppercase = 'A'; uppercase < ('Z' + 1); uppercase++)
        {
            table[index] = uppercase;
            index++;
        }
        for (char lowercase = 'a'; lowercase < ('z' + 1); lowercase++)
        {
            table[index] = lowercase;
            index++;
        }
        for (char number = '0'; number < ('9' + 1); number++)
        {
            table[index] = number;
            index++;
        }
        table[index] = '+';
        table[(index + 1)] = '/';
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
        Character[] characters = GENERAL.makeCharacterArrayFromString(data);
        Integer[] characterCodes = GENERAL.characterArrayToIntegerArray(characters);
        String[] byteArray = makeByteArrayFromIntegerArray(characterCodes);
        int paddingCount = calculatePaddingCount(byteArray);
        String[] paddedByteArray = addPaddingBits(byteArray, paddingCount);
        String allBitsString = joinBits(paddedByteArray);
        String[] sixBitBinaryArray = GENERAL.makeChunksFromString(allBitsString, 6);
        Integer[] sixBitAsciiArray = makeIntegerArrayFromBinaryArray(sixBitBinaryArray);
        Character[] newCharacterArray = lookupOnTable(sixBitAsciiArray);
        Character[] paddedCharacterArray = fixB64Array(newCharacterArray, paddingCount);
        encrypted = GENERAL.makeStringFromCharacterArray(paddedCharacterArray);
        return encrypted;
    }
    public static String decrypt(String data)
    {
        String decrypted = "";
        Character[] paddedCharacterArray = GENERAL.makeCharacterArrayFromString(data);
        int paddingCount = countPaddings(paddedCharacterArray);
        Character[] noPaddingCharacterArray = replacePaddings(paddedCharacterArray, paddingCount);
        Integer[] characterCodeArray = indexLookupOnTable(noPaddingCharacterArray);
        String[] sixBitBinaryArray = makeBinaryArrayFromIntegerArray(characterCodeArray, 6);
        String allBitsString = joinBits(sixBitBinaryArray);
        String[] paddedByteArray = GENERAL.makeChunksFromString(allBitsString, 8);
        String[] byteArray = removePaddingBytes(paddedByteArray);
        Integer[] originalAsciiArray = makeIntegerArrayFromBinaryArray(byteArray);
        Character[] originalCharacterArray = GENERAL.integerArrayToCharacterArray(originalAsciiArray);
        decrypted = GENERAL.makeStringFromCharacterArray(originalCharacterArray);
        return decrypted;
    }

    /* -------------------------------------------------------------------------------------- */
    /* CALCULATION-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    private static String[] removePaddingBytes(String[] paddedBytes)
    {
        int length = paddedBytes.length;
        String[] normalByteArray = new String[length];
        int index = 0;
        for (String byteString : paddedBytes)
        {
            if ((byteString.length()) == 8)
            {
                Boolean notZero = false;
                for (int bitIndex = 0; bitIndex < 8; bitIndex ++)
                {
                    if (byteString.charAt(bitIndex) != '0')
                    {
                        notZero = true;
                        break;
                    }
                }
                if (notZero) 
                {
                    normalByteArray[index] = byteString;
                    index++;
                }
            }
        }
        int actualLength = index;
        String[] actualByteArray = new String[actualLength];
        for (int actualByteIndex = 0; actualByteIndex < actualLength; actualByteIndex ++)
        {
            actualByteArray[actualByteIndex] = normalByteArray[actualByteIndex];
        }
        return actualByteArray;
    }
    private static Integer[] indexLookupOnTable(Character[] characterArray)
    {
        Character[] table = getB64Table();
        int length = table.length;
        int indexIndex = 0;
        Integer[] indices = new Integer[length];
        for (Character currentB64Character : characterArray)
        {
            int index = GENERAL.findIndex(currentB64Character, table);
            indices[indexIndex] = index;
            indexIndex++;
        }
        return indices;
    }
    private static Character[] replacePaddings(Character[] paddedCharacterArray, int paddings)
    {
        int length = paddedCharacterArray.length;
        Character[] normalCharacters = paddedCharacterArray.clone();
        int lastIndex = length - 1;
        for (int paddingIndex = 0; paddingIndex < paddings; paddingIndex++)
        {
            int padding = lastIndex - paddingIndex;
            normalCharacters[padding] = 'A';
        }
        return normalCharacters;
    }
    private static int countPaddings(Character[] paddedCharacterArray)
    {
        int paddings = 0;
        int length = paddedCharacterArray.length;
        int lastIndex = length - 1;
        for (int characterIndex = lastIndex; characterIndex >= 0; -- characterIndex)
        {
            char currentCharacter = paddedCharacterArray[characterIndex].charValue();
            if (currentCharacter == '=')
            {
                paddings ++;
            }
        }
        return paddings;
    }
    private static Character[] fixB64Array(Character[] characterArray, int paddingCount)
    {
        int length = characterArray.length;
        Character[] fixedCharacters = characterArray.clone();
        int lastIndex = length - 1;
        for (int paddingIndex = 0; paddingIndex < paddingCount; paddingIndex++)
        {
            int padding = lastIndex - paddingIndex;
            fixedCharacters[padding] = '=';
        }
        return fixedCharacters;
    }
    private static Character[] lookupOnTable(Integer[] indexArray)
    {
        int length = indexArray.length;
        Character[] table = getB64Table();
        Character[] characterArray = new Character[length];
        for (int index = 0; index < length; index ++)
        {
            characterArray[index] = table[indexArray[index]];
        }
        return characterArray;
    }
    private static Integer[] makeIntegerArrayFromBinaryArray(String[] binaryArray)
    {
        int length = binaryArray.length;
        Integer[] integerValues = new Integer[length];
        for (int index = 0; index < length; index ++)
        {
            String binaryValue = binaryArray[index];
            Integer integerItem = BASE2.binaryToDecimal(binaryValue);
            integerValues[index] = integerItem;
        }
        return integerValues;
    }
    private static String joinBits(String[] binaryArray)
    {
        String joinedBinary = "";
        for (String binary : binaryArray)
        {
            joinedBinary += binary;
        }
        return joinedBinary;
    }
    private static String[] addPaddingBits(String[] byteArray, int paddingCount)
    {
        if (paddingCount < 1)
        {
            return byteArray.clone();
        }
        int paddingBits = paddingCount * 6;
        int extraLength = (int)(Math.ceil((paddingBits/8)));
        int normalLength = byteArray.length;
        int paddedBytesLength = normalLength + extraLength;
        String[] paddedByteArray = new String[paddedBytesLength];
        for (int index = 0; index < normalLength; index++)
        {
            paddedByteArray[index] = byteArray[index];
        }
        String extras = GENERAL.repeatStr("0", paddingBits);
        String[] byteSplitExtras = GENERAL.makeChunksFromString(extras, 8);
        for (int index = normalLength; index < paddedBytesLength; index++)
        {
            int extraIndex = index - normalLength;
            paddedByteArray[index] = byteSplitExtras[extraIndex];
        }
        return paddedByteArray;
    }
    private static int calculatePaddingCount(String[] byteArray)
    {
        int bytes = byteArray.length;
        int paddingCount = (3 - ((bytes) % 3)) % 3; //the last mod action ensures a full section wont get a 3 padding
        return paddingCount;
    }
    private static String[] makeByteArrayFromIntegerArray(Integer[] integerArray)
    {
        return makeBinaryArrayFromIntegerArray(integerArray, 8);
    }
    private static String[] makeBinaryArrayFromIntegerArray(Integer[] integerArray, Integer digits)
    {
        int length = integerArray.length;
        String[] binaryValues = new String[length];
        for (int index = 0; index < length; index ++)
        {
            Integer integerValue = integerArray[index];
            String binaryItem = BASE2.decimalToBinary(integerValue, digits);
            binaryValues[index] = binaryItem;
        }
        return binaryValues;
    }
    
}
