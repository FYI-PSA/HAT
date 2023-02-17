from PythonModules.file_modifying_module import *
from PythonModules.color_conversion import *

image_bytes:bytes = read_bytes_data("Assets/jpg_test.jpg")
print(image_bytes)

# some metadata like how the file starts and ends are also included, so not particularly the amount of pixels.
info_byte = 0
for ycc_int in image_bytes:
    info_byte += 1
print(info_byte)

first_values: list[int] = [image_bytes[i] for i in range(0, info_byte)]
first_bytes: list[bytes] = [integer.to_bytes(1,'little') for integer in first_values]
pixel_value: bytes = b''.join(first_bytes)
print(first_values)
print(first_bytes)
print(pixel_value)

# write jpg bytes to new image file
write_bytes_data("Assets/jpg_output.jpg", pixel_value)


