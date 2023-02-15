import b64_module
import base64

def read_normal_data(file_path:str) -> str:
    with open(file_path, 'r') as normal_file:
        data:str = normal_file.read()
    return data

def read_bytes_data(file_path:str) -> bytes:
    with open(file_path, 'rb') as bytes_file:
        data:bytes = bytes_file.read()
    return data

if __name__ == "__main__":
    file_data = read_bytes_data("__testing_assets__/image.jpg")
    file_ba = bytearray(file_data)
    with open("__testing_assets__/YCbCr.txt", 'w') as file:
        for data in file_ba:
            file.write(str(data))
            file.write(", ")