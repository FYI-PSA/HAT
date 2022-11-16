#define _CRT_SECURE_NO_WARNINGS

#include <Windows.h>
#include <process.h>
#include <Lmcons.h>
#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <string>
#include <vector>

using std::cin;
using std::cout;
using std::endl;
using std::getline;

using std::fstream;
using std::ofstream;

using std::stoi;
using std::string;
using std::to_string;

using std::exit;

using std::vector;

using std::invalid_argument;

void Unchained(void);
void LengthGet(void);
void PathFinder(void);
void WriteToFile(void);
void InputCollector(void);
void WordListCreation(string baseString, int lengthVar);

class Word_List
{
    public:
        vector<string> wordList;
        int wordListSize;

        string filePath;

        vector<string> finalWordList;
        int finalWordListSize;

        int maxChainLen;
        int minChainLen;
};

Word_List WordListObj;

int main ()
{
    cout << "[*] Dictator V0.1" << endl << endl;
    cout << "[!] Getting inputs for dictionary..." << endl << endl;
    InputCollector();
    cout << endl << endl << "[!] Creating file..." << endl << endl;
    PathFinder();
    cout << endl << endl << "[!] Setting options for dictionary..." << endl << endl;
    LengthGet();
    cout << endl << endl << "[!] Creating dictionary..." << endl << endl;
    Unchained();

    cout << endl << endl << "[$] Done!" << endl << endl;

    /*
    TODO:
    CALL WriteToFile IN EACH Unchained LOOP
    Fstream, can then Open that and write to it globally
    OFstream, writes to file directly
    read inputs from a file, just create dictionary
    */
}

void InputCollector()
{
    cout << "[@] Enter the first entry to the word list."
    << endl << "[@] When you're done, just leave the input blank and hit enter again."
    << endl << "[+] First entry: " ;
    string inputVar;
    while(getline(cin,inputVar) && inputVar != "")
    {
        WordListObj.wordList.push_back(inputVar);
        cout << "[@] Successfully added " + inputVar + " to input list!"
        << endl << "[+] Next entry : " ;
        inputVar = "";
    }
    cout << "[/] Stopped adding entries to list." << endl;
    WordListObj.wordListSize = WordListObj.wordList.size();
}

void PathFinder(void)
{
    char userName[UNLEN+1];
    DWORD userNameLength = UNLEN+1;
    GetUserName(userName, &userNameLength);
    string homePath = "C:/Users/" + (string)userName + "/Documents/Hackers-Toolbox/Dictionaries/";
    string fileName;
    string inputName;
    cout << "[@] Name the file to save your dictionary as"
    << endl << "[!] (Files will be saved at '" + homePath + "' as a .txt file )"
    << endl << "[@] Leave field blank to save your file name as the default dictionary.txt"
    << endl << "[@] If the file already exists, new data will be added to the beggining leaving old data untouched."
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
    WordListObj.filePath = homePath + fileName;
    if (CreateDirectory(homePath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
    {
        cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
    }
    else
    {
        cout << "[#] ERR : COULDN'T CREATE FILE '" + WordListObj.filePath + "' !"
        << endl << "[!] ENDING PROGRAM [!]";
        exit(1);
    }
}

void LengthGet(void)
{
    int defaultMin = 1;
    string minInput;
    cout << "[?] Minimum value for the amount of values chanied together in a single possible combination?"
    << endl << "[@] (Example : 2 -> firstSecond, 3 -> firstSecondThird)"
    << endl << "[@] Will default to " + to_string(defaultMin) + " in case of undefined input!"
    << endl << "[?] Minimum chain length value: ";
    getline(cin,minInput);
    int minLength = defaultMin;
    try
    {
        minLength = stoi(minInput);
        cout << endl << minLength << endl;
    }
    catch (invalid_argument)
    {
        cout << "[!] Couldn't understand input, defaulting to " + to_string(defaultMin) + " for minimum value" << endl;
    }
    int defaultMax = 6;
    string maxInput;
    cout << endl << "[?] Maximum value for the amount of values chanied together in a single possible combination?"
    << endl << "[@] Will default to " + to_string(defaultMax) + " in case of undefined input!"
    << endl << "[?] Maximum chain length value: ";
    getline(cin,maxInput);
    int maxLength = defaultMax;
    try
    {
        maxLength = stoi(maxInput);
        cout << endl << maxLength << endl;
    }
    catch (invalid_argument)
    {
        cout << "[!] Couldn't understand input, defaulting to " + to_string(defaultMax) + " for maximum value" << endl;
    }
    WordListObj.minChainLen = minLength;
    WordListObj.maxChainLen = maxLength;
}

void Unchained(void)
{
    int minL = WordListObj.minChainLen;
    int maxL = WordListObj.maxChainLen;
    int percent = 0;
    int progressAmount = maxL - minL + 1;
    int percentPart = 100 / progressAmount;
    bool fullFlag = false;
    for (int currentLength = minL; currentLength <= maxL; currentLength++)
    {
        string based = "";
        WordListCreation(based, currentLength);
        percent += percentPart;
        WordListObj.finalWordListSize = WordListObj.finalWordList.size();
        WriteToFile();
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
    for (int listIndex = 0; listIndex < WordListObj.wordListSize; listIndex++)
    {
        newString = baseString + WordListObj.wordList.at(listIndex);
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
            WordListObj.finalWordList.push_back(newString+"\n");
        }
    }
}

void WriteToFile(void)
{
    ofstream fileObj(WordListObj.filePath);
    string listItem;
    for (int finalIndex = 0; finalIndex < WordListObj.finalWordListSize; finalIndex++)
    {
        listItem = WordListObj.finalWordList.at(finalIndex);
        fileObj << listItem;
    }
    fileObj.close();
    WordListObj.finalWordList.clear();
    WordListObj.finalWordListSize = 0;
}
