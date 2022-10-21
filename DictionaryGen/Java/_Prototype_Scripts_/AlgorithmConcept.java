import java.util.concurrent.*;
import java.nio.file.*;
import java.lang.*;
import java.text.*;
import java.util.*;
import java.io.*;


/*
TODO:
 -Work with runtime args to make some things faster for experienced users
 -Add a man page for runtime args
 -(try to) Make into UI app
 -(try to) Make for mobile
*/

public class AlgorithmConcept
{
    //public static int maximum_chain_value, minimum_chain_value;
    public static boolean debug_value;
    public static String file_name, file_path, parent_path;
    public static String error_message = "\n[!] Oops! Encountered an error! Here are the details: ";
    public static ArrayList<String> word_list = new ArrayList<>(), wordlist_list = new ArrayList<>();
    public static List<String> file_data;
    public static String file_contents;
    public static Scanner scan_obj = new Scanner(System.in);
    public static FileWriter writer_obj;
    public static File file_obj;
    //public static String Ask() {return Ask("");}
    public static String Ask(String prefix)
    {
        Printer(prefix, false);
        return scan_obj.nextLine();
    }
    public static void Printer() { Printer(""); }
    //public static void Printer(boolean new_line) { Printer("", new_line); }
    public static void Printer(String text)
    {
        Printer(text, true);
    }
    public static void Printer(String text, boolean new_line)
    {
        if (new_line) {System.out.println(text);}
        else          {System.out.print(text);  }
    }



    public static void Starter() throws Exception
    {
        debug_value = true;
        boolean defaulted_list = true;
        if (defaulted_list)
        {
            String[] items = {"Mahan", "mahan", "MAHAN", "talebi", "TALEBI", "Talebi", "1383", "83", "2004", "04", "-", "_"};
            for (String item : (Arrays.stream(items).toList()))
            {
                word_list.add(item);
                Printer(item);
            }
        }
        System.out.println("[!] Welcome\n[@] This is a prototype to test the wordlist generation algorithm in java");
        InputCollector();
        NameCreator();
        boolean file_exists = FileCreator();
        Override();
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

    /*
    TODO:
     //this is just to find it with intellij easily
     METHOD TREE:
        Main:
            Greeter:
                Collector:
                    Name:
                        CreateAttempt:
                            FileEXISTS:
                                CANCEL:
                                OVERRIDE:
                                APPEND:
                                VIEW:
                            FileNOTFOUND:
                                FreshCreation:


     */


    /*
    TODO:
     If file exists:
     --[view mode is only enabled by a input bool for the function]
     --Ask to view / override / append / cancel:
     --if CANCEL:
     ----Re-init The chain starting back from NameCreator
     //THIS NEEDS a REVAMP FOR CAlLING METHODS FROM OTHERS
     //WHILE WE'RE AT REVAMPING, TURN FOLDER CREATION INTO A METHOD THAT TAKES PARENT PATHS AS INPUT
     --if VIEW:
     ----Save file data using a sep method
     ----Display that
     ----Re-init this module with the view_bool set to FALSE
     --if OVERRIDE:
     ----Ask if BACKUP or NO:
     ------Save file data
     ------Check for dir at parent_path+"/backup"
     ------Recreate file data there
     ----Initialize FileWriter
     ----CONTINUE
     --if APPEND:
     ----Save file data
     ----Ask if BACKUP of original or NO:
     ------Check for dir at parent_path+"/backup"
     ------Recreate file data there
     ----Initialize FileWriter
     ----Write previous data
     ----CONTINUE
    */





    public static boolean FileCreator() throws Exception {
        boolean return_value = false;
        try
        {
            Files.createDirectories(Paths.get(parent_path));
            file_obj = new File(file_path);
            writer_obj = new FileWriter(file_path);
            if (file_obj.createNewFile())
            {
                Printer("[$] File created successfully at '"+parent_path+"' ! named "+file_name);
            }
            else
            {
                Printer("[#] Looks like file with the name of "+file_name+" already exists at "+parent_path);
                return_value = true;
            }
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 101] : Program stopped after meeting an error with creating the file!");
        }
        return return_value;
    }
    public static void FileExists() throws Exception
    {
        String default_mode = "Append";
        String temp_mode = Ask
        (
            "[!] What would you like to do now?"
            +"\n[@] Enter 'V' → View file contents"
            +"\n[@] Enter 'A' → Append to end of file"
            +"\n[@] Enter 'O' → Override contents of file"
            +"\n[%] Leave empty or enter 'D' to default to "+default_mode+" mode"
            +"\n\n[?] Enter :  "
        );
        //String[] append_entries = {"D","\"D\"","'D'","d","\"d\"","'d'","","''","\"\""," ","' '","\" \"","A","\"A\"","'A'","a","\"a\"","'a'"};
        //if the input is not understandable, just use append. no need to check for that separately!
        String[] view_entries = {"V","\"V\"","'V'","v","\"v\"","'v'"};
        String[] override_entries = {"O","\"O\"","'O'","o","\"o\"","'v'"};
        List<String> view = Arrays.asList(view_entries);
        List<String> override = Arrays.asList(override_entries);
        if (view.contains(temp_mode))
        {//view mode
            if (debug_value) {Printer("Reader");}
            Reader();
        }
        else if (override.contains(temp_mode))
        {//override mode
            if (debug_value) {Printer("Override");}
            Override();
        }
        else
        {//append mode
            if (debug_value) {Printer("Append");}
            Printer("A");
        }
    }
    public static void Override() throws Exception
    {
        Algorithm();
    }
    public static void Reader() throws Exception
    {
        if (file_obj.exists())
        {
            if (file_obj.canRead())
            {
                file_contents = Files.readString(Path.of(file_path));
            }
            else
            {
                throw new Exception("[FUN-ERROR 105] : Program stopped after meeting an error trying to read from an unreadable file!");
            }
        }
        else
        {
            throw new Exception("[FUN-ERROR 104] : Program stopped after meeting an error trying to read from a file that doesn't exist!");
        }
    }
    public static void Writer(String text) throws Exception
    {
        Writer(text, true);
    }
    public static void Writer(String text, boolean new_line) throws Exception
    {
        try
        {
            writer_obj.write(text);
            if (debug_value){Printer(text,false);}
            if (new_line)
            {
                writer_obj.write("\n");
                if(debug_value){Printer();}
            }

        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 102] : Program stopped after meeting an error while writing to the file!");
        }
    }
    public static void Algorithm() throws Exception
    {
        String base_string = "";
        int max_length = 5;
        for (int available_length = 1; available_length <= max_length; available_length++)
        {
            WLC(base_string, available_length);
        }
        try
        {
            MainCreator();

            writer_obj.close();
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 100] : Program stopped after meeting an error while closing the file!");
        }
    }
    //wordlist list creator
    public static void WLC(String text, int length)
    {
        boolean index_flag = false;
        String new_text;
        for (String word : word_list)
        {
            new_text = text + word;
            if (!index_flag)
            {
                length = length - 1;
                index_flag = true;
            }
            if (length > 0)
            {
                WLC(new_text, length);
            }
            else
            {
                wordlist_list.add(new_text);
            }
        }
    }
    public static void MainCreator() throws Exception
    {
        for (String item : wordlist_list)
        {
            Writer(item);
            Printer(item);
        }
    }


    public static void main(String [] args) throws Exception
    {
        Starter();
    }
}
