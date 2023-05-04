import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BASE16 
{
    public static final ArrayList<String> allHexadecimalDigits = new ArrayList<>(Arrays.asList("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"));
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
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        print("[*] Hexadecimal Converter\n\n");
        print("[@] Press 'H' to turn a hexadecimal value into a number\n");
        print("[@] Press 'N' to turn a number into a hex byte\n");
        print("\n[?] > ");
        char choice = getInput(input).charAt(0);
        print("\n\n");
        if (choice == 'H')
        {
            print("[@] Enter the hex string\n");
            print("[?] > ");
            String hexadecimalValue = getInput(input);
            print("[$] Done.\n\n");
            print(String.valueOf(hexToDecimal(hexadecimalValue)));
            print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'N')
        {
            print("[@] Enter the number\n");
            print("[?] > ");
            int decimalValue = Integer.parseInt(getInput(input));
            print("[@] How many digits of hex? (default: 2)\n");
            print("[@] Leave blank if you don't know\n");
            print("[?] > ");
            int hexDigits = 2;
            String digitInput = getInput(input);
            if (!(digitInput.equals("")))
            {
                hexDigits = Integer.parseInt(digitInput);
            }
            String hexadecimalValue = decimalToHex(decimalValue, hexDigits);
            print("[$] Done.\n\n");
            print(hexadecimalValue);
            print("\n\n[!] Goodbye!\n\n");
        }
        input.close();
        return;
    }
    public static String decimalToHex(int decimal)
    {
        return decimalToHex(decimal, 2);
    }
    public static String decimalToHex(int decimal, int hexadecimalDigits)
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
        return hexOutput;
    }
    public static int hexToDecimal(String hexadecimal)
    {
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
}
