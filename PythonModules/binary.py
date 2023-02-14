def decimal_to_binary(decimal_value:int, bits_length:int = 8) -> str:
    binary_value:str=''
    for bit_index in range(0,bits_length):
        bit_power:int = bits_length - (1+bit_index)
        bit_value:int = 2**bit_power
        if (decimal_value >= bit_value):
            binary_value += '1'
            decimal_to_binary -= bit_value
        else:
            binary_value += '0'
    return binary_value

def binary_to_decimal(binary_value:str) -> int:
    decimal_value:int = 0
    bits_length:int = len(binary_value)
    for bit_index in range(0,bits_length):
        if (binary_value[bit_index] == '1'):
            bit_power:int = bits_length - (1+bit_index)
            bit_value:int = 2**bit_power
            decimal_value += bit_value
    return decimal_value

def level_bit_sizes(binary_value_1:str, binary_value_2:str) -> tuple[str, str]:
    bits_length_1:int = len(binary_value_1)
    bits_length_2:int = len(binary_value_2)
    if (bits_length_1 != bits_length_2):
        delta_length:int = abs(bits_length_1 - bits_length_2)
        if (bits_length_1 > bits_length_2):
            binary_value_2 = delta_length*'0' + binary_value_2
        else:
            binary_value_1 = delta_length*'0' + binary_value_1
    return (binary_value_1, binary_value_2)

def binary_xor(binary_value_1:str, binary_value_2:str) -> str:
    (binary_value_1, binary_value_2) = level_bit_sizes(binary_value_1, binary_value_2)    
    bits_length:int = len(binary_value_1)
    xor_binary:list[str] = list(bits_length*'0')
    for bit_index in range(0,bits_length):
        if (binary_value_1[bit_index] == '1'):
            if (binary_value_2[bit_index] == '1'):
                xor_binary[bit_index] = '0'
            else:
                xor_binary[bit_index] = '1'
        else:
            if (binary_value_2[bit_index] == '1'):
                xor_binary[bit_index] = '1'
    return ''.join(xor_binary)

def binary_and(binary_value_1:str, binary_value_2:str) -> str:
    (binary_value_1, binary_value_2) = level_bit_sizes(binary_value_1, binary_value_2)    
    bits_length:int = len(binary_value_1)
    and_binary:list[str] = list(bits_length*'0')
    for bit_index in range(0,bits_length):
        if (binary_value_1[bit_index] == '1' and binary_value_2[bit_index] == '1'):
            and_binary[bit_index] = '1'
    return ''.join(and_binary)

def binary_or(binary_value_1:str, binary_value_2:str) -> str:
    (binary_value_1, binary_value_2) = level_bit_sizes(binary_value_1, binary_value_2)
    bits_length:int = len(binary_value_1)
    or_binary:list[str] = list(bits_length*'0')
    for bit_index in range(0,bits_length):
        if (binary_value_1[bit_index] == '1' or binary_value_2[bit_index] == '1'):
            or_binary[bit_index] = '1'
    return ''.join(or_binary)