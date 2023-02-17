from PythonModules.file_modifying_module import *
from PythonModules.hex_module import *
import tkinter as tk
import tkinter.filedialog as fd

global filedata

filedata = b''

def SelectFile():
    global filedata
    filename:str = fd.askopenfilename()
    if filename == '':
        return
    file_data_raw:bytes = read_bytes_data(filename)
    filedata = file_data_raw
    Data_Display(file_data_raw)

def Data_Display(file_data_raw):
    file_data_list:list[str] = [hex(data) for data in file_data_raw]
    data:str = ''
    end_index:int = len(file_data_list)
    space = 10
    # PLEASE CHANGE THIS A VALUE ONLY IF IT % WIDTH == 0 
    line_limit = hexes_width / space
    # ffs keep the font at 16px
    
    read_out_data: list[str] = []
    # read_out_data should be as many data that can fit on a line, then that amount of it translated into ascii
    # then that amount of it translated into a readable character

    for i in range(0, end_index):
        read_out_data.append(str(file_data_list[i]))
        read_out_data.append(str(hex_to_decimal(file_data_list[i])))
        read_out_data.append(str(chr(hex_to_decimal(file_data_list[i])).encode('utf-8')).removeprefix('b\'').removesuffix('\''))
    
    for i in range(0, end_index):
        mode:str = modes_button.cget('text')

        if mode == 'HEX':
            index:int = (int(i / 3) * 3)

        elif mode == 'ASCII':
            index:int = (int(i / 3) * 3) + 1

        else:
            index:int = (int(i / 3) * 3) + 2
        
        current_data:str = read_out_data[index]
        data += current_data
        
        if (i == (end_index - 1)):
            continue
        
        l = len(current_data)
        data += (space - (l % space)) * ' '


    hexes['state'] = 'normal'
    hexes.delete('1.0', tk.END)
    hexes.insert('0.0', data)
    hexes['state'] = 'disabled'


def Kill():
    root.destroy()

def ChangeMode():
    global filedata
    mode:str = modes_button['text']
    if mode == "HEX":
        modes_button.config(text="ASCII")
        Data_Display(filedata)
    elif mode == "ASCII":
        modes_button.config(text="CHAR")
        Data_Display(filedata)
    else:
        modes_button.config(text="HEX")
        Data_Display(filedata)

root: tk.Tk = tk.Tk()
root.resizable(False,False)
root.geometry('1420x710')

kill_b: tk.Button = tk.Button(root, text="Die", command=Kill)
kill_b.grid(row=0, column=0)

file_select: tk.Button = tk.Button(root, text="PICK FILE", command=SelectFile)
file_select.grid(row=0, column=1)

modes_button: tk.Button = tk.Button(root, text="HEX", command=ChangeMode)
modes_button.grid(row=1, column=2)

hexes_width = 100
hexes_height = 25
hexes: tk.Text = tk.Text(root, width=hexes_width, height=hexes_height, state='disabled', font=("Determination Mono", 16))
hexes.grid(row=1, column=1)

root.mainloop()



