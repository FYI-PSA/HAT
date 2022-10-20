import com.sun.source.doctree.*;
import java.nio.file.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import java.time.*;
import java.sql.*;
import java.io.*;


public class AlgorithmConcept
{


    public static int maximum_chain_value, minimum_chain_value;
    public static boolean debug_value;
    public static String file_name, file_path, parent_path;
    public static String error_message = "\n[!] Oops! Encountered an error! Here are the details: ";

    public static ArrayList<String> word_list = new ArrayList<String>();
    public static Scanner scan_obj = new Scanner(System.in);
    public static FileWriter writer_obj;
    public static File file_obj;

    public static String Ask()
    {
        return Ask("");
    }
    public static String Ask(String prefix)
    {
        Printer(prefix, false);
        return scan_obj.nextLine();
    }


    public static void Printer(String text)
    {
        Printer(text, true);
    }
    public static void Printer(String text, boolean new_line)
    {
        if (new_line) {System.out.println(text);}
        else         {System.out.print(text);}
    }


    public static void Starter()
    {
        System.out.println("[!] Welcome\n[@] This is a prototype to test the wordlist generation algorithm in java");
        InputCollector();
        NameCreator();
        FileCreator();
        Algorithm();
    }

    public static void InputCollector()
    {
        word_list.add(Ask("[?] First input : "));
        while (true)
        {
            String temp_ask = Ask("[@] Type \"Stop Asking\" or leave the input blank to stop.\n[?] Other input : ");
            String[] void_input =
            {
                "",
                "\n",
                " ",
                "STOP ASKING", "STOPASKING", "STOP_ASKING",
                "Stop Asking", "StopAsking", "Stop_Asking",
                "Stop asking", "Stopasking", "Stop_asking",
                "stop asking", "stopasking", "stop_asking"
            };
            if (Arrays.asList(void_input).contains(temp_ask))
            {
                Printer("[#] Stopped asking.\n[$] Thank you for your input.");
                break;
            }
            else
            {
                word_list.add(temp_ask);
                Printer("[$] Successfully added "+temp_ask+" to word list.");
            }
        }
    }

    public static void NameCreator()
    {
        file_name = "temporary.txt";
        parent_path = System.getProperty("user.home") + "\\Documents\\Generated-Dictionaries\\Java-Version\\";
        file_path = parent_path + file_name;
    }

    public static void FileCreator()
    {
        try
        {
            Files.createDirectories(Paths.get(parent_path));
            file_obj = new File(file_path);
            writer_obj = new FileWriter(file_path);
            if (file_obj.createNewFile())
            {
                Printer("[$] File created successfully at \'"+parent_path+"\' ! named "+file_name);
            }
            else
            {
                Printer("[#] File with name of "+file_name+" already made in location "+parent_path);
            }
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
        }
    }

    public static void Writer(String text)
    {
        Writer(text, true);
    }
    public static void Writer(String text, boolean new_line)
    {
        try
        {
            writer_obj.write(text);
            if (new_line)
            {
                writer_obj.write("\n");
            }

        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
        }
    }
    public static void Algorithm()
    {
        for (String word : word_list)
        {
            Writer(word);
        }
        try
        {
            writer_obj.close();
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
        }
    }
    public static void main(String [] args)
    {
        Starter();
    }
}