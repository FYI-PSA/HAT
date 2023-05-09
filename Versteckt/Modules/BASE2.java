import java.lang.Math;
import java.util.Scanner;

public class BASE2 
{

    /* -------------------------------------------------------------------------------------- */
    /* MAIN---------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        GENERAL.print("[*] Binary Converter\n\n");
        GENERAL.print("[@] Enter 'B' to turn binary into a number\n");
        GENERAL.print("[@] Enter 'N' to turn a number into a byte\n");
        GENERAL.print("\n[?] > ");
        char choice = GENERAL.getInput(input).charAt(0);
        GENERAL.print("\n\n");
        if (choice == 'B')
        {
            GENERAL.print("[@] Enter the binary string\n");
            GENERAL.print("[?] > ");
            String binaryValue = GENERAL.getInput(input);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(String.valueOf(binaryToDecimal(binaryValue)));
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'N')
        {
            GENERAL.print("[@] Enter the number\n");
            GENERAL.print("[?] > ");
            int decimalValue = Integer.parseInt(GENERAL.getInput(input));
            GENERAL.print("[@] How many digits of binary? (default: 8)\n");
            GENERAL.print("[@] Leave blank if you don't know\n");
            GENERAL.print("[?] > ");
            int binaryDigits = 8;
            String digitInput = GENERAL.getInput(input);
            if (!(digitInput.equals("")))
            {
                binaryDigits = Integer.parseInt(digitInput);
            }
            String binaryValue = decimalToBinary(decimalValue, binaryDigits);
            GENERAL.print("[$] Done.\n\n");
            GENERAL.print(binaryValue);
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        input.close();
        return;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN-BASE2-METHODS----------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static String decimalToBinary(int decimal)
    {
        return decimalToBinary(decimal, 8);
    }
    public static String decimalToBinary(int decimal, int binaryDigits)
    {
        String binaryOutput = "";
        for (int currentDigitIndex = 0; currentDigitIndex < binaryDigits; currentDigitIndex++)
        {
            int currentDigitImportance = binaryDigits - (currentDigitIndex + 1);
            int currentBinaryValue = (int)(Math.pow((double)2, (double)currentDigitImportance));
            if (decimal >= currentBinaryValue)
            {
                decimal = decimal - (int)currentBinaryValue;
                binaryOutput += "1";
                continue;
            }
            binaryOutput += "0";
        }
        return binaryOutput;
    }
    public static int binaryToDecimal(String binaryString)
    {
        int decimalOutput = 0;
        int binaryDigits = binaryString.length();
        for (int currentDigitIndex = 0; currentDigitIndex < binaryDigits; currentDigitIndex++)
        {
            if ((binaryString.charAt(currentDigitIndex)) == '1')
            {
                int currentDigitImportance = binaryDigits - (currentDigitIndex + 1);
                int currentBinaryValue = (int)(Math.pow((double)2, (double)currentDigitImportance));
                decimalOutput += currentBinaryValue;
            }
        }
        return decimalOutput;
    } 
    public static String dataStringToBinaryString(String data)
    {
        return dataStringToBinaryString(data, " ");
    }
    public static String dataStringToBinaryString(String data, int digits)
    {
        return dataStringToBinaryString(data, " ", 8);
    }
    public static String dataStringToBinaryString(String data, String separator)
    {
        return dataStringToBinaryString(data, separator, 8);
    }
    public static String dataStringToBinaryString(String data, String separator, int digits)
    {
        String encrypted = "";
        int dataLength = data.length();
        for(int characterIndex = 0; characterIndex < dataLength; characterIndex++)
        {
            Character currentCharacter = data.charAt(characterIndex);
            int characterAscii = ((int)currentCharacter.charValue());
            String binaryString = decimalToBinary(characterAscii, digits);
            encrypted += binaryString;
            if (characterIndex == (dataLength-1))
            {
                continue;
            }
            encrypted += separator;
        }
        return encrypted;
    }
    public static String binaryStringToDataString(String binaryString, String separator)
    {
        String decrypted = "";
        String[] binaryItems = binaryString.split(separator);
        for (String binaryItem : binaryItems)
        {
            int asciiCharacter = binaryToDecimal(binaryItem);
            char currentCharacter = ((char)asciiCharacter);
            String current = String.valueOf(currentCharacter);
            decrypted += current;
        }
        return decrypted;
    }
    
}