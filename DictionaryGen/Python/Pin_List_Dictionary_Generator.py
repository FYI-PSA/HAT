from pathlib import Path
import os
import time

global file_name, file_path, word_list, minimum_chain_value, maximum_chain_value, debug_value, starting_time


def Greeter():
    print("[!] Welcome to pin dictionary generator!")
    FileName()


def ListCreation(min_length, max_length):
    max_value = 10 ** (max_length+1)
    min_value = 10 ** min_length
    for number in range(min_value, max_value):
        WriteFunction(str(number))


# def WriteFunction(writer):
#     print(writer)


def WriteFunction(ink):
    if debug_value:
        print(ink)
    with open(file_path, 'a') as Document:
        Document.write(ink + "\n")


def FileName():
    global file_name, file_path
    default_name = "pin_dictionary.txt"
    file_name = input(
        "[✶] Choose a mode to name your pin dictionary:"
        + "\n→ M for Manual"
        + f"\n→ D to Default to '{default_name}'\n"
        + "\n[✶] (Leave empty to automatically set to default)\n"
        + "\n[+] > Select Mode: "
    )
    if file_name == "" or file_name == " " or file_name == "D" or file_name == "d":
        file_name = default_name
        print("[!] Default Mode ✔")
        print(f"[✔] File name saved as '{file_name}'")
    elif file_name == "M" or file_name == "m":
        file_name = input("> Name :    ")
        file_name = file_name + '.txt'
        print(f"[✔] File name saved as '{file_name}'")
    else:
        file_name = default_name
        print("[!] Default Mode ✔")
        print(f"[✔] File name saved as '{file_name}'")
    print("")
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
    print("\n")


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
        f"[?] How long is the {chain_type} length for your target pin?"
        + f"\n[✶] (Leave empty or type D to default to {default_value})"
        + "\n[+] > Input : "
    )
    try:
        chain_input = int(chain_input)
        not_int = False
    except ValueError:
        not_int = True
    if chain_input in empty_void:
        print(f"[✔] Set {chain_type} pin length to default {default_value}")
    else:
        if not_int:
            print(f"[!] Input value not understood, setting {chain_type} chain length to default of {default_value}")
        else:
            default_value = chain_input
            print(f"[✔] Set {chain_type} pin length to {default_value}")
    return default_value


def ChainMaster():
    global starting_time
    print("[✶] Specify a length range for the pins :")
    min_chain = ChainFunc("min")
    print("")
    max_chain = ChainFunc("max")
    print("")
    print("[!] Warning: Long pins may take a lot of time to create!")
    print(f"[✔] Successfully modified pin lengths to start at {min_chain} and end at {max_chain}")
    print("\n")
    time.sleep(0.3)
    print(
        "[✔] Creating your pin dictionary... \n"
        + "[!] This may take a while"
    )
    starting_time = time.time()
    for available_length in range(min_chain-1, max_chain):
        ListCreation(available_length, available_length)
        WriteFunction('0'*(available_length+1))


def main():
    global minimum_chain_value, maximum_chain_value, debug_value
    minimum_chain_value = 3
    maximum_chain_value = 6
    debug_value = False
    Greeter()
    ending_time = time.time()
    creation_time = round((ending_time - starting_time)/60, 3)
    print(
        "[✔] Finished creating the pin dictionary successfully! "
        + f"It took {creation_time} minutes! "
        + "\n\n[✔] Here's where the file is saved: "
        + f"\n[File-Path]  {file_path}\n\n"
    )


if __name__ == '__main__':
    main()
