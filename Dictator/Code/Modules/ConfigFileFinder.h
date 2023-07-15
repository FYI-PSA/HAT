#ifndef DICTATOR_MODULES_CONFIGFILEFINDER_H
#define DICTATOR_MODULES_CONFIGFILEFINDER_H

#include <filesystem>
#include <fstream>
#include <iostream>
#include <string>
#include <utility>
#include <vector>

#include "DirectoryManager.h"
using hat::CreateDirectoryMan;

using std::cin;
using std::cout;
using std::endl;
using std::fstream;
using std::ifstream;
using std::invalid_argument;
using std::ofstream;
using std::pair;
using std::string;
using std::vector;
using std::filesystem::directory_iterator;
using std::filesystem::path;

namespace hat
{
    typedef struct configReturnType
    {
        bool askEntries = true;
        int wordcount = 0;
        vector<string> wordlist = {};

        bool useCustomName = false;
        bool askName = true;
        string name = "dictionary";

        bool useCustomExtension = false;
        string extension = ".txt";

        bool useMinValue = false;
        bool askMin = true;
        string minString = "1";
        int minValue = 1;

        bool useMaxValue = false;
        bool askMax = true;
        string maxString = "6";
        int maxValue = 6;

        bool usePrefix = false;
        string prefix = "";

        bool useSuffix = false;
        string suffix = "";

        bool useConfigs = false;
    } configReturnType;

