import java.lang.Math;
import java.util.Scanner;

/**
 * BASE2
 */
public class BASE2 
{
    private static String getInput()
    {
        System.out.flush();
        Scanner input = new Scanner(System.in);
        String data = input.nextLine();
        return data;
    }
    private static void print(String data)
    {
        System.out.print(data);
        return;
    }
    public static void main(String[] args) 
    {
        print("[*] Binary Converter\n\n");
        print("[@] Press 'B' to turn binary into a number\n");
        print("[@] Press 'N' to turn a number into a byte\n");
        print("\n[?] > ");
        char choice = getInput().charAt(0);
        print("\n\n");
        if (choice == 'B')
        {
            print("[@] Enter the binary string\n");
            print("[?] > ");
            String binaryValue = getInput();
            int decimalValue = binaryToDecimal(binaryValue);
            print("[$] Done.\n\n");
            print(String.valueOf(decimalValue));
            print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'N')
        {
            print("[@] Enter the number\n");
            print("[?] > ");
            int decimalValue = Integer.parseInt(getInput());
            print("[@] How many digits of binary? (default: 8)\n");
            print("[@] Leave blank if you don't know\n");
            print("[?] > ");
            int binaryDigits = 8;
            String input = getInput();
            if (!(input.equals("")))
            {
                binaryDigits = Integer.parseInt(input);
            }
            String binaryValue = decimalToBinary(decimalValue, binaryDigits);
            print("[$] Done.\n\n");
            print(binaryValue);
            print("\n\n[!] Goodbye!\n\n");
        }
        return;
    }
    public static String decimalToBinary(int decimalInput)
    {
        return decimalToBinary(decimalInput, 8);
    }
    public static String decimalToBinary(int decimalInput, int binaryDigits)
    {
        String binaryOutput = "";
        for (int currentDigitIndex = 0; currentDigitIndex < binaryDigits; currentDigitIndex++)
        {
            int currentDigitImportance = 8 - (currentDigitIndex + 1);
            int currentBinaryValue = (int)(Math.pow((double)2, (double)currentDigitImportance));
            if (decimalInput >= currentBinaryValue)
            {
                decimalInput = decimalInput - (int)currentBinaryValue;
                binaryOutput += "1";
                continue;
            }
            binaryOutput += "0";
        }
        return binaryOutput;
    }
    public static int binaryToDecimal(String binaryInput)
    {
        int decimalOutput = 0;
        int binaryDigits = binaryInput.length();
        for (int currentDigitIndex = 0; currentDigitIndex < binaryDigits; currentDigitIndex++)
        {
            if ((binaryInput.charAt(currentDigitIndex)) == '1')
            {
                int currentDigitImportance = binaryDigits - (currentDigitIndex + 1);
                int currentBinaryValue = (int)(Math.pow((double)2, (double)currentDigitImportance));
                decimalOutput += currentBinaryValue;
            }
        }
        return decimalOutput;
    } 
}