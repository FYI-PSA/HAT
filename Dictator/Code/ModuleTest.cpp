#include "./Modules/ConfigFileFinder.h"
#include "./Modules/DirectoryManager.h"

using hat::CreateDirectoryMan;
using hat::FConfigReader;

int main(int argc, char ** argv)
{
    string homePath = "C:/HAT/";
    if (argc > 1)
        homePath = argv[1];

    CreateDirectoryMan(homePath+"/Dictionaries/");
    CreateDirectoryMan(homePath+"/PreConfigs/");
    vector<pair<int, vector<string>>> configReturn = FConfigReader(homePath, true, "");
    int rows = configReturn.size();
    for (int row = 0 ; row < rows ; row++)
    {
        cout << "Row " << row+1 << " : " << endl << configReturn[row].first << "  ,  ";
        vector<string> secondColumn = configReturn[row].second;
        for (int entry = 0 ; entry < secondColumn.size() ; entry++)
        {
            cout << secondColumn[entry];
            if (entry != secondColumn.size()-1)
                cout << " | ";
            else
                cout << endl;
        }
        cout << endl;
    }
}
