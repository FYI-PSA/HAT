import os.path as psycho
from pathlib import Path
#path_owo=psycho.join("./Team Fortress/","sextf2.txt")
#print(type(path_owo),path_owo)
#path_owo="./Team Fortress 2/UwU/sextf2.txt"

OWO=Path("./Dicks/sex.txt")
OWO.parent.mkdir(exist_ok=True,parents=True)

path_owo = str(OWO)

with open(path_owo,'w') as sex:
    sex.write("OwO")
