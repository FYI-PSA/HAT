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
void Printer(string text);
void Printer(string text, bool newLine);
void WordListCreatino(string baseString, int lengthVar);

class Word_List
{
    public:
        vector<string> list;
        int size;
};

Word_List WordListObj;

int main ()
{
    InputCollector();
}

void InputCollector()
{
    cout << "Enter input:\nEnter nothing in the input to stop.\n| >> " ;
    string inputVar;
    while(getline(cin,inputVar) && inputVar != "")
    {
        WordListObj.list.push_back(inputVar);
        Printer("Successfully added "+inputVar+" to input list!");
        inputVar = "";
    }
    Printer("Stopped adding entries to list.");
    WordListObj.size = WordListObj.list.size();
    cout << WordListObj.size << endl;
}


void Printer(string text)
{
    Printer(text, true);
}

void Printer(string text, bool newLine)
{
    cout << text;
    if(newLine)
        cout << endl;
}

void WordListCreation(string baseString, int lengthVar)
{
    bool flag = false;
}
