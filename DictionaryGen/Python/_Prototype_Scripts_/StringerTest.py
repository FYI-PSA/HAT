import Stringer_New

#Warning, Stringer.StringReduction is case sensitive!
USER = "John Smith"
A = f"C:/Users/{USER}/Documents/Game/SavedFiles/stats.sql"
B = f"C:/Users/{USER}/"

Result = Stringer.StringReduction(A,B)
#Returns "Documents/Game/SavedFiles/stats.sql"
print(Result)

Result = Stringer.StringReduction(Result,"/stats.sql")
#Returns "Documents/Game/SavedFiles"
print(Result)

Result = "~/"+Result
#Returns "~/Documents/Game/SavedFiles/stats.sql"
print(Result)

print(Stringer.StringReduction("Uhmm... Hello? Helllo Hello? Hello World? Hello! Hello World!", " Helllo"))