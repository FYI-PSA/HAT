import SocialBuster
import tkinter as tk
import random


def ScreenSize():
    screen_window = tk.Tk()
    screen_window.update_idletasks()
    screen_window.attributes('-fullscreen', True)
    screen_window.state('iconic')
    screen_width = screen_window.winfo_screenwidth()
    screen_height = screen_window.winfo_screenheight()
    screen_window.destroy()
    return screen_width, screen_height


# Give it a set size, adjustable for later on.

# window_width, window_height = ScreenSize()
# window_width, window_height = int(window_width * 0.7), int(window_height * 0.7)

window_width = 1111
window_height = 555

edge_offset = [11.1, 22.2]

root_window = tk.Tk()
window_names = [
    "Security Breach",
    "Secret Berries",
    "Six Bits",
    "Sick Beats",
    "Sound Board",
    "Sinister Bread",
    "Sisters Bakery",
    "Seriously Bad",
    "Sexy Bread",
    "Seven Boxes"
]
root_window.title(random.choice(window_names))
root_window.geometry(str(window_width) + 'x' + str(window_height))

name_image = tk.PhotoImage(file="Images/Logo-V3.png")
name_label = tk.Label(root_window, text="Social ✨ Buster", image=name_image)
name_pos = [edge_offset[0]*3, 0]  # The image has a bit of empty space at the top, no need for Y offset
name_label.place(x=name_pos[0], y=name_pos[1])

words_label_pos = [name_pos[0] + edge_offset[0], name_pos[1] + name_image.height() + edge_offset[1]]
words_label_font = ('Century Regular', 14)
words_label = tk.Label(root_window, text="List of probable strings (Separate with space):", font=words_label_font)
words_label.place(x=words_label_pos[0], y=words_label_pos[1])

words_length = 6 * words_label_font[1]
words_box_pos = [name_pos[0] + edge_offset[0], words_label_pos[1] + words_label_font[1]*2]
words_box = tk.Entry(root_window, width=words_length)
words_box.place(x=words_box_pos[0], y=words_box_pos[1])


def TriggerInterface():
    user_input = words_box.get()
    word_list = user_input.split(" ")
    maximum_length = int(len(word_list))
    minimum_length = int(maximum_length/2)
    print(SocialBuster.Social_Bust(word_list, minimum_length, maximum_length))


generate_pos = [edge_offset[0] + words_length + words_box_pos[0], words_box_pos[1] + 2*words_label_font[1]]
generate_text = "Look for valid usernames in the target platform!"
generate_button = tk.Button(text=generate_text, bg="#ff0586", command=TriggerInterface)
generate_button.place(x=generate_pos[0], y=generate_pos[1])

root_window.mainloop()
