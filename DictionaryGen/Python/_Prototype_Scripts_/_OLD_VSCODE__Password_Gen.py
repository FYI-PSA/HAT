def dict_input():
    global dict_list
    dict_list = []
    dict_list.append(input("[+] Add entry to the list:    "))
    print("[+] Added ✔")
    print("[!] Enter 'Dict_Stop' or leave the field empty and hit enter to finilize the list!")
    while (True):
        X = input("[+] Add another entry to the list:    ")
        if X == "Dict Stop" or X == "Dict_Stop" or X == "dict_stop" or X == "dict stop" or X == "":
            print("[✶] Finilized list entries!")
            print("[✶] Analizing and creating dictionary...")
            break
        else:
            dict_list.append(X)
            print("[+] Added ✔")
    print("[!] Please don't open the dictionary file while the program is still running!")
    file_name()

def file_name():
    global dict_list, fn
    print("[!] Warning: if dictionary with the same file name exists, it will added to the file and may break something!")
    fn = input("[✶] Dictionary file name mode:\n→ A for Automatic | M for Manual\n→ Leave blank or type D to default 'dictionary.txt'\n> Select Mode:   ")
    default = "dictionary.txt"
    if fn == "" or fn == "D" or fn == "d":
        fn = default
        print("[!] Default Mode:")
        print(f"[✔] File name saved as '{fn}'")
    elif fn == "A" or fn == "a":
        if len(dict_list) > 1:
            fn = dict_list[0]+dict_list[1]+'.txt'
            print(f"[✔] File name saved as '{fn}'")
        elif len(dict_list) == 1:
            fn = dict_list[0]+'.txt'
            print(f"[✔] File name saved as '{fn}'")
        else:
            fn = default
            print(f"[✔] File name saved as '{fn}'")
    elif fn == "M" or fn == "m":
        fn = input("> Name :    ")
        fn = fn + '.txt'
        print(f"[✔] File name saved as '{fn}'")


    """ DEBUG ONLY """
    print("[-] Deleting previous file with same name...")
    delete_previous_file_with_same_name(fn)
    print("[!] Successfuly deleted previous file with same name!")
    """ END OF DEBUG , DO NOT ENABLE UNLESS YOU WANT YOUR FILE TO BE OVERWRITTEN WITH EMPTY! """


    all_lenghts(4)

def all_lenghts(max_len):
    global percent_part, percent, dict_list, fn, li
    percent_part = 100 / sum_of_previous_including_itself(max_len)
    percent = percent_part
    single_add()
    li=0
    if max_len > 1:
        for all_len in range(2,max_len):
            print(all_len)
            X = ""
            recursive_add(all_len, X, li)
def recursive_add(length, X, li):
    global percent_part, percent, dict_list, fn
    li=li+1
    print(f"percent_part:    {percent}  |  list iterator:    {li}  |  length:    {length}  |  X:    {X}  |  percent:    {percent_part}  |  dict_list:    {dict_list}  |  fn:    {fn}\n")

    if length > 1:
        for entry in dict_list:
            X = X + entry
            length = length - 1
            #print(f"DEBUG :     length = {length} | X = {X}")
            recursive_add(length, X, li)
    else:
        file = open(fn, 'a')
        file.write(X+"\n")
        file.close()
        percent = percent + percent_part
        print(f"[✔] {percent}% Completed")
        return

def prototype_starter():
    x=[]
    L=[1,3]
    try:
        L=[str(i) for i in L]
    except:
        pass
    listlen = len(L)
    comlen = int(input("> "))
    t = listlen ** comlen
    for i in range(t):
        x.append("")
        #print(i)
    t = int(t / listlen)
    for e in L:
        for i in range(1,t+1):
            magic_number = ((L.index(e) * t) + i) - 1
            x[magic_number] = x[magic_number] + e
    #print(f"{L}\n{x}")
    prototyper_ender(x,L,listlen,t,t,comlen)

def prototyper_ender(x,L,listlen,multi,t,comlen):
    #print(t,listlen,x,L,multi)
    starwalker = listlen ** comlen
    #print(f"im the original             starwalker: {starwalker}")
    #print("MN = ((L.index(e) * t) + i) - 1 + (M * multi)")
    if t == 1:
        #print(t,listlen,x,L)
        delete_previous_file_with_same_name('dictionary.txt')
        with open('dictionary.txt','w') as file:
            for i in x:
                file.write(i+"\n")
        return
    t = int(t / listlen)
    #print(t)
    for e in L:
        for M in range(multi):
            for i in range(1,t+1):
                magic_number = ((L.index(e) * t) + i) - 1
                magic_number = magic_number + M*multi
                #print(f"{magic_number} = (({L.index(e)} * {t}) + {i}) - 1 + ({M} * {multi})")
                #print(f"M : {M}  |  multi : {multi}  |  e : {e}")
                if magic_number > starwalker-1:
                    print("BRAEKING NEWS! : magic_number : " + str(magic_number))
                    break
                try:
                    x[magic_number] = x[magic_number] + e
                except:
                    #print('ERR 1001 : item in specified list index not found')
                    #print(x)
                    pass
                print(x)
    prototyper_ender(x, L, listlen,t,t,comlen)
def recursive_add():
    pass


def single_add():
    global percent_part, percent, dict_list, fn
    file = open(fn, 'a')
    file.write('\n')
    for entry in dict_list:
        file.write(entry+"\n")
    file.close()
    print(f"[✔] {percent}% Completed")
#"""

#change all to work with a LIST system

def sum_of_previous_including_itself(number):
    sum_of = 0
    for previous in range(1,number+1):
        sum_of = sum_of + previous
    return sum_of

def delete_previous_file_with_same_name(fn):
    with open(fn,'w') as file:
        file.write("")

def main():
    #prototype_starter()
    dict_input()

if __name__ == '__main__':
    main()

