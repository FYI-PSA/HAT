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

using std::exit;

using std::vector;
using std::string;

using std::getline;

using std::fstream;
using std::ofstream;

using std::endl;
using std::cout;
using std::cin;

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
};

Word_List WordListObj;

int main ()
{
    cout << "[*] Dictator V0.1" << endl << endl;
    InputCollector();
    cout << "[!] Analyzing entries..." << endl << endl;
    cout << "[!] Creating word list..." << endl;
    PathFinder();
    string based = "";
    int maxLength = 4;
    WordListCreation(based, maxLength);
    cout << "[!] Writing to file..." << endl;
    WordListObj.finalWordListSize = WordListObj.finalWordList.size();
    WriteToFile();
    cout << endl << "[$] Done!" << endl;
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
    cout << "[@] Name the file to save your dictionary as"
    << endl << "[!] (Files will be saved at '" + homePath + "' as a .txt file )"
    << endl << "[?] File name : ";
    getline(cin,fileName);
    if (fileName == "")
        fileName = "dictionary.txt";
    else
        fileName = fileName+".txt";
    WordListObj.filePath = homePath + fileName;
    if (CreateDirectory(homePath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
        cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
    else
    {
        cout << "[#] ERR : COULDN'T CREATE FILE '" + WordListObj.filePath + "' !"
        << endl << "[!] ENDING PROGRAM [!]";
        exit(1);
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
    fstream fileObj(WordListObj.filePath);
    string listItem;
    for (int finalIndex = 0; finalIndex < WordListObj.finalWordListSize; finalIndex++)
    {
        listItem = WordListObj.finalWordList.at(finalIndex);
        fileObj << listItem;
    }
    fileObj.close();
}
