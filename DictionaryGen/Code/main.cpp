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

using std::cin;
using std::cout;
using std::endl;

using std::fstream;
using std::ifstream;
using std::ofstream;

using std::getline;

using std::vector;

using std::filesystem::path;
using std::filesystem::directory_iterator;

using std::stoi;
using std::string;
using std::to_string;
using std::invalid_argument;

using std::system_category;

void Unchained(void);
void LengthGet(void);
void PathFinder(void);
void WriteToFile(void);
void NewFileCreator(void);
void InputCollector(void);
void PathErrors(string createPath);
void WordListCreation(string baseString, int lengthVar);

bool FConfigReader(void);

int CreateDirectoryMan(string createPath);
int CreateDirectoryMan(string createPath, bool giveFeedback);

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

int main (void)
{
    char userName[UNLEN+1];
    DWORD userNameLength = UNLEN+1;
    GetUserName(userName, &userNameLength);
    U_UserName = (string)userName;
    U_UserPath = "C:/Users/" + U_UserName + "/";
    U_DocumentsPath = U_UserPath + "Documents/";
    U_HomePath = U_DocumentsPath + "HAT/";

    cout << "[*] Dictator V0.99" << endl << endl;
    cout << "[!] Looking for fconfig files..." << endl << endl;
    bool fconfigStatus = FConfigReader();
    if (fconfigStatus)
        cout << "[$] An fconfig file has been loaded!" << endl;
    cout << "[!] Getting inputs for dictionary..." << endl << endl;
    InputCollector();
    cout << endl << endl << "[!] Creating file..." << endl << endl;
    PathFinder();
    cout << endl << endl << "[!] Setting options for dictionary..." << endl << endl;
    LengthGet();
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
    << endl << "[$] The dictionary is saved at '" + L_filePath + "' "
    << endl << endl << "[$] Goodbye!" << endl << endl;
}


void InputCollector(void)
{
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
    << endl << "[!] (Files will be saved at '" + dictionaryPath + "' as a .txt file )"
    << endl << "[@] Leave field blank to save your file name as the default dictionary.txt"
    << endl << "[@] If the file already exists, new data will be added to the beginning leaving old data untouched."
    << endl << "[?] File name : ";
    getline(cin,inputName);
    if (inputName == "")
    {
        fileName = "dictionary.txt";
    }
    else
    {
        fileName = inputName+".txt";
    }
    
    L_filePath = dictionaryPath + fileName;
    
    CreateDirectoryMan(dictionaryPath);
    NewFileCreator();
}

void LengthGet(void)
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
    L_minChainLen = minLength;
    L_maxChainLen = maxLength;
    if (L_minChainLen > L_maxChainLen)
    {
        cout << endl << "[!] ERR : Minimum value larger than maximum value."
        << endl << "[!] Exiting program..." << endl;
        exit(1);
    }
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
