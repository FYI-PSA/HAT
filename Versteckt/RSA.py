import random
import time
import math
import json
import os

def PrimeList(range_max: int) -> list[int]:
    all_numbers = []
    next_list = []
    items_list = [] 
    for i in range(1, (range_max+1)):
        all_numbers.append(i)
        next_list.append(i)
        items_list.append(i)
    max_check = range_max ** (0.5)
    max_check = int(max_check)
    checked_numbers = [0, 1]
    next_prime = 0
    for checking_number in range(1, max_check+1):
        triggered = 0
        for number in items_list:
            if checked_numbers.count(number) < 1:
                next_prime = number
                triggered = 1
                break
        if triggered != 1:
            break
        checked_numbers.append(next_prime)
        prime_index = items_list.index(next_prime)
        items_count = len(items_list)
        for item_index in range((prime_index+1), items_count):
            item = items_list[item_index]
            if (item % next_prime) == 0:
                next_list.remove(item)
        items_list = []
        for item in next_list:
            items_list.append(item)
    primes = items_list
    primes.remove(1)
    return primes

def PublicKeys(phi: int) -> list[int]:
    # You can not add even numbers since e must be coprime with (p-1) * (q-1) which is ensured to be even
    # Saves on computation
    valid_e = []
    e_choices = []
    for number in range(2, phi+1):
        if (number & 1) == 1:
            e_choices.append(number)

    # It's gonna take forever and it's gonna be incredibley computation heavy, so just limit it to the first few thousand
    limit = 3333
    l_counter = 0

    for number in e_choices:
        if (l_counter > limit):
            break
        gcd = math.gcd(phi, number)
        if (gcd == 1):
            valid_e.append(number)
            l_counter += 1
    
    return valid_e

# Don't feel like calculating the cycles and then picking a random private key, just go with the first one :P
def PrivateKeys(phi: int, e: int) -> list[int]:
    valid_d = []

    """
        d   = ( k phi + 1 ) / e
        d e =   k phi + 1

        check all multiples of e, remove 1, check if they're devisible by phi
        if yes then that multiple of e is  ( d  *  e )
    """
    
    d = 0
    A = 0
    while 1:
        d += 1
        A += e
        
        B = A - 1
        if (B % phi) == 0:
            if ( e != d ) :
                break

    valid_d.append(d)
    return valid_d


def GenerateRSA() -> tuple[str, str]:

    print("[%] Calculating random factors...")
    prime_range = random.randint(50, 500)
    primes = PrimeList(prime_range)
    p = random.choice(primes)
    q = random.choice(primes)
    n = p * q
    phi = (p-1) * (q-1)
    print("[$] Random factos chosen!")
    
    print("")

    print("[%] Calculating key pair...")
    E = PublicKeys(phi)
    e = random.choice(E)
    D = PrivateKeys(phi, e)
    d = random.choice(D)
    print("[$] Done calculating key pair!")

    print("")

    PUBLIC_KEY = PublicKeyData(e, n)
    PRIVATE_KEY = PrivateKeyData(d, n)
    
    KEY_PAIR = (PUBLIC_KEY, PRIVATE_KEY)

    return KEY_PAIR

# Not strictly constrained to public and private factors, either one can unlock the other

def EncryptMessage(M: int, A: int, N: int) -> int:
    C = (M ** A) % N
    return C

def DecryptMessage(C: int, B: int, N: int) -> int:
    M = (C ** B) % N
    return M

# ---------------------------------------------------------------------------

def PublicKeyData(e : int, n : int) -> str:
    information: dict = {"Encryption Key": e, "Product": n}
    data: str = json.dumps(information)
    return data

def PrivateKeyData(d : int , n : int) -> str:
    information: dict = {"Decryption Key": d, "Product": n}
    data: str = json.dumps(information)
    return data

def SaveKeys(key_pair_data: tuple[str, str], home_path : str = "./") -> None:

    print("[%] Saving keys...")

    key_folder = '.__Keys__/'
    key_path = home_path + key_folder
    os.makedirs(key_path, exist_ok=True)

    public_key_file = key_path + 'Public-Key.txt'
    private_key_file = key_path + '.private_key'
    
    public_key_data = key_pair_data[0]
    private_key_data = key_pair_data[1]

    with open(public_key_file, 'w') as public_file:
        public_file.write(public_key_data)
    
    with open(private_key_file, 'w') as private_file:
        private_file.write(private_key_data)

    print("[$] Key pair saved at " + key_path)
    print("")
    print("[@] Here's your public key:")
    print(public_key_data)
    print("")
    return 

