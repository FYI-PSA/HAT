#include <Windows.h>
#include <filesystem>
#include <iostream>
#include <stdio.h>
#include <io.h>
#include <errno.h>
#include <string>
#include <vector>
#include <string.h>
#include <fstream>
#include <filesystem>

#ifndef DIRMAN_H
#include "DirectoryManager.h"
using funtime::CreateDirectoryMan;
#endif

using std::string;
using std::vector;
using std::filesystem::path;
using std::filesystem::directory_iterator;
using std::cout;
using std::cin;
using std::endl;
using std::pair;

namespace funtime
{
    vector<pair<int, vector<string>>> FConfigReader(string HomePath, bool shouldSearch, string customPath)
    {
        vector<string> L_wordList;
        string D_Name;
        string D_Extension;
        int D_MaximumChain;
        int D_MinimumChain;
        bool A_Name;
        bool A_Entry;
        bool A_MinimumChain;
        bool A_MaximumChain;   
        
        string dummy = "-1";
        vector<pair<int, vector<string>>> baseValue =
        {
                {0,{}} //wordlist
                ,
                {0,{"dictionary"}} // name
                ,
                {0,{".txt"}} // extension
                ,
                {0,{"1"}} // min
                ,
                {0,{"6"}} // max
                ,
                {0,{dummy}} // ask_name
                ,
                {0,{dummy}} // ask_entries
                ,
                {0,{dummy}} // ask_min
                ,
                {0,{dummy}} // ask_max
                ,
                {0,{dummy}} // success check
        };

        vector<pair<int, vector<string>>> returnValue = baseValue;

        // first one : int=words , vector<string>=list
        
        // second one : int=0/1 not/set , vector<string>[0] = d_name
        // third one : int=0/1 not/set , vector<string>[0] = d_ext
        // fourth one : int=0/1 not/set , vector<string>[0] = string(d_min)
        // fifth one : int=0/1 not/set , vector<string>[0] = string(d_max)
        
        // sixth one : int=0/1 false/true = ask_name , NULL
        // seventh one : int=0/1 false/true = ask_entries , NULL
        // eight one : int=0/1 false/true = ask_min , NULL
        // ninth one : int=0/1 false/true = ask_max , NULL


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
                cout << "[!] No configuration file with the '.fconfig' extension found at '" + fconfigPath + "'. " << endl << endl;
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

                vector<string> customEntriesVector;
                string customName;
                string customExt;
                string customMax;
                string customMin;

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
                cout << endl << "[!] Custom File Name : " << endl << customName << endl
                << endl << "[!] Custom File Extension : " << endl << customExt << endl
                << endl << "[!] Custom Minimum Chain Value : " << endl << customMin << endl
                << endl << "[!] Custom Maximum Chain Value : " << endl << customMax << endl
                << endl;
                */


                if (flag_EntryStart)
                {
                    for (int itemIndex = 0; itemIndex < customEntriesVector.size(); itemIndex++)
                    {
                        L_wordList.push_back(customEntriesVector.at(itemIndex));
                        returnValue[0].second.push_back(customEntriesVector.at(itemIndex));
                        returnValue[0].first+=1;
                    }
                    if (flag_AskEntry)
                    {
                        A_Entry = true;
                        returnValue[6].first = 1;
                    }
                    else
                    {
                        A_Entry = false;
                        returnValue[6].first = 0;
                    }
                }
                if (flag_Name && flag_Name_DONE)
                {
                    D_Name = customName;
                    returnValue[1].second[0] = customName;
                    returnValue[1].first = 1;
                    if (flag_AskName)
                    {
                        A_Name = true;
                        returnValue[5].first = 1;
                    }
                    else
                    {
                        A_Name = false;
                        returnValue[5].first = 0;
                    }
                }
                if (flag_Ext && flag_Ext_DONE)
                {
                    D_Extension = customExt;
                    returnValue[2].first = 1;
                    returnValue[2].second[0] = customExt;
                }
                if (flag_Min && flag_Min_DONE)
                {
                    try
                    {
                        D_MinimumChain = stoi(customMin);
                        returnValue[3].first = 1;
                        returnValue[3].second[0] = customMin;
                        if (flag_AskMin)
                        {
                            returnValue[7].first = 1;
                            A_MinimumChain = true;
                        }
                        else
                        {
                            returnValue[7].first = 0;
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
                        returnValue[4].first = 1;
                        returnValue[4].second[0] = customMax;
                        if (flag_AskMax)
                        {
                            returnValue[8].first = 1;
                            A_MaximumChain = true;
                        }
                        else
                        {
                            returnValue[8].first = 0;
                            A_MaximumChain = true;
                        }
                    }
                    catch (invalid_argument)
                    {
                    }
                }
            }
            returnValue[9].first = 1;
            if (!customFileFound && (customPath != ""))
            {
                returnValue[9].first = 0;
                return baseValue;
            }
            if (!validConfigFound)
            {
                returnValue[9].first = 0;
                return baseValue;
            }
            bool changedFlag = false;
            for (int row = 0 ; row < returnValue.size()-1 ; row ++)
            { // -1 because the last one needs to be ignored
                if (returnValue[row].first != 0)
                {
                    changedFlag = true;   
                }
            }
            if (!changedFlag)
            {
                returnValue[9].first = 0;
                return baseValue;
            }
            return returnValue;
        }
        returnValue[9].first = 0;
        cout << "[!] Couldn't load custom file... Skipping configs." << endl << endl;
        return baseValue;
    }

}

