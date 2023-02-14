import ascii

def read_utf8_data(file_path:str) -> str:
    with open(file_path, 'r') as utf8_file:
        data:str = utf8_file.read()
    return data

def read_bytes_data(file_path:str) -> bytes:
    with open(file_path, 'rb') as bytes_file:
        data:bytes = bytes_file.read()
    return data

if __name__ == "__main__":
    print(read_bytes_data("__testing_assets__/image.jpg"))