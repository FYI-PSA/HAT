import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class RSA_BIG 
{

    /* -------------------------------------------------------------------------------------- */
    /* MAIN---------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        GENERAL.print("[*] RSA\n\n");
        GENERAL.print("[@] Enter 'K' to generate a new keypair\n");
        GENERAL.print("[@] Enter 'C' to change a message with a keypair\n");
        GENERAL.print("\n[?] > ");
        char choice = GENERAL.getInput(input).charAt(0);
        GENERAL.print("\n\n");
        if (choice == 'K')
        {
            GENERAL.print("Generating keypair...\n");

            pair<pair<Integer, Integer>, pair<Integer, Integer>> keyPair;
            keyPair = generateKeyPair();
    
            GENERAL.print("Public Key: \n");
            GENERAL.print(keyPair.first);
            GENERAL.print("\n\n");        
            GENERAL.print("Private Key: \n");
            GENERAL.print(keyPair.second);
            GENERAL.print("\n\n");
    
            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        else if (choice == 'C')
        {
            GENERAL.print("[@] Enter number\n");
            GENERAL.print("[?] > ");
            Integer number = Integer.parseInt(GENERAL.getInput(input));
            GENERAL.print("[@] Enter a key in the same order as given to you by this program, seperated by a comma\n");
            GENERAL.print("[?] > ");

            String[] keyPair = GENERAL.getInput(input).split(",");
            Integer X = Integer.parseInt(keyPair[0].strip());
            Integer M = Integer.parseInt(keyPair[1].strip());

            Integer newNumber = changeMessage(number, X, M);
            GENERAL.print("[$] New number:\n\n");
            GENERAL.print(newNumber);

            GENERAL.print("\n\n[!] Goodbye!\n\n");
        }
        input.close();
        return;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN-RSA-METHODS---------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */
    
    public static Integer changeMessage(Integer message, Integer power, Integer mod)
    {
        Integer newMessage = GENERAL.SlowModularExponentiation(message, power, mod);
        return newMessage;
    }

    public static pair<pair<Integer, Integer>, pair<Integer, Integer>> generateKeyPair()
    {
        pair<pair<Integer, Integer>, pair<Integer, Integer>> keyPair = new pair<pair<Integer, Integer>, pair<Integer, Integer>>(){};

        Integer ceil = 333;
        Integer[] primes = generatePrimeList(ceil);
        Integer[] primesArray = GENERAL.strongValues(primes);

        SecureRandom pqRandom = new SecureRandom();

        int p = GENERAL.secureChooseInteger(primesArray, pqRandom);
        int q = GENERAL.secureChooseInteger(primesArray, pqRandom);
        while (q == p)
        {
            q = GENERAL.secureChooseInteger(primesArray, pqRandom);
        }
        int n = p*q;
        int phiN = (p-1) * (q-1);

        Integer[] validEArray = validEList(phiN);
        Integer[] wantedEncryptionValues = GENERAL.strongValues(validEArray);

        SecureRandom expoRandom = new SecureRandom();

        int e = GENERAL.secureChooseInteger(wantedEncryptionValues, expoRandom);

        // int d = slowCalculateD(phiN, e);
        int d = calculateD(phiN, e);

        pair<Integer, Integer> publicKey = new pair<Integer, Integer>  (e , n);
        pair<Integer, Integer> privateKey = new pair<Integer, Integer> (d , n);

        keyPair = new pair<pair<Integer,Integer>, pair<Integer,Integer>>(publicKey, privateKey);

        return keyPair;
    }

    /* -------------------------------------------------------------------------------------- */
    /* CALCULATION-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */


    private static Integer calculateD(Integer phiN, Integer E)
    {
        return GENERAL.ModularMultiplicativeInverse(E, phiN);
    }
    private static Integer[] validEList(Integer phiN)
    {
        Integer[] unfilledEArray = new Integer[phiN];
        int index = 0;
        // it's a valid E if  (1 < x < phiN)  and  (GCD(phiN, E) == 1) 
        // all even numbers are out because phiN = (q-1)(p-1) both p or q are primes and one of them - 1 MUST become even
        for (Integer number = 2; number < phiN; number ++)
        {
            if ((number & 1) == 1)
            {
                if ( (GENERAL.GreatestCommonDevisor(number, phiN)) == 1 )
                {
                    unfilledEArray[index] = number;
                    index++;
                }
            }
        }
        int length = index; // because at the end, it adds one more when it's not needed, so now it's the length of the array, not the last index
        Integer[] validEsArray = new Integer[length];
        for (int properIndex = 0; properIndex < length; properIndex ++)
        {
            validEsArray[properIndex] = unfilledEArray[properIndex];
        }
        return validEsArray;
    }
    private static Integer[] generatePrimeList(Integer ceil) 
    {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (Integer number = 2; number <= ceil; ++number)
        {
            numbers.add(number);
        }        
        ArrayList<Integer> checks = new ArrayList<Integer>(numbers);
        ArrayList<Integer> flaggedPrimes = new ArrayList<Integer>();
        for (int loop = 0; loop <= ceil; loop++)
        {
            ArrayList<Integer> copycat = new ArrayList<Integer>(checks);
            int currentPrime = 1;
            boolean notEnded = true;
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
            checks = new ArrayList<Integer>(copycat);
        }
        int length = flaggedPrimes.size();
        Integer[] primes = new Integer[length];
        for (int index = 0; index < length; index++)
        {
            primes[index] = flaggedPrimes.get(index);
        }
        return primes;
    }

}
