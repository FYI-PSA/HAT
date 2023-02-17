try:
    import ascii_module
except ImportError:
    try:
        import PythonModules.ascii_module
    except ImportError:
        pass
try:
    import binary_module
except ImportError:
    try:
        import PythonModules.binary_module
    except ImportError:
        pass
try:
    import chunks_module
except ImportError:
    try:
        import PythonModules.chunks_module
    except ImportError:
        pass

global b64_table

def b64_lookup_table() -> list[chr]:
    table = []
    for char_index in range(ord('A'),ord('Z')+1) :
        table.append(chr(char_index))
    for char_index in range(ord('a'),ord('z')+1) :
        table.append(chr(char_index))
    for char_index in range(ord('0'),ord('9')+1) :
        table.append(chr(char_index))
    table.append('+')
    table.append('/')
    return table

b64_table = b64_lookup_table()

def item_to_b64(item: str|int) -> str:
    global b64_table
    
    unencoded_string: str = str(item)
    
    # print(unencoded_string)
    
    unencoded_ascii_list: list[int] = ascii_module.item_to_ascii_list(unencoded_string)
    
    # print(unencoded_ascii_list)
    
    unencoded_binary_list: list[str] = [binary_module.decimal_to_binary(ascii_item, 8) for ascii_item in unencoded_ascii_list]
    
    # print(unencoded_binary_list)
    
    unencoded_binary: str = ''.join(unencoded_binary_list)
    bits: int = len(unencoded_binary)
    if (bits % 24) != 0:
        unencoded_binary += (24 - (bits % 24)) * '2'
    
    # print(unencoded_binary)
    
    b64_binary_list: list[str] = chunks_module.string_to_chunks(unencoded_binary, 6)
    
    # print(b64_binary_list)
    
    encoded_lookup_list: list[int] = []
    for binary_ascii_item in b64_binary_list:
        if (binary_ascii_item == '222222'):
            encoded_lookup_list.append(64)
        else:
            encoded_lookup_list.append(binary_module.binary_to_decimal(binary_ascii_item, 6))
    
    # print(encoded_lookup_list)
    
    encoded_character_list: list[chr] = []
    for encoded_item_index in encoded_lookup_list:
        if (encoded_item_index == 64):
            encoded_character_list.append('=')
        else:
            encoded_character_list.append(b64_table[encoded_item_index])
    
    # print(encoded_character_list)
    
    encoded_string: str = ''.join(encoded_character_list)
    
    # print(encoded_string)
    
    return encoded_string

def b64_to_item(encoded_string: str) -> int|str:
    global b64_table
    
    encoded_list: list[chr] = list(encoded_string)
    
    # print(encoded_list)
    
    unencoded_ascii_list: list[int] = []
    for encoded_char in encoded_list:
        if (encoded_char == '='):
            unencoded_ascii_list.append(64)
        else:
            unencoded_ascii_list.append(b64_table.index(encoded_char))
    
    # print(unencoded_ascii_list)
    
    binary_unencoded_list: list[str] = []
    for unencoded_ascii in unencoded_ascii_list:
        if (unencoded_ascii == 64):
            binary_unencoded_list.append('222222')
            pass
        else:
            binary_unencoded_list.append(binary_module.decimal_to_binary(unencoded_ascii, 6))
    
    # print(binary_unencoded_list)
    
    joined_binary: str = ''.join(binary_unencoded_list)

    # print(joined_binary)

    byte_binary: list[str] = chunks_module.string_to_chunks(joined_binary, 8)

    # print(byte_binary)

    used_bytes: list[str] = []
    for byte_section in byte_binary:
        useless_byte_flag: bool = False
        for bit_index in range(0,8):
            if byte_section[bit_index] == '2':
                useless_byte_flag = True
                break
        if not useless_byte_flag:
            used_bytes.append(byte_section)

    # print(used_bytes)
    
    ascii_list: list[int] = [binary_module.binary_to_decimal(byte_item, 8) for byte_item in used_bytes]

    # print(ascii_list)

    unencoded_string: list[chr] = ascii_module.ascii_list_to_string(ascii_list)
    
    # print(unencoded_string)
    
    return unencoded_string

if __name__ == "__main__":
    import base64
    loop_switch: bool = True
    while loop_switch:
        entry: str = input("Enter something or nothing: ")
        if entry == '':
            loop_switch = False
            continue
        print("Encoded to B64 using this:")
        
        b64_encoded = item_to_b64(entry)
        print(b64_encoded.encode('utf-8'))
        
        print("\nDecoded from B64 using this:")
        
        b64_decoded = b64_to_item(b64_encoded)
        print(b64_decoded.encode('utf-8'))
        
        print("\nPython builtin:")
        
        print(base64.b64encode(entry.encode('utf-8')))
        print(base64.b64decode(b64_encoded))
        
        print("\n")