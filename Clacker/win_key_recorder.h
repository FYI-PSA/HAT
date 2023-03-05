#ifndef _WIN_KEY_REC
#define _WIN_KEY_REC

#ifndef _STD_IO
#include <stdio.h>
#endif

#ifndef _STDBOOL_H
#include <stdbool.h>
#endif

#ifndef _INC_CONIO
#include <conio.h>
#endif

char returnKeyPress()
{
    char key;
    bool loop_cond = 1;
    while (loop_cond)
    {
        if(_kbhit())
        {
            key = _getch();
            loop_cond = 0;
        }
    }
    return key;
}

#endif