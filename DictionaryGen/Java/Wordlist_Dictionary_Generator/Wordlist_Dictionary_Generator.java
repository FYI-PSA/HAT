// https://GitHub.com/Funtime-UwU/Hackers-Toolbox

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Objects;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Wordlist_Dictionary_Generator
{
    public static int minimum_chain_value;
    public static int maximum_chain_value;
    public static int default_chain_value;

    public static double delta_time;
    public static double ending_time;
    public static double starting_time;

    public static boolean pre_items;
    public static boolean file_exists;
    public static boolean debug_value;

    public static Scanner scan_obj;

    public static File file_obj;
    public static FileWriter writer_obj;

    public static String file_path;
    public static String file_name;
    public static String backup_path;
    public static String backup_name;
    public static String parent_path;
    public static String backup_parent;
    public static String file_contents;

    public static ArrayList<String> word_list;

    static
    {
        scan_obj = new Scanner(System.in);
        word_list = new ArrayList<>();
        debug_value = false;
        pre_items = false;
    }

    public static String Ask()
    {
        return Ask("");
    }

    public static String Ask(String prefix)
    {
        Printer(prefix, false);
        return scan_obj.nextLine();
    }

    public static void Printer()
    {
        Printer("");
    }

    public static void Printer(String text)
    {
        Printer(text, true);
    }

    public static void Printer(String text, boolean new_line)
    {
        if (new_line)
        {
            System.out.println(text);
        }
        else
        {
            System.out.print(text);
        }
    }

    public static void Starter() throws Exception
    {
        if (pre_items)
        {
            String[] items = {"-","_",".","@","!","?","(",")","[","]","{","}","$","%","&","*","\"","'"," ","/","\\"};
            word_list.addAll(Arrays.asList(items));
        }
        System.out.println("[!] Welcome to the wordlist generator!\n[!] This is the faster version of the program, written in Java!");
        InputCollector();
    }

    public static void InputCollector() throws Exception
    {
        word_list.add(Ask("[?] First input : "));
        boolean Asking = true;
        while (Asking)
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
            List<String> void_list = Arrays.asList(void_input);
            if (void_list.contains(temp_ask))
            {
                Printer("[#] Stopped asking.\n[$] Thank you for your input.");
                Asking = false;
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
        String mode = Ask
            (
        "[!] Which method do prefer for saving the dictionary file?"
                + "\n[@] Enter 'M' → Manual naming"
                + "\n[@] Enter 'A' → Automatic naming"
                + "\n[%] Leave empty or enter 'D' to default to " + default_name
                + "\n[!] (The file with always be saved with a .txt extension, no matter the mode)"
                + "\n\n[?] Enter :  "
            );
        String[] manual_mode =
            {
                "'m'", "\"m\"", "m",
                "'M'", "\"M\"", "M",
                "MANUAL", "Manual", "manual"
            };
        String[] automatic_mode =
            {
                "'a'", "\"a\"", "a",
                "'A'", "\"A\"", "A",
                "AUTO", "Auto", "auto",
                "AUTOMATIC", "Automatic", "automatic"
            };
        List<String> automatic_list = Arrays.asList(automatic_mode);
        List<String> manual_list = Arrays.asList(manual_mode);
        if (manual_list.contains(mode))
        {
            Printer("[?] Name your file : ",false);
            file_name = Ask()+".txt";
        }
        else if (automatic_list.contains(mode))
        {
            if (word_list.size() < 1)
            {
                file_name = "_ERROR_EMPTY_"+default_name;
                Printer("\n\n[ERROR 101] : Program stopped after meeting an error trying to process inputs while there were none!\n");
                System.exit(1);
                //throw new Exception("[FUN-ERROR 101]");
            }
            else
            {
                String first_item = word_list.get(0);
                if (word_list.size() >= 2)
                {
                    file_name = first_item + "_" + word_list.get(1) + "_" + default_name;
                }
                else
                {
                    file_name = first_item + "_" + default_name;
                }
                Printer("[$] You file name was set to " + file_name + " !");
            }
        }
        else
        {
            Printer("[$] Defaulting to " + default_name + " !");
            file_name = default_name;
        }
        String usr_home_path = System.getProperty("user.home");
        parent_path = usr_home_path.replaceAll("\\\\","/") + "/Documents/Generated-Dictionaries/";
        file_path = parent_path + file_name;
        file_exists = FileCreator();
        if (file_exists)
        {
            FileExists(true);
        }
        else
        {
            NewFileWriter();
        }
    }

    public static boolean FileCreator()
    {
        boolean return_value = false;
        try
        {
            Files.createDirectories(Paths.get(parent_path));
            file_obj = new File(file_path);
            if (file_obj.createNewFile())
            {
                Printer("[$] File created successfully at '"+parent_path+"' !");
            }
            else
            {
                Printer("[#] Looks like file with the name of "+file_name+" already exists at "+parent_path);
                return_value = true;
            }
        }
        catch (IOException err_value)
        {
            Printer("[FUN-ERROR 103] : Program stopped after meeting an error with creating the file!\n" +
                "[  ERROR 103  ] The cause of this issue may be a wrong file path, or incorrect permissions.");
            System.exit(1);
            //throw new Exception("[ERROR 103]");
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
                + "\n\n[?] Enter :  "
            ;
        }
        else
        {
            ask_entry =
                "[!] What would you like to do now?"
                +"\n[@] Enter 'A' → Append to end of file"
                +"\n[@] Enter 'O' → Override contents of file"
                +"\n[@] Enter 'C' → Cancel this operation and choose to exit or save to another file"
                +"\n[%] Leave empty or enter 'D' to default to " + default_mode + " mode"
                +"\n\n[?] Enter :  "
            ;
        }
        backup_parent = parent_path + "\\backups\\";
        backup_name = file_name + ".backup";
        backup_path = backup_parent+backup_name;
        String temp_mode = Ask (ask_entry);
        String[] view_entries =
            {
                "V", "\"V\"", "'V'",
                "v", "\"v\"", "'v'",
                "view", "VIEW", "View"
            };
        String[] overwrite_entries =
            {
                "O", "\"O\"", "'O'",
                "o", "\"o\"", "'o'",
                "overwrite", "Overwrite", "OverWrite", "overWrite", "OVERWRITE"
            };
        String[] cancel_entries =
            {
                "C", "\"C\"", "'C'",
                "c", "\"c\"", "'c'",
                "cancel", "CANCEL", "Cancel"
            };
        List<String> viewList = Arrays.asList(view_entries);
        List<String> overwriteList = Arrays.asList(overwrite_entries);
        List<String> cancelList = Arrays.asList(cancel_entries);
        if (view_mode && viewList.contains(temp_mode))
        {
            View();
        }
        else if (overwriteList.contains(temp_mode))
        {
            Overwrite();
        }
        else if (cancelList.contains(temp_mode))
        {
            Cancel();
        }
        else
        {
            Append();
        }
    }

    public static void NewFileWriter() throws Exception
    {
        WriterInit();
        Algorithm();
    }

    public static void View() throws Exception
    {
        Saviour();
        Printer("[@] Here's all of the data that was in the file:\n\n"+file_contents+"\n\n");
        FileExists(false);
    }

    public static void Append() throws Exception
    {
        BackerUp(false);
        Writer(file_contents);
        Algorithm();
    }

    public static void Cancel() throws Exception
    {
        Printer("[!] Cancelling...\n[?] Would you like to fully exit the program, or to just change the file name and location?");
        Printer("[?] Enter 'EXIT' or 'CHANGE' (will default to CHANGE in case of undefined input)");
        String[] change_list =
        {
            "EXIT", "\"EXIT\"", "'EXIT'",
            "Exit", "\"Exit\"", "'Exit'",
            "exit", "\"exit\"", "'exit'",
            "E", "\"E\"", "'E'",
            "e", "\"e\"", "'e'",
            "quit", "cancel"
        };
        List<String> changeList = Arrays.asList(change_list);
        String exit_cond = Ask();
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
        boolean append_instead = BackerUp(true);
        if (append_instead)
        {
            Append();
        }
        else
        {
            WriterInit();
            Algorithm();
        }
    }

    public static boolean BackerUp(boolean not_append_mode) throws Exception
    {
        String question =
            "[?] Do you want to save a backup of current existing file?\n" +
            "[@] (Will default to 'YES' in case of undefined input)\n" +
            "[!] (WARNING: THIS MAY DELETE ANY PRE-EXISTING BACKUPS IN FOLDER "+backup_parent+"   !)\n" +
            "\n[?] >  ";
        if (not_append_mode)
        {
            question =
                "[?] Do you want to save a backup of current existing file?\n" +
                "[@] (Will default to 'YES' in case of undefined input)\n" +
                "[@] (You can enter 'CANCEL' to cancel this operation and append instead.)\n" +
                "[!] (THIS MAY DELETE ANY PRE-EXISTING BACKUPS IN FOLDER "+backup_parent+"   !)\n" +
                "\n[?] >  ";
        }
        String backup_cond = Ask(question);
        String[] no_list =
            {
                "NO", "\"NO\"", "'NO'",
                "No", "\"No\"", "'No'",
                "no", "\"no\"", "'no'",
                "N", "\"N\"", "'N'",
                "n", "\"n\"", "'n'"
            };
        String[] cancel_list =
            {
                "CANCEL", "\"CANCEL\"", "'CANCEL'",
                "Cancel", "\"Cancel\"", "'Cancel'",
                "cancel", "\"cancel\"", "'cancel'",
                "C", "\"C\"", "'C'",
                "c", "\"c\"", "'c'"
            };
        List<String> noList = Arrays.asList(no_list);
        List<String> cancelList = Arrays.asList(cancel_list);
        if (!noList.contains(backup_cond))
        {
            Printer("[$] Saving backup to "+backup_path);
            Saviour();
            Backup();
            WriterInit();
        }
        else if (cancelList.contains(backup_cond) && not_append_mode)
        {
            Printer("[!] Appending instead...");
            return true;
        }
        else
        {
            Printer("[@] Avoided creating backup.");
        }
        return false;
    }

    public static void Saviour() throws Exception
    {
        if (!file_obj.exists())
        {
            Printer("\n\n[ERROR 104] : Program stopped after meeting an error trying to read from a file that doesn't exist!\n");
            System.exit(1);
            //throw new Exception("[ERROR 104]");
        }
        if (!file_obj.canRead())
        {
            Printer("\n\n[ERROR 102] : Program stopped after meeting an error trying to read from an unreadable file!\n");
            System.exit(1);
            //throw new Exception("[ERROR 102]");
        }
        file_contents = Files.readString(Path.of(file_path));
    }

    public static void Backup()
    {
        try
        {
            Files.createDirectories(Paths.get(backup_parent));
            File backup_file_obj = new File(backup_path);
            if (backup_file_obj.createNewFile())
            {
                Printer("[$] Successfully created new backup!");
            }
            else
            {
                Printer("[!] Overwriting previous backup...");
            }
            FileWriter backup_writer_obj = new FileWriter(backup_path);
            backup_writer_obj.write(file_contents);
            backup_writer_obj.close();
        }
        catch (IOException error_value)
        {
            Printer("[FUN-ERROR 103] : Program stopped after meeting an error with creating the backup file!\n" +
                    "[  ERROR 103  ] The cause of this issue may be a wrong file path, or incorrect permissions.");
            System.exit(1);
            //throw new Exception("[ERROR 103]");
        }
    }

    public static void WriterInit() throws Exception
    {
        writer_obj = new FileWriter(file_path);
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
            Printer("[FUN-ERROR 105] : Program stopped after meeting an error trying to write to the file!\n" +
                "[  ERROR 105  ] The cause of this issue may be incorrect permissions or the file might be corrupted.");
            System.exit(1);
            //throw new Exception("[ERROR 105]");
        }
    }

    public static void DjangoChained()
    {
        minimum_chain_value = Unchained("minimum");
        maximum_chain_value = Unchained("maximum") + 1;
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
        String temp_chain_value = Ask("[?] What is the "+chain_type+" chain length?  ");
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

    public static void MemoryMan()
    {
        long free_memory = Runtime.getRuntime().freeMemory();
        //will make this a slider in a UI version, not going to deal with percentages on terminal app at the moment
        //if you want me to add percentages, remind me by opening an issue on the GitHub page
        if (free_memory < (1024*1024 / 250 ))
        {
            System.gc();
        }
    }

    public static void Algorithm() throws Exception
    {
        DjangoChained();
        String base_string = "";
        Printer("[$] Creating! \n[!] This process may take a lot of time, please be patient...");
        TimeUnit.MILLISECONDS.sleep(750);
        Printer("[$] GENERATING",false);
        for (int i = 0; i < 3; i ++)
        {
            TimeUnit.MILLISECONDS.sleep(500);
            Printer(".",false);
        }
        Printer();
        starting_time = System.nanoTime();
        int gap = maximum_chain_value - minimum_chain_value;
        int percentage_gap = 100/gap;
        int general_percent = 0;
        for (int available_length = minimum_chain_value; available_length < maximum_chain_value; available_length++)
        {
            if (general_percent != 100) {Printer("Progress : ["+general_percent+"%]");}
            WLC(base_string, available_length);
            general_percent = general_percent + percentage_gap;
        }
        MemoryMan();
        Printer("Progress : [100%] !");
        ending_time = System.nanoTime();
        delta_time = ending_time - starting_time;
        float minutes_took = (float)(delta_time / Math.pow(10.0, 9.0) / 60.0);
        minutes_took =  ((float)((int)(minutes_took * 100))) / 100 ;
        Printer("[$] Finished! It took exactly "+minutes_took+" minutes to finish creating your dictionary!\n[@] Enjoy!");
        Printer("[@] The file is saved at "+file_path);
    }

    public static void WLC(String text, int length)
    {
        boolean index_flag = false;
        String new_text;
        for (String word : word_list)
        {
            MemoryMan();
            new_text = text + word;
            if (!index_flag)
            {
                length --;
                index_flag = true;
            }
            if (length > 0)
            {
                WLC(new_text, length);
            }
            else
            {
                Writer(new_text);
            }
        }
    }

    public static void main(String [] args) throws Exception
    {
        Starter();
        writer_obj.close();
    }
}
