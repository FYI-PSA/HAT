import java.util.concurrent.TimeUnit;
import java.nio.file.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class AlgorithmConcept
{
    public static File file_obj;
    public static FileWriter writer_obj;
    public static Scanner scan_obj = new Scanner(System.in);
    public static double starting_time, ending_time, delta_time;
    public static boolean debug_value = false, pre_items = false, file_exists;
    public static int maximum_chain_value, minimum_chain_value, default_chain_value, LLC_index;
    public static String error_message = "\n[!] Oops! Encountered an error! Here are the details:";
    public static ArrayList<String> word_list = new ArrayList<>(), wordlist_list = new ArrayList<>();
    public static String file_name, file_path, parent_path, file_contents, backup_parent, backup_name, backup_path;



    public static String Ask() {return Ask("");}
    public static String Ask(String prefix)
    {
        Printer(prefix, false);
        return scan_obj.nextLine();
    }
    public static void Printer() { Printer(""); }
    public static void Printer(String text)
    {
        Printer(text, true);
    }
    public static void Printer(String text, boolean new_line)
    {
        if (new_line) {System.out.println(text);}
        else          {System.out.print(text);  }
    }
    public static void Debugger(String debug_text, boolean new_line)
    {if (debug_value) {Printer(debug_text, new_line);}}
    public static void Debugger(String debug_text)
    {if (debug_value) {Printer(debug_text, true);}}



    public static void Starter() throws Exception
    {
        //You can use this for testing, so you don't have to enter some items at the start of running the file every time.
        if (pre_items)
        {
            String[] items = {"-","_",".","@","!","?","(",")","[","]","{","}","$","%","&","*","\"","'"," "};
            for (String item : (Arrays.stream(items).toList()))
            {
                word_list.add(item);
                Debugger(item);
            }
        }
        System.out.println("[!] Welcome\n[@] This is a prototype to test the wordlist generation algorithm in java");
        InputCollector();
    }

    public static void InputCollector() throws Exception
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
        NameCreator();
    }

    public static void NameCreator() throws Exception
    {
        String default_name = "dictionary.txt";
        String mode = Ask("Mode: D/A/M");
        if (Objects.equals(mode,"M"))
        {
            file_name = Ask()+".txt";
        }
        else if (Objects.equals(mode, "A"))
        {
            if (word_list.size() >= 1)
            {
                if (word_list.size() >= 2)
                {
                    file_name = word_list.get(0)+"_"+word_list.get(1)+"_"+default_name;
                }
                else
                {
                    file_name = word_list.get(0)+"_"+default_name;
                }
            }
            else
            {
                file_name = "_ERROR_EMPTY_"+default_name;
                throw new Exception("[FUN-ERROR 107] : Program stopped after finding no input to create the automatic name with!");
            }
        }
        else
        {
            Printer("[$] Defaulting to "+default_name+" !");
            file_name = default_name;
        }

        parent_path = System.getProperty("user.home") + "\\Documents\\Generated-Dictionaries\\Java-Version\\";
        file_path = parent_path + file_name;
        file_exists = FileCreator();
        if (file_exists)
        {
            Printer("[#] File with matching name found! What would you like to do?");
            FileExists(true);
        }
        else
        {
            Printer("[$] Created new file named "+file_name+" successfully!");
            NewFileWriter();
        }
    }

    public static void WriterInit() throws Exception
    {
        writer_obj = new FileWriter(file_path);
    }

    public static void NewFileWriter() throws Exception
    {
        WriterInit();
        Algorithm();
    }

    public static boolean FileCreator() throws Exception {
        boolean return_value = false;
        try
        {
            Files.createDirectories(Paths.get(parent_path));
            file_obj = new File(file_path);
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

    public static void FileExists(boolean view_mode) throws Exception
    {
        String ask_entry, default_mode = "Append";
        if (view_mode)
        {
            ask_entry =
                "[!] What would you like to do now?"
                + "\n[@] Enter 'V' → View file contents"
                + "\n[@] Enter 'A' → Append to end of file"
                + "\n[@] Enter 'O' → Override contents of file"
                + "\n[@] Enter 'C' → Cancel this operation and choose to exit or save to another file"
                + "\n[%] Leave empty or enter 'D' to default to " + default_mode + " mode"
                + "\n\n[?] Enter :  ";
        }
        else
        {
            ask_entry =
                "[!] What would you like to do now?"
                +"\n[@] Enter 'A' → Append to end of file"
                +"\n[@] Enter 'O' → Override contents of file"
                +"\n[@] Enter 'C' → Cancel this operation and choose to exit or save to another file"
                +"\n[%] Leave empty or enter 'D' to default to " + default_mode + " mode"
                +"\n\n[?] Enter :  ";
        }
        backup_parent = parent_path + "\\backups\\";
        backup_name = file_name + ".backup";
        backup_path = backup_parent+backup_name;
        String temp_mode = Ask (ask_entry);
        String[] view_entries     = {"V","\"V\"","'V'","v","\"v\"","'v'"};
        String[] overwrite_entries = {"O","\"O\"","'O'","o","\"o\"","'v'"};
        String[] cancel_entries   = {"C","\"C\"","'C'","c","\"c\"","'c'"};
        List<String> viewList = Arrays.asList(view_entries);
        List<String> overwriteList = Arrays.asList(overwrite_entries);
        List<String> cancelList = Arrays.asList(cancel_entries);
        if (view_mode && viewList.contains(temp_mode))
        {//view mode
            Debugger("View");
            View();
        }
        else if (overwriteList.contains(temp_mode))
        {//overwrite mode
            Debugger("Overwrite");
            Overwrite();
        }
        else if (cancelList.contains(temp_mode))
        {//cancel
            Debugger("Cancel");
            Cancel();
        }
        else
        {//append mode
            Debugger("Append");
            Append();
        }
    }

    public static void View() throws Exception
    {
        Saviour();
        Printer("[@] Here's all of the data that was in the file:\n\n"+file_contents+"\n\n");
        FileExists(false);
    }

    public static void Append() throws Exception
    {
        BackerUp();
        Writer(file_contents);
        Algorithm();
    }

    public static void Cancel() throws Exception
    {
        Printer("[!] Cancelling...\n[?] Would you like to fully exit the program, or to just change the file name and location?");
        Printer("[?] Enter 'EXIT' or 'CHANGE' (will default to CHANGE in case of undefined input)");
        String exit_cond = Ask();
        String[] change_list = {"EXIT","\"EXIT\"","'EXIT'","exit","\"exit\"","'exit'","e","quit","cancel"};
        List<String> changeList = Arrays.asList(change_list);
        if (changeList.contains(exit_cond))
        {
            Printer("[!] Thank you for your time! Hope to see you later!");
            System.exit(0);
        }
        else
        {
            Printer("[@] Change your file name and path:\n");
            NameCreator();
        }
    }

    public static void Overwrite() throws Exception
    {
        BackerUp();
        Algorithm();
    }

    public static void BackerUp() throws Exception
    {
        String backup_cond = Ask("[?] Do you want to save a backup of current existing file? Enter 'YES' or 'NO'\n" +
                "[@] (Will default to 'YES' in case of undefined input)\n" +
                "[@] (You can enter 'CANCEL' to cancel this operation and append instead.)\n" +
                "[!] WARNING: THIS MAY DELETE ANY PRE-EXISTING BACKUPS IN FOLDER "+backup_parent+"   !\n" +
                "[!] PLEASE USE WITH CAUTION!\n" +
                "[?] >  ");
        String[] no_list = {"NO", "\"NO\"", "'NO'", "No", "\"No\"", "'No'", "no", "\"no\"", "'no'", "N", "n"};
        List<String> noList = Arrays.asList(no_list);
        if (noList.contains(backup_cond))
        {
            Debugger("No to backup");
        }
        else
        {
            Printer("[$] Saving backup to "+backup_path);
            Saviour();
            Backup();
            WriterInit();
        }
    }

    public static void Saviour() throws Exception
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

    public static void Backup() throws Exception
    {
        //File backup_exists_obj = new File(parent_path+"\\backups\\"+file_name+".backup");
        try
        {
            Files.createDirectories(Paths.get(backup_parent));
            File backup_file_obj = new File(backup_path);
            if (backup_file_obj.createNewFile())
            {Printer("[$] Successfully created new backup!");}
            else
            {Printer("[!] Overwriting previous backup...");}
            FileWriter backup_writer_obj = new FileWriter(backup_path);
            backup_writer_obj.write(file_contents);
            backup_writer_obj.close();
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 106] : Program stopped after meeting an issue trying to create a backup!");
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
            Debugger(text,false);
            if (new_line)
            {
                writer_obj.write("\n");
                Debugger("", false);
            }
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 102] : Program stopped after meeting an error while writing to the file!");
        }
    }

    public static void DjangoChained()
    {//instead of working with pairs and hashmaps, just make the minimum_chain_value and maximum_chain_value public statics.
        minimum_chain_value = Unchained("minimum");
        maximum_chain_value = Unchained("maximum");
    }

    public static int Unchained(String chain_type)
    {
        int chain_value;
        if (Objects.equals(chain_type, "minimum"))
        {
            default_chain_value = 1;
        }
        else
        {
            default_chain_value = 6;
        }
        String temp_chain_value = Ask("[?] What is the "+chain_type+" chain length?");
        try
        {
            chain_value = Integer.parseInt(temp_chain_value);
        }
        catch (NumberFormatException error_value)
        {
            Printer("[#] Sorry, that input couldn't be understood as a number. defaulting to +"+default_chain_value);
            chain_value = default_chain_value;
        }
        return chain_value;
    }

    public static void Algorithm() throws Exception
    {
        String base_string = "";
        DjangoChained();
        Printer("[$] Creating! \n[!] This process may take a lot of time, please be patient...");
        TimeUnit.MILLISECONDS.sleep(200);
        Printer("[$] GENERATING",false);
        for (int i = 0; i < 3; i ++)
        {
            TimeUnit.MILLISECONDS.sleep(100);
            Printer(".",false);
        }
        Printer();
        starting_time = System.nanoTime();
        int gap = maximum_chain_value - minimum_chain_value;
        int percentage_gap = 100/gap;
        int general_percent = 0;
        for (int available_length = minimum_chain_value; available_length <= maximum_chain_value; available_length++)
        {
            Printer("Progress : ["+general_percent+"%]");
            WLC(base_string, available_length);
            general_percent = general_percent + percentage_gap;
        }
        if (general_percent != 100 || (general_percent - percentage_gap) == 100)
        {
            Printer("Progress : [100%] !");
        }
        /*
        try
        {
            LLC_index = 0;
            while (LLC_index < wordlist_list.size())
            {
                try
                {
                    MainCreator(LLC_index);
                }
                catch (OutOfMemoryError memory_issue)
                {
                    Printer("[#!] Oops, there seems to be a memory issue, attempting to fix it and continuing...\nSee logs: ");
                    memory_issue.printStackTrace();
                    writer_obj.close();
                    Saviour();
                    Writer(file_contents);
                    WriterInit();
                }
            }
        }
        catch (IOException error_value)
        {
            Printer(error_message);
            error_value.printStackTrace();
            throw new Exception("[FUN-ERROR 100] : Program stopped after meeting an error while closing the file!");
        }
        */
        ending_time = System.nanoTime();
        delta_time = ending_time - starting_time;
        // 1 nano second is 10^(-9) seconds, 1 minute is 60 seconds --> M = ((NS * (10^9)) / 60)
        float minutes_took = (float) ( ( delta_time * ( Math.pow(10,9) ) ) / 60 );
        Printer("[$] Finished! It took exactly "+minutes_took+" minutes to finish creating your dictionary!\n[@] Enjoy!");
        Printer("[@] The file is saved at "+file_path);
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
                Writer(new_text);
                Debugger(new_text);
            }
        }
    }

    public static void MainCreator(int starting_index) throws Exception
    {
        String item;
        for (LLC_index = starting_index; LLC_index < wordlist_list.size(); LLC_index++)
        {
            item = wordlist_list.get(LLC_index);
            Writer(item);
            Debugger("Number: "+LLC_index+" | Item:  "+item);
        }
    }


    public static void main(String [] args) throws Exception
    {
        Starter();
        writer_obj.close();
    }
}
