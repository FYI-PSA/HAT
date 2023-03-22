#define _CRT_SECURE_NO_WARNINGS
#include <system_error>
#include <filesystem>
#include <Windows.h>
#include <process.h>
#include <Lmcons.h>
#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <iomanip>
#include <stdio.h>
#include <errno.h>
#include <string>
#include <vector>
#include <time.h>
#include <ctime>
#include <io.h>

#include "Modules/ConfigFileFinder.h"
using funtime::FConfigReader;

#ifndef DIRMAN_H
#include "Modules/DirectoryManager.h"
using funtime::CreateDirectoryMan;
#endif

using std::cin;
using std::cout;
using std::endl;

using std::fstream;
using std::ifstream;
using std::ofstream;

using std::getline;

using std::vector;
using std::pair;

using std::filesystem::path;
using std::filesystem::directory_iterator;

using std::stoi;
using std::string;
using std::to_string;
using std::invalid_argument;

using std::system_category;

void Unchained(void);
pair<int,int> LengthGet(bool ask_min, bool ask_max);
void PathFinder(void);
void WriteToFile(void);
void NewFileCreator(void);
void InputCollector(void);
void PathErrors(string createPath);
void WordListCreation(string baseString, int lengthVar);

bool FConfigReader(void);

fstream L_fileObj;
string L_filePath;
int L_maxChainLen;
int L_minChainLen;
int L_wordListSize;
vector<string> L_wordList;

string U_UserName;
string U_UserPath;
string U_HomePath;
string U_DocumentsPath;

string D_Name = "dictionary";
string D_Extension = ".txt";
int D_MinimumChain = 1;
int D_MaximumChain = 6;

bool A_Name = true;
bool A_Entry = true;
bool A_MinimumChain = true;
bool A_MaximumChain = true;


/*
TODO:

    FEATURE EXCLUSIVE TO CONFIG FILE SETTINGS:
    CAN SPECIFY IF REPEATING OF A WORD IS ALLOWED IN PASSPHRASE
    """documentation:
    during the word list entries, you can format like the following:

    { MIN : 0 , MAX : 5 } : <word>

    """
    OTHERWISE WORDS WILL HAVE A MIN OF 0 AND A MAX OF 1
*/

/*
EXTRA FUTURE TODO:
    ABILITY TO REPLACE STUFF LIKE H@XX0R-M@N AND 1337-DUD3
--- may not intruduce due to the nature of this app and how it would potentially be very computationaly heavy
*/


