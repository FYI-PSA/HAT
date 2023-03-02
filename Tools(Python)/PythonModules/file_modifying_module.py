def read_normal_data(file_path:str) -> str:
    with open(file_path, 'r', encoding='utf-8') as normal_file:
        data:str = normal_file.read()
    return data

def read_bytes_data(file_path:str) -> bytes:
    with open(file_path, 'rb', encoding='utf-8') as bytes_file:
        data:bytes = bytes_file.read()
    return data

def write_normal_data(file_path:str, data:str) -> None:
    with open(file_path, 'w', encoding='utf-8') as normal_file:
        normal_file.write(data)

def write_bytes_data(file_path:str, data:bytes) -> None:
    with open(file_path, 'wb', encoding='utf-8') as bytes_file:
        bytes_file.write(data)
