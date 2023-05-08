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
            String hexadecimalValue = decimalToHex(decimalValue, hexDigits);
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
