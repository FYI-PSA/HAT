//#define _CRT_SECURE_NO_WARNINGS
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
#include <string>
#include <vector>
#include <time.h>
#include <ctime>

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
bool FConfigReader(void);
void NewFileCreator(void);
void InputCollector(void);
void PathErrors(string createPath);
void WordListCreation(string baseString, int lengthVar);

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
    FConfigReader();
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
/*
TODO :
- maybe bring back the old system of overriding a file if it exists, instead of always appending to the start.
 ^ could be an idea for the GUI version
*/
}

bool FConfigReader(void)
{
    string fconfigPath = U_HomePath + "PreConfigs/";

    if (CreateDirectory(fconfigPath.c_str(), NULL))
    {
        cout << "[!] No previous folder for pre configurations found." << endl;
        return false;
    }
    else if (GetLastError() == ERROR_PATH_NOT_FOUND)
    {
        cout << "[!] Main folder for the HAT app doesn't exist. No fconfig files found." << endl;
        return false;
    }
    else if (GetLastError() == ERROR_ALREADY_EXISTS)
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


    // File creation errors to account for:

    // DWORD 2 : Can not find the file specified 
    // - defined as ERROR_FILE_NOT_FOUND
    // Notes :
    // *Not for this section of the code*

    // DWORD 3 : Can not find the path specified 
    // - defined as ERROR_PATH_NOT_FOUND
    // Notes :
    // A parent directory is missing, try creating that, and check for the same errors
    //   - Again, if a parent directory is missing, then try creating that and check for the same errors
    //     - Put this in some sort of function or class to avoid repeating code too much
    //     [At most will repeat twice to create Documents in the ~ Home path]

    // DWORD 4 : Can not open the file specified 
    // - defined as ERROR_TOO_MANY_OPEN_FILES
    // *Not for this section of the code*

    // DWORD 5 : Access is denied 
    // - defined as ERROR_ACCESS_DENIED
    // Notes:
    // Exit out and tell the user to either run as admin or ask the admin for permission on the ~/Documents folder

    // DWORD 18 : There are no more files 
    // - defined as ERROR_NO_MORE_FILES
    // *Not for this section of the code*

    // DWORD 183 : Can not create an already existing file 
    // - defined as ERROR_ALREADY_EXISTS
    // Notes:
    // Cool, located pre existing folder, make your file in there, no more need for creating directories.

    // Any other value: throw exception, exit, out the code and the text form of the message.

void PathErrors(string attemptingPath)
{
    if (CreateDirectory(attemptingPath.c_str(), NULL) != 0)
    {
        cout << "[$] Directory " + attemptingPath + " was created successfully!" << endl;
        return; // it was successful
    }
    DWORD errorCode = ::GetLastError();
    // cout << "Error Code : " << errorCode << "  |  ";
    // cout << "Error Text : " << system_category().message(errorCode) << endl;
    if (errorCode == 3)
    {
        path orphanPath = attemptingPath;
        path noParent = orphanPath.parent_path();
        string strungParent = noParent.generic_string();
        cout << "[!] Parent folder missing, attempting to create..." << endl;
        PathErrors(strungParent);
        cout << "[!] Now trying to create the subfolder again..." << endl;
        PathErrors(attemptingPath);
        return;
    }
    else if (errorCode == 5)
    {
        cout << "[#] It seems you don't have the permission to create your dictionary's folder to contain your files." <<
        endl << "[@] Try running again as an Administrator or contact your system's admin for help." << endl;
        exit(1);
        return;
    }
    else if (errorCode == 183)
    {
        cout << "[$] Located pre existing containing folder..." << endl;
        return;
    }
    else
    {
        cout << "[#] Something went wrong internernally, here's the issue code and it's text:" << 
        endl << "[%] Error Code : " << errorCode << "  |  "
        << "Error Text : " << system_category().message(errorCode) << endl;
        cout << "[@] Please report to the following link the above Error Code and Error Text to fix the issue in the next update:" <<
        endl << "[%] https://www.GitHub.com/Funtime-UwU/HAT/Issues/" << endl;
        exit(1);
        return;
    }
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
    
    PathErrors(dictionaryPath);
    NewFileCreator();
    
    /*
    cout << "Attempting to create something at " << dictionaryPath.c_str() << endl;
    if(CreateDirectory(dictionaryPath.c_str(), NULL))
    {
        cout << "Created directory " << dictionaryPath.c_str() << " with success." << endl;
    }
    else
    {
        cout << "Coudn't create directory at " << dictionaryPath.c_str() << " because of this: " << endl;
        DWORD ERRMSG = ::GetLastError();
        cout << "Code : " << ERRMSG << "  |  ";
        cout << "Message : " << system_category().message(ERRMSG) << endl;
    }
    
    // Make PathErrors() a switch case that checks for errors, exists, and exceptions, and creates the directory called for in param

    // 
    // BUG:
    // "CreateDirectory" SEEMS TO NOT BE WORKING CORRECTLY SOMETIMES
    // CORRECTION:
    // "CreateDirectory" DOESN'T WORK AT ALL. THE ERROR MESSAGES ARE NOT GETTING DETECTED, ALWAYS GOING TO ELSE CLAUSE.
    // CAN'T CREATE ANY DIRECTORY. ONLY THE FILE IF "~/Documents/HAT/Dictionaries" EXISTS
    // 
    
    if (::GetLastError() == ERROR_ALREADY_EXISTS)
    {
        //there's a HAT in DOCUMENTS, there's also a Dictionaries folder.
        cout << "[!] Located pre-existing 'Dictionaries' folder..." << endl;
        ofstream fileInitObj(L_filePath);
        fileInitObj << " \n \n ";
        fileInitObj.close();
        cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
    }
    else if (::GetLastError() == ERROR_PATH_NOT_FOUND)
    {
        //there's not a HAT in DOCUMENTS
        if (CreateDirectory(U_HomePath.c_str(), NULL))
        {
            //there's a DOCUMENTS in USER
            cout << "[!] Creating the parent folder '" + U_HomePath + "'... " << endl;
            if (CreateDirectory(dictionaryPath.c_str(), NULL) || ::GetLastError() == ERROR_ALREADY_EXISTS)
            {
                ofstream fileInitObj(L_filePath);
                fileInitObj << " \n \n ";
                fileInitObj.close();
                cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
            }
        }
        else if(::GetLastError() == ERROR_ALREADY_EXISTS)
        {
            //there's not a HAT in DOCUMENTs, but there's a HAT in DOCUMENTS
            cout << "[#] ERR : CAN'T WORK WITH SHRODINGER'S TOOLBOX!"
            << endl << "[!] ENDING PROGRAM [!]";
            exit(1);
        }
        else if(::GetLastError() == ERROR_PATH_NOT_FOUND)
        {
            //there's no DOCUMENTS at all
            cout << "[#] ERR : USER HAS NO DOCUMENTS FOLDER!";
            if (CreateDirectory(U_DocumentsPath.c_str(), NULL))
            {
                //there's a USER
                cout << "[!] Creating 'Documents' folder for " + U_UserName + "... " << endl;
                if (CreateDirectory(U_HomePath.c_str(), NULL) || ::GetLastError() == ERROR_ALREADY_EXISTS)
                {
                    cout << "[!] Creating the parent folder '" + U_HomePath + "'... " << endl;
                    if (CreateDirectory(dictionaryPath.c_str(), NULL) || ERROR_ALREADY_EXISTS == GetLastError())
                    {
                        ofstream fileInitObj(L_filePath);
                        fileInitObj << " \n \n ";
                        fileInitObj.close();
                        cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
                    }
                }
            }
            else if (::GetLastError() == ERROR_ALREADY_EXISTS)
            {
                //there's no DOCUMENTS at all, however, DOCUMENTS exists.
                cout << "[#] ERR: CAN'T WORK WITH SHRODINGER'S DOCUMENTS!"
                << endl << "[!] ENDING PROGRAM [!]";
                exit(1);
            }
            else if (::GetLastError() == ERROR_PATH_NOT_FOUND)
            {
                //there is no USER
                cout << "[#] USER DOESN'T EXIST, WHICH MEANS THIS PROGRAM WAS NEVER RUN!"
                << endl << "[#] [!] ERR : PARADOX DETECTED! [!] [#]"
                << endl << "[#] [!] ENDING SIMULATION [!] [#]";
                exit(1);
            }
        }
    }
    else
    { 
        //there's a HAT in DOCUMENTS, there's not a Dictionaries folder.
        cout << "[!] Creating 'Dictionaries' folder..." << endl;
        CreateDirectory(dictionaryPath.c_str(), NULL);
        ofstream fileInitObj(L_filePath);
        fileInitObj << " \n \n ";
        fileInitObj.close();
        cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
    }

    */


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
