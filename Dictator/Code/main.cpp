/* "Github.com/Funtime-UwU/Hat" */
// #define _CRT_SECURE_NO_WARNINGS


// #include <time.h>
#include <ctime>
#include <fstream>
#include <iostream>
#include <string>
#include <utility>
#include <vector>

#include "Modules/DirectoryManager.h"
using hat::CreateDirectoryMan;
#include "Modules/ConfigFileFinder.h"
using hat::FConfigReader;

#include "Modules/ConfigHelper.h"

using std::cin;
using std::cout;
using std::endl;

using std::fstream;
using std::ifstream;
using std::ofstream;

using std::getline;

using std::vector;
using std::pair;

using std::stoi;
using std::string;
using std::to_string;
using std::invalid_argument;

vector<string> HandleLaunchParams(int argumentC, char** argumentV);

void PathFinder(void);

void NewFileCreator(void);

void Unchained(void);
pair<int,int> LengthGet(bool ask_min, bool ask_max);

void InputCollector(void);
void WordListCreation(string baseString, int lengthVar, vector<int> ignoreIndexes);

#ifndef __unix__
string Operating_System = "Windows";
string homePathCreator()
{
    string homePath = "C:/Hat/";
    return homePath;
}
#else
#include <unistd.h>
string Operating_System = "Linux";
string homePathCreator()
{
    string username = getlogin();
    string homePath = "/home/" + username + "/Hat/";
    return homePath;
}
#endif

fstream L_fileObj;
string L_filePath;
int L_maxChainLen;
int L_minChainLen;
int L_wordListSize;
vector<string> L_wordList;

string U_HomePath;

string D_Name = "dictionary";
string D_Extension = ".txt";
int D_MinimumChain = 1;
int D_MaximumChain = 6;

bool A_Name = true;
bool A_Entry = true;
bool A_MinimumChain = true;
bool A_MaximumChain = true;

bool F_SearchForConfig = true;
string F_CustomPath = "";

string O_Prefix = ""; bool O_Prefix_Set = false;
string O_Suffix = ""; bool O_Suffix_Set = false;
string D_Prefix = ""; bool D_Prefix_Set = false;
string D_Suffix = ""; bool D_Suffix_Set = false;

/*
TODO:
- Rewrite sub modules of header files for linux 
*/

