# just for strings or bytes into ascii list and vise versa, otherwise use the builtin ord() and chr() functions
def item_to_ascii_list(item) -> list[int]:
    return [ord(character) for character in item]

def ascii_list_to_string(ascii_list:list[int]) -> str:
    return ''.join([chr(ascii_value) for ascii_value in ascii_list])