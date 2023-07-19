________________

## Install

  * ### _Windows:_
    - Download the [latest release of dictator for Windows](https://Github.com/FYI-PSA/Hat/releases/latest)
    - Simply launch the executable from CMD or PowerShell  

  * ### _Linux/Mac:_
    - Download the [latest release of dictator for Linux/Mac](https://Github.com/FYI-PSA/Hat/releases/latest)
    - Run the following command in the same folder as your downloaded executable:
    
    > (Replace {FILE} with the name of the file you just downloaded)

    * ```sudo chmod a+x ./{FILE}; cp ./{FILE} ~/.local/bin/dictator;```
    - You may now launch dictator at any time from your console by entering `dictator`

________________

## Compiling from source
  * Dictator does not have many dependencies, you can compile it by navigating to /Dictator/ in the sourc folders and then entering:
  
  * `mkdir Compiled; g++ Code/main.cpp -o Compiled/dictator --std=c++20`

________________

## Config files:
  * [config-help.txt](/Dictator/config-help.txt)

________________

## More help on using the software
  * All necessary information about using the software can be found by using the `--help` flag when executing

________________
