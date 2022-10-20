import os
import pathlib

def get_info():
    pass
    F_Year = input("Favorite Persian Year?")
    E_Year = input("Favorite English Year?")
    Online_Alias = input("Alias?")
    Name = input("Name?")
    Last_Name = input("Last Name?")
    
    

def mix_info(Year="",Alias="",Name="",LastName=""):
    pass

def file_name_func(Year="",Alias="",Name="",LastName=""):
    filename = input("File name for this to be saved as?")
    if filename == "":
        if Name == "":
            if LastName == "":
                if Alias == "":
                    filename = "not_important"
                else:
                    filename = Alias
            else:
                filename = LastName+Alias
        else:
            filename = Name+LastName+Alias
    else:
        pass
    
    return filename+'.txt'

def main():
    pass
    """
    write_info("Parsa\nparsa\nParsaParsa\nparsaparsa\nParsaparsa\nparsaParsa\nPARSAPARSA\n1386\n2007","","","Funtime_UwU")
    #"""
    get_info()

if __name__ == '__main__':
    main()