int main (void)
{
    U_HomePath = "C:/HAT/";
    cout << "[*] Dictator V0.99" << endl << endl;
    cout << "[!] Looking for fconfig files..." << endl << endl;
    vector<pair<int, vector<string>>> fconfigStatus = FConfigReader(U_HomePath);
    if (fconfigStatus[9].first == 1)
    {
        cout << "[$] An fconfig file has been loaded!" << endl;
    
        if (fconfigStatus[0].first != 0)
        {
            for (int wordIndex = 0 ; wordIndex < fconfigStatus[0].first ; wordIndex++)
            {
                L_wordList.push_back(fconfigStatus[0].second[wordIndex]);
            }
        }
        
        if (fconfigStatus[6].first == 1)
        {
            InputCollector();
        }
        else
        {
            cout << "[!] Loading only words from the fconfig..." << endl;
            L_wordListSize = L_wordList.size();
        }
    }
    else
    {
        InputCollector();
    }
    cout << "[$] Done!" << endl << endl
        << "[!] Attempting to create the file..." << endl << endl;
    if (fconfigStatus[9].first == 1)
    {
        if (fconfigStatus[2].first == 1)
        {
            D_Extension = fconfigStatus[2].second[0];
        }
        if (fconfigStatus[1].first == 1)
        {
            D_Name = fconfigStatus[1].second[0];
            if (fconfigStatus[5].first == 1)
            {
                PathFinder();
            }
            else
            {
                cout << "[!] File name and path has been loaded from the config file and will no longer be asked." << endl;
                L_filePath = U_HomePath + "Dictionaries/" + D_Name + D_Extension;
                cout << "[%] File Path : " << L_filePath << endl;
                NewFileCreator();
            }
        }
    }
    else
    {
        PathFinder();
    }
    cout << endl << endl << "[!] Setting options for dictionary..." << endl << endl;  
    
    pair<int,int> chainLs;
    if (fconfigStatus[9].first == 1)
    {
        bool ask_mini = true, ask_maxi = true;

        // 4 for min, 7 for a_min, 5 for max, 8 for a_max
        if (fconfigStatus[3].first == 1)
        {
            try
            {
                D_MinimumChain = stoi(fconfigStatus[3].second[0]); 
                if (fconfigStatus[7].first == 0)
                {
                    cout << "[!] Will not ask for the minimum chain length value, setting it to " << D_MinimumChain << " immediately." << endl;
                    ask_mini = false;
                }
                else
                {
                    cout << "[!] Defaulted the minimum chain length value to " << D_MinimumChain << " from the config file!" << endl;
                    ask_mini = true;
                }
            }
            catch (invalid_argument)
            {
                ask_mini = true;
            }
        }
        if (fconfigStatus[4].first ==  1)
        {
            try
            {
                D_MaximumChain = stoi(fconfigStatus[4].second[0]);
                if (fconfigStatus[8].first == 0)
                {
                    cout << "[!] Will not ask for the maximum chain length value, setting it to " << D_MaximumChain << " immediately." << endl;
                    ask_maxi = false;
                }
                else
                {
                    cout << "[!] Defaulted the maximum chain length value to " << D_MaximumChain << " from the config file!" << endl;
                    ask_maxi = true;
                }
            }
            catch (invalid_argument)
            {
                ask_maxi = true;
            }
        }
        cout << endl;
        chainLs = LengthGet(ask_mini, ask_maxi);
    }
    else
    {
        chainLs = LengthGet(true, true); 
    }
    clock_t creationStart = std::clock();
    cout << endl << endl << "[!] Creating dictionary..." << endl << endl;
    L_fileObj.open(L_filePath);
    Unchained();
    L_fileObj.close();
    clock_t creationStop = std::clock();
    clock_t deltaCTime = creationStop - creationStart;
    float minuteDCT = (double)deltaCTime / 60000;
    cout << endl << endl << "[$] Done!"
    << endl << "[$] It took " << std::setprecision(3) << std::fixed << minuteDCT << " minutes"
    << endl << "[$] The dictionary is saved at '" << L_filePath << "' "
    << endl << endl << "[$] Goodbye!" << endl << endl;
}

void InputCollector(void)
{
    cout << "[!] Getting inputs for dictionary..." << endl << endl;
    cout << "[@] Enter the first entry to the word list."
    << endl << "[@] When you're done, just leave the input blank and hit enter again."
    << endl << "[+] First entry: " ;
    string inputVar;
    while(getline(cin,inputVar) && inputVar != "")
    {
        L_wordList.push_back(inputVar);
        cout << "[@] Successfully added " + inputVar + " to input list!"
        << endl << "[+] Next entry : " ;
        inputVar = "";
    }
    cout << "[/] Stopped adding entries to list." << endl;
    L_wordListSize = L_wordList.size();
}

void NewFileCreator(void)
{
    ofstream fileInitObj(L_filePath);
    fileInitObj << " \n \n ";
    fileInitObj.close();
    cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
}

