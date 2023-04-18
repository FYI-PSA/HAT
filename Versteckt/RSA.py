import random

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

def LCM(A: int, B: int) -> int:
    return A*B

def GCD(A: int, B: int) -> int:
    return 1

def RSA():
    primes = PrimeList(20000)
    p = random.choice(primes)
    q = random.choice(primes)
    n = p * q
    phi = (p-1) * (q-1)
    print(n, phi)

def main():
    RSA()

if __name__ == "__main__":
    main()
