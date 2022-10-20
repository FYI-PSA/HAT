from pathlib import Path
import os
import time

global file_name, file_path, word_list, minimum_chain_value, maximum_chain_value, debug_value, starting_time


def Greeter():
    print("[!] Welcome to the dictionary generator!")
    InputGather([])


def ListCreation(x, k):
    flag_bool = False
    for f in word_list:
        z = x + f
        if not flag_bool:
            k = k - 1
            flag_bool = True
        if k > 0:
            ListCreation(z, k)
        else:
            WriteFunction(z)


# def WriteFunction(writer):
#     print(writer)


def WriteFunction(ink):
    with open(file_path, 'a') as Document:
        Document.write(ink + "\n")


def InputGather(pre_existing_info):
    global word_list
    word_list = pre_existing_info
    word_list.append(input("[+] Add entry to the list:\n[+] > "))
    print(f"[+] Added {word_list[0]} ✔")
    print("[!] Enter 'Dict stop' or leave the field empty when you're done adding entries to the list.")
    while True:
        temp_input = input("[+] > ")
        exit_list = [
            "",
            "Dict_Stop",
            "\'Dict_Stop\'",
            "Dict Stop",
            "\'Dict Stop\'",
            "Dict_stop",
            "\'Dict_stop\'",
            "Dict stop",
            "\'Dict stop\'",
            "dict stop",
            "dict_stop",
            "\'dict_stop\'",
            "\'dict stop\'"
        ]
        if temp_input in exit_list:
            print("[✶] Finalized list entries!")
            print("[✶] Analyzing and creating dictionary...")
            break
        else:
            word_list.append(temp_input)
            print(f"[+] Added {temp_input} ✔")
    print("")
    print("[!] Please don't open the dictionary file while the program is still running!")
    FileName()


def FileName():
    global file_name, file_path
    default_name = "dictionary.txt"
    print("\n\n")
    file_name = input(
        "[✶] Dictionary file name mode:"
        + "\n→ A for Automatic"
        + "\n→ M for Manual"
        + f"\n→ D to Default to '{default_name}'\n"
        + "\n[✶] (Leave empty to automatically set to default)\n"
        + "\n[+] > Select Mode: "
    )
    if file_name == "" or file_name == " " or file_name == "D" or file_name == "d":
        file_name = default_name
        print("[!] Default Mode ✔")
        print(f"[✔] File name saved as '{file_name}'")
    elif file_name == "A" or file_name == "a":
        if len(word_list) > 1:
            file_name = word_list[0] + word_list[1] + '.txt'
            print(f"[✔] File name saved as '{file_name}'")
        elif len(word_list) == 1:
            file_name = word_list[0] + '.txt'
            print(f"[✔] File name saved as '{file_name}'")
        else:
            file_name = default_name
            print(f"[✔] File name saved as '{file_name}'")
    elif file_name == "M" or file_name == "m":
        file_name = input("> Name :    ")
        file_name = file_name + '.txt'
        print(f"[✔] File name saved as '{file_name}'")
    else:
        file_name = default_name
        print("[!] Default Mode ✔")
        print(f"[✔] File name saved as '{file_name}'")

    print("\n\n")

    path_file_path = Path(os.path.expanduser(f'~/Documents/Generated-Dictionaries/{file_name}'))
    path_file_path.parent.mkdir(exist_ok=True, parents=True)
    file_path = str(path_file_path)
    try:
        ExistenceCheck()
    except FileNotFoundError:
        Blanker()
    ChainMaster()


def ExistenceCheck():
    global file_name, file_path
    empty_data = ["", " ", "\n", "   "]
    with open(file_path, 'r') as file_data:
        if file_data in empty_data:
            Blanker()
        else:
            print(
                f"[!] {file_name} contains data that may be important. Do you want to: "
                + "\n→ V to view the content of the file"
                + "\n→ O to overwrite the contents of the file"
                + "\n→ A to append to the end of the file"
            )
            print(
                "[!] Warning: Appending to the end of the file may break something!"
                + "\n[✶] Leave empty to default to Append mode\n"
            )
            choice = input("[✶] > Choose one: ")
            if choice == "V" or choice == "v":
                print(f"[✶] File Data:\n\n{file_data.read()}\n\n")
                choice_2 = input(
                    "[!] Do you want to now append to the end of the file or overwrite?"
                    + "\n→ O to overwrite the contents of the file"
                    + "\n→ A to append to the end of the file"
                    + "\n[✶] Leaving blank will default to append mode."
                    + "\n[✶] > Select Mode: ")
                if choice_2 == "O" or choice_2 == "o":
                    print("[!] Overwriting file... ")
                    print("[✔] Overwrote successfully!")
                    Blanker()
                elif choice_2 == "" or choice_2 == " " or choice_2 == "A" or choice_2 == "a":
                    print("[+] Append Mode! ✔")
            elif choice == "O" or choice == "o":
                print("[!] Overwriting file... ")
                print("[✔] Overwrote successfully!")
                Blanker()
            elif choice == "" or choice == " " or choice == "A" or choice == "a":
                print("[+] Append Mode! ✔")
    print("\n\n")


def Blanker():
    with open(file_path, "w") as blanker_file:
        blanker_file.write(" \n")


def ChainFunc(chain_end_type):
    if chain_end_type == "min":
        chain_type = "minimum"
        default_value = minimum_chain_value
    else:
        chain_type = "maximum"
        default_value = maximum_chain_value
    empty_void = ["", " ", "D", "d"]
    chain_input = input(
        f"[?] How long is your {chain_type} word chain?"
        + f"\n[✶] (Leave empty or type D to default to {default_value})"
        + "\n[+] > Input : "
    )
    try:
        chain_input = int(chain_input)
        not_int = False
    except ValueError:
        not_int = True
    if chain_input in empty_void:
        print(f"[✔] Set {chain_type} chain length to default {default_value}")
    else:
        if not_int:
            print(f"[!] Input value not understood, setting {chain_type} chain length to default of {default_value}")
        else:
            default_value = chain_input
            print(f"[✔] Set {chain_type} chain length to {default_value}")
    return default_value


def ChainMaster():
    global starting_time
    print("[✶] Specify length range of chains of words to be created :")
    min_chain = ChainFunc("min")
    print("")
    max_chain = ChainFunc("max")
    print("")
    print("[!] Warning: Large chains may take a lot of time to create!")
    print(f"[✔] Successfully modified chain lengths to start at {min_chain} and end at {max_chain}")
    print("\n")
    time.sleep(0.3)
    print(
        "[✔] Creating your dictionary... \n"
        + "[!] This may take a long time"
    )
    starting_time = time.time()
    for available_length in range(min_chain, max_chain+1):
        base_string = ""
        ListCreation(base_string, available_length)


def main():
    global minimum_chain_value, maximum_chain_value, debug_value
    minimum_chain_value = 1
    maximum_chain_value = 5
    debug_value = False
    Greeter()
    ending_time = time.time()
    creation_time = round((ending_time - starting_time)/60, 3)
    print(
        "[✔] Finished creating the dictionary successfully! "
        + f"It took {creation_time} minutes to create! "
        + "\n\n[✔] Here's where the file is saved: "
        + f"\n[File-Path]  {file_path}\n\n"
    )


if __name__ == '__main__':
    main()