    configReturnType FConfigReader(string HomePath, bool shouldSearch, string customPath)
    {
        vector<string> L_wordList;
        string D_Prefix;
        string D_Suffix;
        string D_Name;
        string D_Extension;
        int D_MaximumChain;
        int D_MinimumChain;
        bool A_Name;
        bool A_Entry;
        bool A_MinimumChain;
        bool A_MaximumChain;

        configReturnType baseValue = configReturnType();

        configReturnType returnValue = baseValue;

        if (!shouldSearch)
        {
            cout << "[!] Configurations are to be ignored (launch paramater)" << endl;
            return baseValue;
        }

        string fconfigPath = HomePath + "PreConfigs/";
        string customFileName = "";
        string customFilePath = "";
        if (customPath != "")
        {
            path customPath_path = customPath;

            path customPathParent_path = customPath_path;
            customPathParent_path = customPathParent_path.remove_filename();
            customFilePath = customPathParent_path.generic_string();

            path customFileName_path = customPath_path;
            customFileName_path = customFileName_path.filename();
            customFileName = customFileName_path.generic_string();

            fconfigPath = customFilePath;
            cout << "[$] Custom Path loaded." << endl;
        }
        int createdStatus = CreateDirectoryMan(fconfigPath, false);
        // -1 if undefined err,
        // 0 if successfully made,
        // 1 if already existed,
        // 2 if planned errors

        if (createdStatus == -1 || createdStatus == 0 || createdStatus == 2)
        {
            cout << "[!] No folder for the configurations was found." << endl;
            return baseValue;
        }
        else if (createdStatus == 1)
        {
            vector<path> pathVector = {};
            vector<path> fileVector = {};
            vector<path> nameVector = {};
            vector<path> extVector = {};

            vector<path> fconfigFiles = {};
            bool fconfigExists = false;

            for (auto &fileExistence : directory_iterator(fconfigPath))
            {
                pathVector.push_back(fileExistence.path());
            }
            for (int fileIndex = 0; fileIndex < pathVector.size(); fileIndex++)
            {
                path fullFile = pathVector.at(fileIndex);
                fileVector.push_back(fullFile.filename());
                nameVector.push_back(fullFile.stem());
                extVector.push_back(fullFile.extension());

                if (extVector.at(fileIndex).generic_string() == ".fconfig")
                {
                    cout << "[@] Found configuration file '" + nameVector.at(fileIndex).generic_string() + "'! " << endl;
                    fconfigFiles.push_back(fullFile);
                    fconfigExists = true;
                }
                cout << endl;
            }

            if (!fconfigExists)
            {
                cout << "[!] No configuration file with the '.fconfig' extension found at '" + fconfigPath + "'. " << endl
                     << endl;
                return baseValue;
            }

            bool validConfigFound = false;
            bool customFileFound = false;
            for (int fileIndex = 0; fileIndex < fconfigFiles.size(); fileIndex++)
            {
                if (validConfigFound)
                {
                    break;
                }

                string fconfigFilePath = fconfigFiles.at(fileIndex).generic_string();
                string fconfigFileName = fconfigFiles.at(fileIndex).filename().generic_string();

                if (customFileName != "")
                {
                    if (fconfigFileName != customFileName)
                    {
                        // debug cout, not neccessary to list all files that don't match
                        // cout << "[#] " + fconfigFileName + " Doesn't match " + customFileName << endl;
                        continue;
                    }
                    else
                    {
                        cout << "[$] Located configuration file from launch paramaters!" << endl;
                        customFileFound = true;
                    }
                }

                ifstream fconfigFile(fconfigFilePath);
                string fileData;

                int lineNumber = 0;

                bool flag_EntryStart = false;
                bool flag_EntryEnd = false;
                bool flag_AskEntry = false;
                bool flag_OverWrite = false;

                bool flag_Prefix = false;
                bool flag_Prefix_DONE = false;
                bool flag_Suffix = false;
                bool flag_Suffix_DONE = false;

                bool flag_Name = false;
                bool flag_Name_DONE = false;
                bool flag_AskName = false;
                bool flag_Ext = false;
                bool flag_Ext_DONE = false;

                bool flag_Max = false;
                bool flag_Max_DONE = false;
                bool flag_AskMax = false;
                bool flag_Min = false;
                bool flag_Min_DONE = false;
                bool flag_AskMin = false;

                vector<string> customEntriesVector = {};
                string customPrefix = "";
                string customSuffix = "";
                string customName = "";
                string customExt = "";
                string customMax = "";
                string customMin = "";

                while (getline(fconfigFile, fileData))
                {
                    lineNumber++;
                    if (lineNumber == 1 && fileData == "--Dictator-Config--")
                    {
                        cout << "[$] Valid configuration found in '" + fconfigFileName + "'! " << endl;
                        validConfigFound = true;
                        continue;
                    }
                    else if (lineNumber == 1 && fileData != "--Dictator-Config--")
                    {
                        continue;
                    }

                    if (!validConfigFound)
                    {
                        cout << "[#] Invalid config!" << endl;
                        break;
                    }
                    //-----
                    if (fileData == "")
                    {
                        continue;
                    }
                    if (fileData == "--Entries--")
                    {
                        flag_EntryStart = true;
                        continue;
                    }
                    if (fileData == "--End-Entries--")
                    {
                        flag_EntryEnd = true;
                        continue;
                    }
                    else if (flag_EntryStart && !flag_EntryEnd)
                    {
                        if (fileData != "")
                            customEntriesVector.push_back(fileData);
                        continue;
                    }
                    if (fileData == "--Still-Get-Entries--")
                    {
                        flag_AskEntry = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Overwrite-If-Exists--")
                    {
                        flag_OverWrite = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Prefix--")
                    {
                        flag_Prefix = true;
                        continue;
                    }
                    else if (flag_Prefix && !flag_Prefix_DONE)
                    {
                        customPrefix = fileData;
                        flag_Prefix_DONE = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Suffix--")
                    {
                        flag_Suffix = true;
                        continue;
                    }
                    else if (flag_Suffix && !flag_Suffix_DONE)
                    {
                        customSuffix = fileData;
                        flag_Suffix_DONE = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--File-Name--")
                    {
                        flag_Name = true;
                        continue;
                    }
                    else if (flag_Name && !flag_Name_DONE)
                    {
                        customName = fileData;
                        flag_Name_DONE = true;
                        continue;
                    }
                    if (fileData == "--Still-Ask-File-Name--")
                    {
                        flag_AskName = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Custom-Extension--")
                    {
                        flag_Ext = true;
                        continue;
                    }
                    else if (flag_Ext && !flag_Ext_DONE)
                    {
                        customExt = fileData;
                        flag_Ext_DONE = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Max-Len--")
                    {
                        flag_Max = true;
                        continue;
                    }
                    else if (flag_Max && !flag_Max_DONE)
                    {
                        customMax = fileData;
                        flag_Max_DONE = true;
                        continue;
                    }
                    if (fileData == "--Still-Ask-Max-Len--")
                    {
                        flag_AskMax = true;
                        continue;
                    }
                    //-----
                    if (fileData == "--Min-Len--")
                    {
                        flag_Min = true;
                        continue;
                    }
                    else if (flag_Min && !flag_Min_DONE)
                    {
                        customMin = fileData;
                        flag_Min_DONE = true;
                        continue;
                    }
                    if (fileData == "--Still-Ask-Min-Len--")
                    {
                        flag_AskMin = true;
                        continue;
                    }
                }
                fconfigFile.close();
                if (lineNumber == 0)
                {
                    cout << "[#] Invalid config" << endl;
                    break;
                }

                /*
                cout << "[!] Entry start : " << flag_EntryStart
                << endl << "[!] Entry end : " << flag_EntryEnd
                << endl << "[!] Ask Entry : " << flag_AskEntry
                << endl << "[!] Overwrite : " << flag_OverWrite
                << endl << "[!] Prefix : " << flag_Prefix
                << endl << "[!] Suffix : " << flag_Suffix
                << endl << "[!] File Name : " << flag_Name
                << endl << "[!] Ask File Name : " << flag_AskName
                << endl << "[!] Extension : " << flag_Ext
                << endl << "[!] Maximum : " << flag_Max
                << endl << "[!] Ask Maximum : " << flag_AskMax
                << endl << "[!] Minimum : " << flag_Min
                << endl << "[!] Ask Minimum : " << flag_AskMin
                << endl << endl;

                cout << "[!] Custom Entries : " << endl;
                for (int itemIndex = 0; itemIndex < customEntriesVector.size(); itemIndex++)
                {
                   cout << customEntriesVector.at(itemIndex) << endl;
                }
                cout
                << endl << "[!] Custom Prefix : " << endl << customPrefix << endl
                << endl << "[!] Custom Suffix : " << endl << customSuffix << endl
                << endl << "[!] Custom File Name : " << endl << customName << endl
                << endl << "[!] Custom File Extension : " << endl << customExt << endl
                << endl << "[!] Custom Minimum Chain Value : " << endl << customMin << endl
                << endl << "[!] Custom Maximum Chain Value : " << endl << customMax << endl
                << endl;
                // */

                if (flag_EntryStart)
                {
                    for (int itemIndex = 0; itemIndex < customEntriesVector.size(); itemIndex++)
                    {
                        L_wordList.push_back(customEntriesVector.at(itemIndex));
                        returnValue.wordlist.push_back(customEntriesVector.at(itemIndex));
                    }
                    returnValue.wordcount = customEntriesVector.size();
                    if (flag_AskEntry)
                    {
                        A_Entry = true;
                        returnValue.askEntries = true;
                    }
                    else
                    {
                        A_Entry = false;
                        returnValue.askEntries = false;
                    }
                }
                if (flag_Prefix && flag_Prefix_DONE)
                {
                    D_Prefix = customPrefix;
                    returnValue.prefix = customPrefix;
                    returnValue.usePrefix = 1;
                }
                if (flag_Suffix && flag_Suffix_DONE)
                {
                    D_Suffix = customSuffix;
                    returnValue.suffix = customSuffix;
                    returnValue.useSuffix = 1;
                }
                if (flag_Name && flag_Name_DONE)
                {
                    D_Name = customName;
                    returnValue.name = customName;
                    returnValue.useCustomName = true;
                    if (flag_AskName)
                    {
                        A_Name = true;
                        returnValue.askName = true;
                    }
                    else
                    {
                        A_Name = false;
                        returnValue.askName = false;
                    }
                }
                if (flag_Ext && flag_Ext_DONE)
                {
                    D_Extension = customExt;
                    returnValue.useCustomExtension = true;
                    returnValue.extension = customExt;
                }
                if (flag_Min && flag_Min_DONE)
                {
                    try
                    {
                        D_MinimumChain = stoi(customMin);
                        returnValue.useMinValue = true;
                        returnValue.minString = customMin;
                        returnValue.minValue = D_MinimumChain;
                        if (flag_AskMin)
                        {
                            returnValue.askMin = true;
                            A_MinimumChain = true;
                        }
                        else
                        {
                            returnValue.askMin = false;
                            A_MinimumChain = false;
                        }
                    }
                    catch (invalid_argument)
                    {
                    }
                }
                if (flag_Max && flag_Max_DONE)
                {
                    try
                    {
                        D_MaximumChain = stoi(customMax);
                        returnValue.useMaxValue = 1;
                        returnValue.maxString = customMax;
                        returnValue.maxValue = D_MaximumChain;
                        if (flag_AskMax)
                        {
                            returnValue.askMax = true;
                            A_MaximumChain = true;
                        }
                        else
                        {
                            returnValue.askMax = false;
                            A_MaximumChain = true;
                        }
                    }
                    catch (invalid_argument)
                    {
                    }
                }
            }
            returnValue.useConfigs = true;
            if (!customFileFound && (customPath != ""))
            {
                returnValue.useConfigs = false;
                return baseValue;
            }
            if (!validConfigFound)
            {
                returnValue.useConfigs = false;
                return baseValue;
            }
            return returnValue;
        }
        returnValue.useConfigs = false;
        cout << "[!] Couldn't load custom file... Skipping configs." << endl
             << endl;
        return baseValue;
    }

} // namespace hat

#endif