
import BASE2

def SplitChunks(data: str, chunk_size: int) -> list[str]:
    current_chunk_buffer: str = ''
    current_chunk_size: int = 0
    chunks_array: list[str] = []
    for character in data:
        if (((current_chunk_size % chunk_size) == 0) and (current_chunk_size != 0)):
            chunks_array.append(current_chunk_buffer)
            current_chunk_buffer = ''
            current_chunk_size = 0
        current_chunk_buffer += character
        current_chunk_size += 1
    if current_chunk_size > 0:
        chunks_array.append(current_chunk_buffer)
        current_chunk_buffer = ''
        current_chunk_size = 0
    return chunks_array

def B64_Table() -> list[str]:
    table: list[str] = []
    for uc_letter in range(ord('A'), ord('Z')+1):
        table.append(str(chr(uc_letter)))
    for lc_letter in range(ord('a'), ord('z')+1):
        table.append(str(chr(lc_letter)))
    for num in range(0, 10):
        table.append(str(num))
    table.append('+')
    table.append('/')
    return table

"""
    BASE 64 ENCRYPTION:

    Bytes Data -> 
    -> Ascii Array 
    -> 8Bit Binary Array 
    -> Joined Binary String 
    -> Fix Length To A Mutliple Of 6 By Adding  'A'  s to the end of the string
    -> Split Into 6Bit Binary Array
    -> For Each Array Item, Replace 'A's With '0' If The First Letter Of Item Is Not 'A' 
    -> Number Array, Count 'A' Blocks As A Variable
    -> Use B64 Table To Turn Numbers Into Character
    -> Join Together The Array, Add '=' For The Count Of 'A' Blocks
    
"""

def B64_Encrypt(original_data: bytes) -> str: 
    og_data_arr: list[bytes] = [item for item in original_data]
    og_data_ascii_arr: list[int] = [int(item) for item in og_data_arr]
    og_data_binary_arr: list[str] = [BASE2.DecimalToBinary(item) for item in og_data_ascii_arr]
    og_joined_binary_arr: str = ''.join(og_data_binary_arr)
    og_joined_binary_len: int = len(og_joined_binary_arr)
    len_remainder: int = og_joined_binary_len % 24
    extra_bits_needed: int = 0
    if (len_remainder != 0):
        extra_bits_needed = 24 - len_remainder
    extra_bits: str = 'A' * extra_bits_needed
    padded_binary_string: str = og_joined_binary_arr + extra_bits
    sixbit_binary_arr: list[str] = SplitChunks(padded_binary_string, 6)
    fixed_binary_arr: list[str] = []
    padding_counter: int = 0
    for item in sixbit_binary_arr:
        if (item[0] != 'A'):
            new_item: str = ''
            for bit in item:
                if (bit == 'A'):
                    new_item +='0'
                else:
                    new_item += bit
            fixed_binary_arr.append(new_item)
        else:
            padding_counter += 1
    b64_ascii_arr: list[int] = [BASE2.BinaryToDecimal(item) for item in fixed_binary_arr]
    b64_table: list[str] = B64_Table()
    b64_character_arr: list[str] = [b64_table[index] for index in b64_ascii_arr]
    b64_string = ''.join(b64_character_arr)
    b64_paddings = '=' * padding_counter
    b64_encrpyted_message = b64_string + b64_paddings
    return b64_encrpyted_message

"""

    BASE 64 DECRYPTION:

    B64 String ->
    -> Remove all padding
    -> Character Array
    -> Character Array Into Number Array Using Table
    -> 6 Bit Binary Array From Number Array
    -> Joined Binary String
    -> Split Into 8 Bit Binary List
    -> Ascii Array Out of 8Bit Array
    -> Bytes Array Out of Ascii Array
    -> Join together Bytes Data 

"""

def RemovePadding(base64_data: str) -> str:
    data_buffer = ''
    for character in base64_data:
        if (character != '='):
            data_buffer += character
        else:
            break
    return data_buffer

def B64_Decrypt(encrypted_data: str) -> bytes:
    enc_no_padding: str = RemovePadding(encrypted_data)
    enc_char_arr: list[str] = [character for character in enc_no_padding]
    b64_table: list[str] = B64_Table()
    enc_ascii_arr: list[int] = [b64_table.index(character) for character in enc_char_arr]
    enc_sixbit_bin_arr: list[str] = [BASE2.DecimalToBinary(number, 6) for number in enc_ascii_arr]
    joined_binary_string: str = ''.join(enc_sixbit_bin_arr)
    eightbit_bin_array: list[str] = SplitChunks(joined_binary_string, 8)
    # remove extra bits that appear from the process
    eightbit_bin_arr_fixed: list[str] = [item for item in eightbit_bin_array if (len(item) == 8)]
    og_ascii_arr: list[int] = [BASE2.BinaryToDecimal(item) for item in eightbit_bin_arr_fixed]
    og_data_arr: list[bytes] = [item.to_bytes(1, byteorder='big') for item in og_ascii_arr]
    og_data: bytes = b''.join(og_data_arr)
    return og_data

