import java.lang.Math;
import java.util.Scanner;

public class BASE2 
{
    private static String getInput(Scanner input)
    {
        String data = input.nextLine();
        return data;
    }
    private static void print(String data)
    {
        System.out.flush();
        System.out.print(data);
        return;
    }
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        print("[*] Binary Converter\n\n");
        print("[@] Press 'B' to turn binary into a number\n");
        print("[@] Press 'N' to turn a number into a byte\n");
        print("\n[?] > ");
        char choice = getInput(input).charAt(0);
        print("\n\n");
        if (choice == 'B')
        {
            print("[@] Enter the binary string\n");
            print("[?] > ");
            String binaryValue = getInput(input);
            print("[$] Done.\n\n");
            print(String.valueOf(binaryToDecimal(binaryValue)));
            print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'N')
        {
            print("[@] Enter the number\n");
            print("[?] > ");
            int decimalValue = Integer.parseInt(getInput(input));
            print("[@] How many digits of binary? (default: 8)\n");
            print("[@] Leave blank if you don't know\n");
            print("[?] > ");
            int binaryDigits = 8;
            String digitInput = getInput(input);
            if (!(digitInput.equals("")))
            {
                binaryDigits = Integer.parseInt(digitInput);
            }
            String binaryValue = decimalToBinary(decimalValue, binaryDigits);
            print("[$] Done.\n\n");
            print(binaryValue);
            print("\n\n[!] Goodbye!\n\n");
        }
        input.close();
        return;
    }
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
}