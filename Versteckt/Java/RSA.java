import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.RandomAccess;


public class RSA 
{
    private static String getInput(Scanner input)
    {
        String data = input.nextLine();
        return data;
    }
    private static void print(String data)
    {
        System.out.print(data);
        System.out.flush();
        return;
    }
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        int floor = Integer.parseInt(String.valueOf(getInput(input)));
        int ceil = Integer.parseInt(String.valueOf(getInput(input)));
        ArrayList<Integer> primes = generatePrimeList(floor, ceil);
        pair randomPrimes = choosePair(primes);
        int p = randomPrimes.first;
        int q = randomPrimes.second;
        int n = p*q;
        int phi = (p-1) * (q-1);

        for (Integer item : primes)
        {
            print(String.valueOf(item) + "\n");
        }
    }
    private static pair choosePair(ArrayList<Integer> integerList)
    {
        int listSize = integerList.size();
        pair items = new pair();
        pair itemsIndex = new pair();
        Random seed = new Random();
        itemsIndex.first = seed.nextInt(listSize);
        itemsIndex.second = seed.nextInt(listSize);
        items.first = integerList.get(itemsIndex.first);
        items.second = integerList.get(itemsIndex.second);
        return items;
    }
    private static ArrayList<Integer> generatePrimeList(int floor, int ceil) 
    { /* Includes both ends (if possible) */
        ArrayList<Integer> numbers = new ArrayList<>(){};
        for (int number = 2; number <= ceil; ++ number)
        {
            numbers.add(number);
        }        
        ArrayList<Integer> checks = new ArrayList<>(numbers);
        ArrayList<Integer> flaggedPrimes = new ArrayList<>(){};
        for (int loop = 0; loop <= ceil; loop++)
        {
            ArrayList<Integer> copycat = new ArrayList<>(checks);
            int currentPrime = 1;
            Boolean notEnded = true;
            for (Integer nextPrime : checks)
            {
                if (! (flaggedPrimes.contains(nextPrime)))
                {
                    flaggedPrimes.add(nextPrime);
                    currentPrime = nextPrime;
                    notEnded = false;
                    break;
                }
            }
            if (notEnded)
            {
                break;
            }
            for (Integer currentChecking : checks)
            {
                if (currentChecking == currentPrime)
                {
                    continue;
                }
                if ((currentChecking % currentPrime) == 0)
                {
                    copycat.remove(currentChecking);
                }
            }
            checks = new ArrayList<>(copycat);
        }
        ArrayList<Integer> primes = new ArrayList<>(flaggedPrimes);
        for (Integer prime : flaggedPrimes)
        {
            if (prime < floor)
            {
                primes.remove(prime);
            }
        }
        return primes;
    }
}
