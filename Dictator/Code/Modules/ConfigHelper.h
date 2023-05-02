#include <iostream>
#include <fstream>
#include <string.h>
#include <string>

namespace hat
{
    void updateText(void)
    {
        std::string config_help_file_path = "config-help.txt";
        std::ifstream config_help_file(config_help_file_path);
        std::ofstream text_module("copyme.txt");
        std::string config_help = "return \"";
        std::string line_data = "";
        while (getline(config_help_file, line_data))
        {
            config_help += line_data;
            config_help += "\\n";
        }
        config_help += "\";";
        text_module << config_help;
    } // copy it in place of the configHelpData return line, it does include the semicolon too. just be careful of double quotes
    std::string configHelpData(void)
    {
        return "\nFile must be named '<NAME>.fConfig' unless providing the\n'--conf-file <path including name and ext>'\nargument during execution from the console\n\n* The first file with the .fConf extension that gets automatically detected in '{Hat Home Path}/PreConfigs/'\non run, will be executed. \nif you want to make sure the correct config gets selected, make the first line something other than the starting syntax.\n\nThe program will also ignore these .fConfig files if you use the\n'--ignore-conf'\nflag during execution from the console\n(will also ignore all configs even if you use '--conf-file <file>')\n\n_________________________________________________________________________________\n\nStart:\n--Dictator-Config--\n[required, first line of file]\n\n\n[!! From this point, all settings are optional (unless specified otherwise). !!]\n\n\n--Entries--\n<entry1>\n<entry2>\n<entry...>\n<...>\n--End-Entries--\n[--End-Entries-- is required to stop adding entries, however if the file ends, it will automatically stop]\n[Be careful to include the end flag, otherwise the program will count your other flags as inputs, unless they're blank*]\n[*A blank entry will not be counted.]\n\n--Still-Get-Entries--\n[will ask you for more entries during runtime, even if you provided some entries previously]\n[Ignored if no --Entries-- flag]\n\n--Prefix--\n<string>\n[Will set a prefix at the start of each output in the dictionary]\n[*If you set the prefix using launch flags, then that will override this entry]\n\n--Suffix--\n<string>\n[Will set a prefix at the start of each output in the dictionary]\n[*If you set the suffix using launch flags, then that will override this entry]\n\n--File-Name--\n<name without extension>\n[Will set the default name for the file. Won't ask unless provided]\n[If you provided an extension it will count as part of the name*]\n[*Unless you set the custom extension blank]\n\n--Still-Ask-File-Name--\n[Will still ask you for a file name during runtime]\n[--File-Name-- will be the default if you leave entry blank during runtime]\n[Ignored if no --File-Name-- Flag]\n\n--Custom-Extension--\n<extension, including the dot>\n[Will set the extension of the file. Can be without a dot, but it will result in a normal file*]\n[*Unless provided with an extension in the file name]\n\n--Max-Len--\n<maximum chain length>\n[Gets a maximum chain length. Won't ask unless provided]\n\n--Still-Ask-Max-Len--\n[Will still ask you for a maximum chain length during runtime]\n[--Max-Len-- will be the default if you enter nothing for maximum chain value during runtime]\n[Ignored if no --Max-Len-- flag]\n\n--Min-Len--\n<minimum chain length>\n[Gets a minimum chain length. Won't ask unless provided]\n\n--Still-Ask-Min-Len--\n[Will still ask you for a minimum chain length during runtime]\n[--MinLen-- will be the default if you enter nothing for minimum chain value during runtime]\n[Ignored if no --Min-Len-- flag]\n";
    }
}
