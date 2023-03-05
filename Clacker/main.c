#ifndef _STD_IO
#include <stdio.h>
#endif

#ifndef _WIN_KEY_REC
#include "win_key_recorder.h"
#endif

int mode = 1; // 1 : ascii numbers | 2 : characters

int main(void)
{
    while(1)
    {
        char key = returnKeyPress();
        if (mode == 1)
        {
            printf(" %d ", key);
            continue;
        }
        if (key == '\n' || key == '\r') 
        {
            printf("<ENTER>");
            continue;
        }
        if (key == 8)
        {
            printf("<BACKSPACE>");
            continue;
        }
        if (key == 27)
        {
            printf("<ESC>");
            continue;
        }
        printf("%c", key);
    }
}