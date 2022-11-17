//#define _CRT_SECURE_NO_WARNINGS

#include <Windows.h>
#include <process.h>
#include <Lmcons.h>
#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <iomanip>
#include <string>
#include <vector>
#include <time.h>
#include <ctime>

using std::cin;
using std::cout;
using std::endl;
using std::getline;

using std::fstream;
using std::ofstream;

using std::stoi;
using std::string;
using std::to_string;

void Unchained(void);
void LengthGet(void);
void PathFinder(void);
void WriteToFile(void);
void InputCollector(void);
void WordListCreation(string baseString, int lengthVar);

class Word_List
{
    public:
        std::vector<string> wordList;
        int wordListSize;

        fstream fileObj;
        string filePath;

        int maxChainLen;
        int minChainLen;
};

Word_List WordListObj;

int main ()
{
    cout << "[*] Dictator V0.98" << endl << endl;
    cout << "[!] Getting inputs for dictionary..." << endl << endl;
    InputCollector();
    cout << endl << endl << "[!] Creating file..." << endl << endl;
    PathFinder();
    cout << endl << endl << "[!] Setting options for dictionary..." << endl << endl;
    LengthGet();
    clock_t creationStart = std::clock();
    cout << endl << endl << "[!] Creating dictionary..." << endl << endl;
    WordListObj.fileObj.open(WordListObj.filePath);
    Unchained();
    WordListObj.fileObj.close();
    clock_t creationStop = std::clock();
    clock_t deltaCTime = creationStop - creationStart;
    float minuteDCT = (double)deltaCTime / 60000;
    cout << endl << endl << "[$] Done!"
    << endl << "[$] It took " << std::setprecision(2) << minuteDCT << " minutes"
    << endl << "[$] The dictionary is saved at '" + WordListObj.filePath + "' "
    << endl << endl << "[$] Goodbye!" << endl << endl;
    /*
    TODO:

    maybe bring back the old system of overriding a file if it exists, instead of always appending to the start.
    ^ could be an idea for the GUI version

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
    string usrName = (string)userName;
    string userHome = "C:\\Users\\" + usrName + "\\";
    string userDocuments = userHome + "Documents\\";
    string parentAppPath = userDocuments + "Hackers-Toolbox\\";
    string homePath = parentAppPath + "Dictionaries\\";
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
    //Creating the file and the folder :
    //there's a Hackers-Toolbox in DOCUMENTS, there's no Dictionaries folder.
    if (CreateDirectory(homePath.c_str(), NULL))
    {
        cout << "[!] Creating 'Dictionaries' folder..." << endl;
        ofstream fileInitObj(WordListObj.filePath);
        fileInitObj << " \n \n ";
        fileInitObj.close();
        cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
    }
    //there's a Hackers-Toolbox in DOCUMENTS, there's also a Dictionaries folder.
    else if (GetLastError() == ERROR_ALREADY_EXISTS)
    {
        cout << "[!] Located pre-existing 'Dictionaries' folder..." << endl;
        ofstream fileInitObj(WordListObj.filePath);
        fileInitObj << " \n \n ";
        fileInitObj.close();
        cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
    }
    //there's not a Hackers-Toolbox in DOCUMENTS
    else if (GetLastError() == ERROR_PATH_NOT_FOUND)
    {
        //there's a DOCUMENTS in USER
        if (CreateDirectory(parentAppPath.c_str(), NULL))
        {
            cout << "[!] Creating the parent folder '" + parentAppPath + "'... " << endl;
            if (CreateDirectory(homePath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
            {
                ofstream fileInitObj(WordListObj.filePath);
                fileInitObj << " \n \n ";
                fileInitObj.close();
                cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
            }
        }
        //there's not a Hackers-Toolbox in DOCUMENTs, but there's a Hackers-Toolbox in DOCUMENTS
        else if(GetLastError() == ERROR_ALREADY_EXISTS)
        {
            cout << "[#] ERR : CAN'T WORK WITH SHRODINGER'S FOLDER!"
            << endl << "[!] ENDING PROGRAM [!]";
            std::exit(1);
        }
        //there's not a DOCUMENTS at all
        else if(GetLastError() == ERROR_PATH_NOT_FOUND)
        {
            cout << "[#] ERR : USER HAS NO DOCUMENTS FOLDER!";
            //there's a USER
            if (CreateDirectory(userDocuments.c_str(), NULL))
            {
                cout << "[!] Creating 'Documents' folder for " + usrName + "... " << endl;
                if (CreateDirectory(parentAppPath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
                {
                    cout << "[!] Creating the parent folder '" + parentAppPath + "'... " << endl;
                    if (CreateDirectory(homePath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
                    {
                        ofstream fileInitObj(WordListObj.filePath);
                        fileInitObj << " \n \n ";
                        fileInitObj.close();
                        cout << "[$] Successfully created file '" + WordListObj.filePath + "' !" << endl;
                    }
                }
            }
            //there's not a DOCUMENTS at all, however, DOCUMENTS exists.
            else if (GetLastError() == ERROR_ALREADY_EXISTS)
            {
                cout << "[#] ERR: CAN'T WORK WITH SHRODINGER'S DOCUMENTS!"
                << endl << "[!] ENDING PROGRAM [!]";
                std::exit(1);
            }
            //there is no USER
            else if (GetLastError() == ERROR_PATH_NOT_FOUND)
            {
                cout << "[#] USER DOESN'T EXIST, WHICH MEANS THIS PROGRAM WAS NEVER RUN!"
                << endl << "[#] [!] ERR : PARADOX DETECTED! [!] [#]"
                << endl << "[#] [!] ENDING SIMULATION [!] [#]";
                std::exit(1);
            }
        }
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
        cout << "[$] Set minimum value to " + to_string(minLength) + " !" << endl;
    }
    catch (std::invalid_argument)
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
        cout << "[$] Set maximum value to " + to_string(maxLength) + " !" << endl;
    }
    catch (std::invalid_argument)
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
            string resultString = newString+"\n";
            WordListObj.fileObj << resultString;
        }
    }
}
