global global_word_list
global global_generated_usernames


def main():
    print(Social_Bust(["Funtime", "UwU", "_", "twt"]))


def Social_Bust(word_list, minimum_length="undefined_", maximum_length="undefined"):
    default_min = len(word_list)-2
    default_max = len(word_list)+2
    if minimum_length == "undefined_" or type(minimum_length) == str:
        if maximum_length == "undefined_" or type(maximum_length) == str:
            return_value = DictionaryGen(word_list, default_min, default_max)
        else:
            return_value = DictionaryGen(word_list, default_min, maximum_length)
    else:
        if maximum_length == "undefined_" or type(maximum_length) == str:
            return_value = DictionaryGen(word_list, minimum_length, default_max)
        else:
            return_value = DictionaryGen(word_list, minimum_length, maximum_length)
    return return_value


def DictionaryGen(word_list, minimum_chain=0, maximum_chain=3):
    global global_word_list
    global global_generated_usernames
    starting_base = ""
    global_word_list = word_list
    global_generated_usernames = []
    for length_value in range(minimum_chain, maximum_chain+1):
        GenerateDictionary(starting_base, length_value)
    return global_generated_usernames


def GenerateDictionary(user_tag, current_length):
    global global_word_list
    global global_generated_usernames
    done_flag = False
    for word in global_word_list:
        new_word = user_tag + word
        if not done_flag:
            current_length = current_length - 1
            done_flag = True
        if current_length > 0:
            GenerateDictionary(new_word, current_length)
        else:
            global_generated_usernames.append(new_word)


if __name__ == "__main__":
    main()
