#ifndef DICTATOR_MODULES_DIRMAN_H
#define DICTATOR_MODULES_DIRMAN_H

#include <errno.h>
#include <filesystem>
#include <iostream>
#include <string>

using std::cout;
using std::endl;
using std::string;
using std::filesystem::path;

#ifndef __unix__
#include <io.h>
int __global_hat_mkdir(string dirpath)
{
    int successValue = mkdir(dirpath.c_str());
    return successValue;
}
#else
#include <sys/stat.h>
int __global_hat_mkdir(string dirpath)
{
    mode_t fullAdminPerm = S_IRWXU | S_IRUSR | S_IWUSR | S_IXUSR;
    mode_t fullGroupPerm = S_IRWXG | S_IRGRP | S_IWGRP | S_IXGRP;
    mode_t fullOtherPerm = S_IRWXO | S_IROTH | S_IWOTH | S_IXOTH;
    mode_t createFileMode =  fullAdminPerm | fullGroupPerm | fullOtherPerm; 
    int successValue = mkdir(dirpath.c_str(), createFileMode);
    return successValue;
}
#endif 

// return values : 
// 0 = directory successfully created
// 1 = directory was existing found
// -1 = unidentified edge case

namespace hat
{

    int CreateDirectoryMan(string createPath, bool giveFeedback)
    {
        int successValue = __global_hat_mkdir(createPath);
        int errors = 6;
        int errorValues[errors] = {EACCES, EEXIST, ENAMETOOLONG, ENOENT, ENOTDIR, EROFS}; 
        if (successValue == -1)
        {
            int errorCode = errno;
            if (giveFeedback)
            {
                cout << "[!] Couldn't create folder " << createPath << " because of an issue." << 
                endl << "[!] Trying to solve it..." << endl;
            }
            int matchingIndex = -1;
            for (int i = 0 ; i < errors; i++)
            {
                int currentError = errorValues[i]; 
                if (currentError == errorCode)
                {
                    matchingIndex = i;
                    break;
                }
            }
            if (matchingIndex != -1)
            {
                int currentError = errorValues[matchingIndex];
                if (giveFeedback)
                {
                    cout << "[!] Reason for the error: ";
                }
                if (currentError == EACCES)
                {
                    if (giveFeedback)
                    {
                        cout << "Access denied." << 
                        endl << "[@] Try asking a system admin for help or if you own this computer, run this program as administrator." << endl;
                        exit(1);
                    }
                    return 3;
                }
                else if (currentError == EEXIST)
                {
                    if (giveFeedback)
                    {
                        cout << endl << "[$] Located pre existing folder!" << endl;
                    }
                    return 1;
                }
                else if (currentError == ENAMETOOLONG)
                {
                    if (giveFeedback)
                    {
                        cout << "File name is too long or invalid." << endl;
                        exit(1); // maybe resort back to d_name ?
                    }
                    return 3;
                }
                else if (currentError == ENOENT)
                {
                    if (giveFeedback)
                    {
                        cout << "A parent folder is missing." <<
                        endl << "[!] Attempting to create missing parent folder..." << endl ;
                    }
                    path currentPath = createPath;
                    path parentPath = currentPath.parent_path();
                    string parentDir = parentPath.generic_string();
                    CreateDirectoryMan(parentDir, giveFeedback);
                    if (giveFeedback)
                    {
                        cout << "[$] Parent folder successfully created!" << 
                        endl <<"[!] Trying to create self again..." << endl;
                    }
                    CreateDirectoryMan(createPath, giveFeedback);
                    return 0;
                }
                else if (currentError == ENOSPC)
                {
                    if (giveFeedback)
                    {
                        cout << "File system out of storage space." << endl;
                        exit(1);
                    }
                    return 3;
                }
                else if (currentError == ENOTDIR)
                {
                    if (giveFeedback)
                    {
                        cout << "A mentioned parent 'folder' is not actually a folder." << endl;
                        exit(1);
                    }
                    return 3;
                }
                else
                {
                    if (giveFeedback)
                    {
                        cout << endl << "[!] Unidentified error!" << endl;
                        exit(1);
                    }
                    return -1;
                }
            }
            else
            {
                if (giveFeedback)
                {
                    cout << endl << "[!] Unidentified error!" << endl;
                    exit(1);
                }
                return -1;
            }
        }
        else
        {
            if (giveFeedback)
            {
                cout << "[$] Successfully created a new folder " << createPath << "!" << endl;
            }
            return 0;
        }

        return -1;
    }

    int CreateDirectoryMan(string createPath)
    {
        return CreateDirectoryMan(createPath, true);
    }

}

#endif
