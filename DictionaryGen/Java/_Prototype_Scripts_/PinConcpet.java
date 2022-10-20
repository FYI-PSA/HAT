import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PinConcpet
{
    static int DigitCounter(int input_number)
    {
        if (input_number == 0) {return 1;}
        int digit_amount = 0;
        while (true)
        {
            if (input_number > 0)
            {
                digit_amount ++;
                input_number = input_number/10;
            }
            else
            {
                break;
            }
        }
        return digit_amount;
    }


    static boolean PowerOfTen(int input_number)
    {
        boolean is_powered = true;
        String string_value = Integer.toString(input_number);
        int value_length = DigitCounter(input_number);
        for (int digit = 0; digit < value_length; digit++)
        {
            String char_value = String.valueOf(string_value.charAt(digit));
            if (digit == 0) {if (!char_value.equals("1")) {is_powered = false;}}
            else            {if (!char_value.equals("0")) {is_powered = false;}}
        }
        return is_powered;
    }


    public static void main(String [] args)
    {
        try
        {
            Scanner scanner_obj = new Scanner(System.in);
            System.out.println("Java!");
            String file_name = "test_dictionary.txt";
            File file_obj = new File(file_name);
            boolean previously_existed = !file_obj.createNewFile();
            System.out.println("Previously existed : "+previously_existed);
            FileWriter writer_obj = new FileWriter(file_name);
            System.out.print("Maximum: "); int Max = scanner_obj.nextInt();
            for (int number = 0; number <= Max; number++)
            {
                if (PowerOfTen(number))
                {
                    for (int d = 0; d < DigitCounter(number); d++)
                    {
                        writer_obj.write("0");
                    }
                    writer_obj.write("\n");
                }
                String next_entry = Integer.toString(number)+'\n';
                writer_obj.write(next_entry);
            } writer_obj.close();
            System.out.println("Done!");
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }
    }
}