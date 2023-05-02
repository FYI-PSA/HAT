def DecimalToBinary(decimal: int, bits: int = 8) -> str:
    binary_data: str = ''
    for bit in range(0, bits):
        bit_power = bits - (bit + 1)
        power_of_two = 2 ** bit_power
        if (decimal >= power_of_two):
            binary_data += '1'
            decimal -= power_of_two
        else:
            binary_data += '0'
    return binary_data

def BinaryToDecimal(binary_data: str) -> int:
    bits = len(binary_data)
    decimal: int = 0
    for bit in range(0, bits):
        if ((binary_data[bit]) == '1'):
            bit_power = bits - (bit + 1)
            power_of_two = 2 ** bit_power
            decimal += power_of_two
    return decimal

