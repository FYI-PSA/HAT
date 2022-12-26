#include <Windows.h>
#include <filesystem>
#include <iostream>
#include <stdio.h>
#include <io.h>
#include <cstudio>
#include <errno.h>
#include <string>
#include <string.h>
#include <fstream>

bool FConfigReader(void)
{
    string fconfigPath = U_HomePath + "PreConfigs/";

    int createdStatus = CreateDirectoryMan(fconfigPath, true);
    // -1 if undefined err, 
    // 0 if successfully made, 
    // 1 if already existed, 
    // 2 if planned errors

    if (createdStatus == -1 || createdStatus == 0 || createdStatus == 2)
    {
        cout << "[!] No folder for pre configurations found." << endl;
        return false;
    }
    else if (createdStatus == 1)
    {
        vector<path> pathVector;
        vector<path> fileVector;
        vector<path> nameVector;
        vector<path> extVector;

        vector<path> fconfigFiles;
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
            cout << "[!] No configuration file with the '.fcofnig' extension found at '" + fconfigPath + "'. " << endl << endl;
            return false;
        }

        bool validConfigFound = false;
        for (int fileIndex = 0; fileIndex < fconfigFiles.size(); fileIndex++)
        {
            if (validConfigFound)
            {
                break;
            }

            string fconfigFilePath = fconfigFiles.at(fileIndex).generic_string();
            string fconfigFileName = fconfigFiles.at(fileIndex).filename().generic_string();

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

            if (flag_EntryStart)
            {
                for (int itemIndex = 0; itemIndex < customEntriesVector.size(); itemIndex++)
                {
                    L_wordList.push_back(customEntriesVector.at(itemIndex));
                }
                if (flag_AskEntry)
                {
                    A_Entry = true;
                }
                else
                {
                    A_Entry = false;
                }
            }
            if (flag_Name && flag_Name_DONE)
            {
                D_Name = customName;
                if (flag_AskName)
                {
                    A_Name = true;
                }
                else
                {
                    A_Name = false;
                }
            }
            if (flag_Ext && flag_Ext_DONE)
            {
                D_Extension = customExt;
            }
            if (flag_Min && flag_Min_DONE)
            {
                try
                {
                    D_MinimumChain = stoi(customMin);
                }
                catch (invalid_argument)
                {
                    D_MinimumChain = D_MinimumChain;
                }
                if (flag_AskMin)
                {
                    A_MinimumChain = true;
                }
                else
                {
                    A_MinimumChain = false;
                }
            }
            if (flag_Max && flag_Max_DONE)
            {
                try
                {
                    D_MaximumChain = stoi(customMax);
                }
                catch (invalid_argument)
                {
                    D_MaximumChain = D_MaximumChain;
                }
                if (flag_AskMax)
                {
                    A_MaximumChain = true;
                }
                else
                {
                    A_MaximumChain = true;
                }
            }
        }
        return true;
    }
    cout << "[!] Something went wrong doing that." << endl << endl;
    return false;
}

