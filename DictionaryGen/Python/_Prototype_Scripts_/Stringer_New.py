def StringReduction(string1="", string2=""):
    #print(string1, "\n", string2)
    list_first_string = list(string1)
    list_second_string = list(string2)
    len_first_string = len(list_first_string)
    len_second_string = len(list_second_string)
    reduction_dummy = "reduction_dummy"
    replace_list = list_first_string
    search_bool = False
    for i in range(len_first_string):
        i_letter = list_first_string[i]
        i_index=i
        if i_letter == list_second_string[0] and search_bool != True:
            j_list=[]
            for j in range(len_second_string):
                j_letter = list_second_string[j]
                j_list.append(j_letter)
                j_index=j
            k_index = i_index+j_index
            if j_list == list_second_string:
                for k in range(i_index+1,k_index+2):
                    replace_list[k] = reduction_dummy
                    search_bool = True
    return_list = [];
    for dummy in replace_list:
        if dummy != reduction_dummy:
            return_list.append(dummy)
    return_string = "".join(return_list)
    return return_string