def ReadPrivateKey(private_file_path: str = "./.__Keys__/.private_key") -> dict:
    
    print("[%] Reading private key...")
    print("")

    try:
        with open(private_file_path, 'r') as private_file:
            data = private_file.read()
        
        information: dict = json.loads(data)

        print("[$] Got private key!")
    
    except FileNotFoundError:
        
        print("[#] Private key file not found!")
        exit(-1)


    return information

def ProcessEncryptedInput(data: str) -> list[int]:
    data = data.removeprefix('START - ')
    data = data.removesuffix(' - END')
    items: list[str] = data.split(' - ')
    integers: list[int] = [int(item) for item in items]
    return integers

def DecryptData(data: str, D: int, N: int) -> str:
    data_encrypted_arr: list[int] = ProcessEncryptedInput(data)
    data_ascii_arr: list[int] = [DecryptMessage(C=encrypted, B=D, N=N) for encrypted in data_encrypted_arr]
    character_arr = [chr(item) for item in data_ascii_arr] 
    original_string = ''.join(character_arr)
    return original_string

def EncryptData(data: str, E: int, N: int) -> str:
    data_ascii_arr: list[int] = [ord(character) for character in data]
    data_encrypted_arr: list[int] = [EncryptMessage(M=number, A=E, N=N) for number in data_ascii_arr]
    encrypted_string = "START - "
    for item in data_encrypted_arr:
        encrypted_string += str(item) + ' - '
    encrypted_string += 'END'
    return encrypted_string


def Goodbye() -> None:
    print("")
    print("[@] Goodbye!")
    exit(0)

def main() -> None:
    print("[*] RSA Demo")
    print("")
    print("[!] Press  G  to generate a new RSA keypair")
    print("[!] Press  E  to encrypt text with a pre existing RSA keypair")
    print("[!] Press  D  to decrypt text with a pre existing RSA keypair")
    choice = input("[?] >  ")
    print("")
    if choice == 'G':
        KeyPair = GenerateRSA()
        SaveKeys(KeyPair)
        Goodbye()

    elif choice == 'E':
        print("[!] Press  S  to sign text with your private key")
        print("[!] Press  E  to encrypt text with someone else's public key")
        key_choice = input("[?] >  ")
        print("")
        
        if key_choice == 'S':
            PRIVATE_KEY = ReadPrivateKey()
            a = PRIVATE_KEY['Decryption Key']
            n = PRIVATE_KEY['Product']
        else:
            PUBLIC_KEY = json.loads(input('[@] Enter the content of the "Public-Key.json" file : '))
            a = PUBLIC_KEY['Encryption Key']
            n = PUBLIC_KEY['Product']

        # TODO : TURN INTO BASE64
        data = input("[?] Text to encrypt: ")
        encrypted = EncryptData(data, a, n)
        print("")
        print("[$] Operation successful! (Copy the folllowing line)")
        print("")
        print(encrypted)
        
        Goodbye()

    elif choice == 'D':
        print("[!] Press  P  to decrypt a message signed with your pubic key")
        print("[!] Press  S  to decrypt a message signed by someone's private key as a signiture")
        key_choice = input("[?] >  ")
        print("")

        if key_choice == 'P':
            PRIVATE_KEY = ReadPrivateKey()
            b = PRIVATE_KEY['Decryption Key']
            n = PRIVATE_KEY['Product']
        else:
            PUBLIC_KEY = json.loads(input('[@] Enter the content of the "Public-Key.json" file : '))
            b = PUBLIC_KEY['Encryption Key']
            n = PUBLIC_KEY['Product']

        # TODO : CONVERT FROM BASE64 TO NUMBERS
        data = input("[?] Encrpyted data to decrypt: ")
        decrypted = DecryptData(data, b, n)
        print("")
        print("[$] Operation successful! Data:")
        print("")
        print(decrypted)

        Goodbye()


    return

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\n")
        print("[!] Existing Program")
        Goodbye()
