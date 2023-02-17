# Yes after writing the binary module and the base64 module, I shamelessly copied sections from both and mashed them here

global hex_table

def hex_lookup_table() -> list[chr]:
    table = []
    for char_index in range(ord('0'),ord('9')+1) :
        table.append(chr(char_index))
    for char_index in range(ord('A'),ord('Z')+1) :
        table.append(chr(char_index))
    return table

hex_table: list[chr] = hex_lookup_table()

def decimal_to_hex(decimal_value:int, marking:bool = True, digits:int = 2, uppercase:bool = True) -> str:
    global hex_table
    hex_value:str = ''
    if marking:
        hex_value += '0x'
    for digit_index in range(0,digits):
        digit_power:int = digits - (1+digit_index)
        digit_value:int = 16 ** digit_power
        if (decimal_value >= digit_value) :
            how_much:int = int(decimal_value/digit_value)
            decimal_value -= digit_value
            hex_value += hex_table.index(how_much)
        else:
            hex_value += '0'
    if not uppercase:
        hex_value = hex_value.lower()
    return hex_value
    
def hex_to_decimal(hex_value:str) -> int:
    global hex_table
    # automatically deals with hex markings using the prefix-remove method of str objects
    # also automatically gets the size
    hex_value = hex_value.removeprefix('0x')
    hex_value = hex_value.upper()
    digits:int = len(hex_value)
    decimal_value:int = 0
    for digit_index in range(0,digits):
        digit_power:int = digits - (1+digit_index)
        hex_digit:chr = hex_value[digit_index]
        position_value:int = hex_table.index(hex_digit)
        digit_value = (16 ** digit_power) * position_value
        decimal_value += digit_value
    return decimal_value

if __name__ == "__main__":
    print(hex_to_decimal('0x45'))