int main (int argc, char** argv)
{
    cout << "[*] Dictator V2.1" << endl << endl;
    
    U_HomePath = homePathCreator();

    HandleLaunchParams(argc, argv);

    if (F_SearchForConfig)
    {
        cout << "[!] Looking for fconfig files..." << endl << endl;
    }
    vector<pair<int, vector<string>>> fconfigStatus = FConfigReader(U_HomePath, F_SearchForConfig, F_CustomPath);
    if (fconfigStatus[9].first == 1)
    {
        cout << "[$] An fconfig file has been loaded!" << endl;
    
        if (fconfigStatus[10].first != 0)
        {
            D_Prefix = fconfigStatus[10].second[0];
            D_Prefix_Set = true;
        }
        if (D_Prefix_Set && O_Prefix_Set)
        {
            cout << "[!] Confict in prefix options detected, setting to launch paramater as it has a higher priority"
            << endl << "[$] Prefix is : " + O_Prefix 
            << endl;
        }
        if (D_Prefix_Set && !O_Prefix_Set)
        {
            O_Prefix = D_Prefix;
            cout << "[$] Prefix is : " + O_Prefix << endl;
        }

        if (fconfigStatus[11].first != 0)
        {
            D_Suffix = fconfigStatus[11].second[0];
            D_Suffix_Set = true;
        }
        if (D_Suffix_Set && O_Suffix_Set)
        {
            cout << "[!] Conflict in suffix options detected, setting to launch paramater as it has a higher priority"
            << endl << "[$] Suffix is : " + O_Suffix
            << endl;
        }
        if (D_Suffix_Set && !O_Suffix_Set)
        {
            O_Suffix = D_Suffix;
            cout << "[$] Suffix is : " + O_Suffix << endl;
        }

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

        cout << "[@] " << L_wordListSize << " words were loaded!" << endl; 
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
    uint32_t creationStart = std::time(NULL);
    cout << endl << endl << "[!] Creating dictionary..." << endl << endl;
    L_fileObj.open(L_filePath);
    Unchained();
    L_fileObj.close();
    uint32_t creationStop = std::time(NULL);
    uint32_t deltaCTime = creationStop - creationStart;
    float minuteDCT = (static_cast<double>(deltaCTime)) / 60;
    cout << endl << endl << "[$] Done!"
    << endl << "[$] It took " << std::setprecision(3) << std::fixed << minuteDCT << " minutes"
    << endl << "[$] The dictionary is saved at '" << L_filePath << "' "
    << endl << endl << "[$] Goodbye!" << endl << endl;
}

vector<string> HandleLaunchParams(int argCount, char** argArr)
{
    vector<string> launchParams = {};
    for (int argumentIndex = 0; argumentIndex < argCount; argumentIndex++)
    {
        launchParams.push_back(argArr[argumentIndex]);
    }

    bool paramDetected = false;
    bool hasCustomConfig = false;
    bool hasSuffix = false;
    bool hasPrefix = false;
    for (auto& paramater : launchParams)
    {
        if (paramater == "-h" || paramater == "-help" || paramater == "--help")
        {
            paramDetected = true;
            cout
            << endl << "[@] Use '--prefix <output prefix>' to set a prefix at the start of each output in the dictionary file"
            << endl << "[@] There is no prefix by default, unless specified in a custom config file"
            << endl;
            cout
            << endl << "[@] Use '--suffix <output suffix>' to set a suffix at the end of each output in the dictionary file"
            << endl << "[@] There is no suffix by default, unless specified in a custom config file"
            << endl;
            cout
            << endl << "[@] Use '--conf-help' for help about how config files work."
            << endl;
            cout
            << endl << "[@] Use '--conf-file <config file path>' to load a custom config file"
            << endl << "[@] By default the first valid file in an alphabetical order is loaded from the default configs folder"
            << endl << "[%] Default Location: " + U_HomePath + "PreConfigs"
            << endl;
            cout
            << endl << "[@] Use '--ignore-conf' to ignore all configuration files"
            << endl << "[@] (If used with '--conf-file' it will still ignore all configurations)"
            << endl;
            cout
            << endl << endl;
            exit(0);
        }
        if (paramater == "--conf-help")
        {
            paramDetected = true;
            string config_help = hat::configHelpData();
            cout << endl << endl << config_help << endl << endl;
            exit(0);
        }
        if (paramater == "--ignore-conf")
        {
            paramDetected = true;
            cout << "[!] Will ignore all configuration files" << endl;
            F_SearchForConfig = false;
        }
        if (hasCustomConfig)
        {
            cout << "[!] Will load custom config file : '" + paramater + "'" << endl;
            F_CustomPath = paramater;
            hasCustomConfig = false;
        }
        else if (paramater == "--conf-file")
        {
            paramDetected = true;
            hasCustomConfig = true;
        }
        if (hasPrefix)
        {
            cout << "[$] Custom prefix of '" + paramater + "' was detected." << endl;
            O_Prefix = paramater;
            O_Prefix_Set = true;
            hasPrefix = false;
        }
        else if (paramater == "--prefix")
        {
            paramDetected = true;
            hasPrefix = true;
        }
        if (hasSuffix)
        {
            cout << "[$] Custom suffix of '" + paramater + "' was detected." << endl;
            O_Suffix = paramater;
            O_Suffix_Set = true;
            hasSuffix = false;
        }
        else if (paramater == "--suffix")
        {
            paramDetected = true;
            hasSuffix = true;
        }
    }
    if (hasCustomConfig)
    {
        cout << "[#] Custom config file :  !!ERR {No file specified, flag will be ignored.}!!" << endl;
    }
    if (!paramDetected && (argCount > 1))
    {
        cout << endl << "[#] Incorrect launch paramaters, run with '--help' to see all valid flags." << endl << endl;
        exit(0);
    }
    return launchParams;
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
    if (L_maxChainLen > L_wordListSize)
    {
        cout << endl << "[!] ERR : Minimum length longer than all words, shortening max to count of words.";
        L_maxChainLen = L_wordListSize;
        cout << endl << "[@] Maximum value is now " << L_maxChainLen << endl;
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
        vector<int> ignoreIndexes = {};
        WordListCreation(based, currentLength, ignoreIndexes);
        percent += percentPart;
        if (percent < 100)
        {
            cout << "[!] Progress: " + to_string(percent) + "%" << endl;
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

/*

solution:
- LIST_IDS : make an ARRAY the length of all words, assign each one an ID by index [so dupes dont get confused]
- USED_IDS : make an empty VECTOR
- each time a word is used, append to the end of vector
- check all of the vector with variable LISTINDEX signifying the current id in the wordlist
- if it matches
- vector needs to be local and passed through so that if it's used in one iteration it doesn't count for another iteration


*/
void WordListCreation(string baseString, int lengthVar, vector<int> usedIndexes)
{
    bool lengthFlag = false;
    bool indexUsedFlag = false;
    string newString;
    for (int listIndex = 0; listIndex < L_wordListSize; listIndex++)
    {
        if (!lengthFlag)
        {
            lengthVar--;
            lengthFlag = true;
        }

        bool canCreate = true;
        for (auto& usedIndex : usedIndexes)
        {
            if (listIndex == usedIndex)
            {
                canCreate = false;
                break;
            }
        }
        if (!indexUsedFlag)
        {
            usedIndexes.push_back(listIndex);
            //indexUsedFlag = true;
        }

        if (canCreate)
        {
            newString = baseString + L_wordList.at(listIndex);
            if (lengthVar > 0)
            {
                WordListCreation(newString, lengthVar, usedIndexes);
                usedIndexes.pop_back();
            }
            else
            {
                string resultString = O_Prefix + newString + O_Suffix + "\n";
                L_fileObj << resultString;
            }
        }
    }
}
