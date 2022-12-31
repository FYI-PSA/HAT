
namespace funtime
{

    void NewFileCreator(void)
    {
        ofstream fileInitObj(L_filePath);
        fileInitObj << " \n \n ";
        fileInitObj.close();
        cout << "[$] Successfully created file '" + L_filePath + "' !" << endl;
    }

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

}

