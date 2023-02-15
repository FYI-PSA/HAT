def string_to_chunks(string_value:str, chunk_size:int) -> list[str]:
    chunks:list[str] = []
    chunk_buffer:str = ''
    string_length:int = len(string_value)
    for char_index in range(0,string_length):
        chunk_buffer += string_value[char_index]
        if ((char_index+1) % chunk_size == 0):
            chunks.append(chunk_buffer)
            chunk_buffer = ''
    if (chunk_buffer != ''):
        chunk_buffer += (chunk_size - len(chunk_buffer)) * ' '
        chunks.append(chunk_buffer)
        chunk_buffer = ''
    return chunks