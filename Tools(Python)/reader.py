from PythonModules.file_modifying_module import *
from PythonModules.hex_module import *
import tkinter as tk
import tkinter.filedialog as fd
import time

global file_name

file_name:str = ''

def SelectFile():
    global file_name
    filename:str = fd.askopenfilename()
    if filename == '':
        return
    file_name = filename
    Data_Display(filename)

def Data_Display(file_name):
    print(file_name)
    space = 10
    # PLEASE CHANGE THIS A VALUE ONLY IF ITS MOD BY <space> IS 0
    line_limit = hexes_width / space
    
    hexes['state'] = 'normal'
    hexes.delete('0.0', tk.END)    
    mode:str = modes_button.cget('text')
    i:int = 0
    with open(file_name, 'rb') as file:
        reading_from_file = True
        while reading_from_file:
            for data in file.readline(i):
                if data == b'':
                    reading_from_file=False
                    i+=1
                    root.update()
                    break
                item = hex(data)
                if mode == 'HEX':
                    current_data:str = item
                elif mode == 'ASCII':
                    current_data:str = str(hex_to_decimal(item))
                else:
                    current_data:str = str( str((str(chr(hex_to_decimal(item)))).encode('utf-8')).removeprefix('b\'').removesuffix('\'') )
                l = len(current_data)
                current_data += (space - (l % space)) * ' '            
                hexes.insert(tk.END, current_data)
                root.update()
            i+=1
        hexes['state'] = 'disabled'


def Kill():
    root.destroy()

def ChangeMode():
    global file_name
    mode:str = modes_button['text']
    if mode == "HEX":
        modes_button.config(text="ASCII")
        Data_Display(file_name)
    elif mode == "ASCII":
        modes_button.config(text="CHAR")
        Data_Display(file_name)
    else:
        modes_button.config(text="HEX")
        Data_Display(file_name)

root: tk.Tk = tk.Tk()
root.resizable(False,False)
window_size = (root.winfo_screenwidth(),root.winfo_screenheight())
root.geometry('%dx%d' %window_size)
root.title('explorer')
root.attributes('-fullscreen', True)
root.state('zoomed')

kill_b: tk.Button = tk.Button(root, text="Die", command=Kill)
kill_b.grid(row=0, column=0)

file_select: tk.Button = tk.Button(root, text="PICK FILE", command=SelectFile)
file_select.grid(row=2, column=0)

modes_button: tk.Button = tk.Button(root, text="HEX", command=ChangeMode)
modes_button.grid(row=1, column=0)

hexes_width = 150
hexes_height = 25
hexes: tk.Text = tk.Text(root, width=hexes_width, height=hexes_height, state='disabled', font=("Fira Code", 12))
hexes.grid(row=1, column=1)
# The font is Fira Code @  https://fonts.google.com/specimen/Fira+Code?category=Monospace&subset=latin
root.mainloop()



