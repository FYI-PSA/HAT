#include <iostream>
#include <string.h>
#include <vector>

class Word_List
{
    public:
        std::string wordStart;
        int listLength;
};

int main ()
{
    Word_List wordList;
    std::cin >> wordList.wordStart;
    std::cout << wordList.wordStart;
}