void PathFinder(void)
{
    string dictionaryPath = U_HomePath + "Dictionaries/";
    string fileName;
    string inputName;
    cout << "[@] Name the file to save your dictionary as"
    << endl << "[!] (Files will be saved at '" + dictionaryPath + "' as a '" + D_Extension + "' file )"
    << endl << "[@] Leave field blank to save your file name as the default dictionary.txt"
    << endl << "[@] If the file already exists, new data will be added to the beginning leaving old data untouched."
    << endl << "[?] File name : ";
    getline(cin,inputName);
    if (inputName == "")
    {
        fileName = D_Name + D_Extension;
    }
    else
    {
        fileName = inputName+D_Extension;
    }
    
    L_filePath = dictionaryPath + fileName;
    
    CreateDirectoryMan(dictionaryPath);
    NewFileCreator();
}

pair<int, int> LengthGet(bool min_ask, bool max_ask)
{
    if (min_ask)
    {
        string minInput;
        cout << "[?] Minimum value for the amount of values chanied together in a single possible combination?"
        << endl << "[@] (Example : 2 -> firstSecond, 3 -> firstSecondThird)"
        << endl << "[@] Will default to " + to_string(D_MinimumChain) + " in case of undefined input!"
        << endl << "[?] Minimum chain length value: ";
        getline(cin,minInput);
        int minLength;
        try
        {
            minLength = stoi(minInput);
            cout << "[$] Set minimum value to " + to_string(minLength) + " !" << endl;
        }
        catch (invalid_argument)
        {
            cout << "[!] Couldn't understand input, defaulting to " + to_string(D_MinimumChain) + " for minimum value" << endl;
            minLength = D_MinimumChain;
        }
        L_minChainLen = minLength;
    }
    else
    {
        L_minChainLen = D_MinimumChain;
    }
    if (max_ask)
    {
        string maxInput;
        cout << endl << "[?] Maximum value for the amount of values chanied together in a single possible combination?"
        << endl << "[@] Will default to " + to_string(D_MaximumChain) + " in case of undefined input!"
        << endl << "[?] Maximum chain length value: ";
        getline(cin,maxInput);
        int maxLength;
        try
        {
            maxLength = stoi(maxInput);
            cout << "[$] Set maximum value to " + to_string(D_MaximumChain) + " !" << endl;
        }
        catch (invalid_argument)
        {
            cout << "[!] Couldn't understand input, defaulting to " + to_string(D_MaximumChain) + " for maximum value" << endl;
            maxLength = D_MaximumChain;
        }
        L_maxChainLen = maxLength;
    }
    else
    {
        L_maxChainLen = D_MaximumChain;
    }
    if (L_minChainLen > L_maxChainLen)
    {
        cout << endl << "[!] ERR : Minimum value larger than maximum value."
        << endl << "[!] Exiting program..." << endl;
        exit(1);
    }
    pair<int,int> chainReturn = {L_minChainLen, L_maxChainLen};
    return chainReturn;
}

void Unchained(void)
{
    int minL = L_minChainLen;
    int maxL = L_maxChainLen;
    int percent = 0;
    int progressAmount = maxL - minL + 1;
    int percentPart = 100 / progressAmount;
    bool fullFlag = false;
    for (int currentLength = minL; currentLength <= maxL; currentLength++)
    {
        string based = "";
        WordListCreation(based, currentLength);
        percent += percentPart;
        if (percent < 100)
        {
            cout << "[!] Progress: " + to_string(percent) + "\%" << endl;
        }
        else
        {
            cout << "[$] 100% !" << endl;
            fullFlag = true;
        }
    }
    if (!fullFlag)
    {
        cout << "[$] 100% !" << endl;
    }
}

void WordListCreation(string baseString, int lengthVar)
{
    bool flag = false;
    string newString;
    for (int listIndex = 0; listIndex < L_wordListSize; listIndex++)
    {
        newString = baseString + L_wordList.at(listIndex);
        if (!flag)
        {
            lengthVar--;
            flag = true;
        }
        if (lengthVar > 0)
        {
            WordListCreation(newString,lengthVar);
        }
        else
        {
            string resultString = newString+"\n";
            L_fileObj << resultString;
        }
    }
}
