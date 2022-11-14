#include <iostream>
#include <string.h>
#include <string>
#include <vector>

using std::vector;
using std::string;

using std::getline;

using std::endl;
using std::cout;
using std::cin;

void InputCollector(void);
void WordListCreation(string baseString, int lengthVar);

class Word_List
{
    public:
        vector<string> wordList;
        int wordListSize;
};

Word_List WordListObj;

int main ()
{
    cout << "[#!] Dictator V0.1" << endl << endl;
    InputCollector();
    cout << "[$!] Analyzing entries..." << endl << endl;
    cout << "[$!] Creating word list..." << endl;
    string based = "";
    int maxLength = 3;
    WordListCreation(based, maxLength);
    cout << endl << "[$!$] Done!" << endl;
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
            cout << newString << endl;
        }
    }
}
