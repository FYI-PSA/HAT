import java.security.SecureRandom;
import java.util.ArrayList;

public class RSA 
{

    /* -------------------------------------------------------------------------------------- */
    /* MAIN---------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    public static void main(String[] args) 
    {
        pair<pair<Integer, Integer>, pair<Integer, Integer>> keyPair;
        keyPair = generateKeyPair();

        GENERAL.print("Public Key: \n");
        GENERAL.print(keyPair.first);
        GENERAL.print("\n\n");        
        GENERAL.print("Private Key: \n");
        GENERAL.print(keyPair.second);
        GENERAL.print("\n\n");

        return;
    }

    /* -------------------------------------------------------------------------------------- */
    /* MAIN-RSA-METHODS---------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */
    
    public static Integer changeMessage(Integer message, Integer power, Integer mod)
    {
        Integer newMessage = ((int)(((Double)(Math.pow(message.doubleValue(), power.doubleValue()))).doubleValue())) % mod;
        return newMessage;
    }

    public static pair<pair<Integer, Integer>, pair<Integer, Integer>> generateKeyPair()
    {
        pair<pair<Integer, Integer>, pair<Integer, Integer>> keyPair = new pair<pair<Integer, Integer>, pair<Integer, Integer>>(){};

        int floor = 666;
        int ceil = 7777;
        ArrayList<Integer> primes = generatePrimeList(floor, ceil);

        SecureRandom newSecureRandomInstance = new SecureRandom();

        int p = GENERAL.secureChooseInteger(primes, newSecureRandomInstance);
        int q = GENERAL.secureChooseInteger(primes, newSecureRandomInstance);
        int n = p*q;
        int phiN = (p-1) * (q-1);

        ArrayList<Integer> validEncryptionValues = validEList(phiN);
        ArrayList<Integer> wantedEncryptionValues = GENERAL.strongValues(validEncryptionValues);

        int e = GENERAL.secureChooseInteger(wantedEncryptionValues, newSecureRandomInstance);

        ArrayList<Integer> validDecryptionValues = validDList(phiN, e);
        ArrayList<Integer> wantedDecryptionValues = GENERAL.strongValues(validDecryptionValues);

        int d = GENERAL.secureChooseInteger(wantedDecryptionValues, newSecureRandomInstance);

        pair<Integer, Integer> publicKey = new pair<Integer, Integer>  (e , n);
        pair<Integer, Integer> privateKey = new pair<Integer, Integer> (d , n);

        keyPair = new pair<pair<Integer,Integer>, pair<Integer,Integer>>(publicKey, privateKey);

        return keyPair;
    }

    /* -------------------------------------------------------------------------------------- */
    /* CALCULATION-METHODS------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */

    private static ArrayList<Integer> validDList(int phiN, int E)
    {
        ArrayList<Integer> validDs = new ArrayList<Integer>(){};
        /*
            it's a valid D if
                E*D (mod phiN) = 1 (mod phiN)
            which means:
                E * D + (phiN)A = 1 + (phiN)B
                E * D = 1 + (phiN)(B-A)
                E * D = 1 + (phiN)k
                D = (1 + (phiN)k) / E

            search every multiple of E -> M  = E*t
            see if [(M - 1) % phiN] == 0
            -> IF YES:
                then it's valid,
                D = M / E  -> (t)

            generate 666 valid Ds 
        */
        int D = 0;
        int multipleE = 0;
        while (true)
        {
            multipleE += E;
            D += 1;
            int kPhiN = multipleE - 1;
            if ((kPhiN % phiN) == 0)
            {
                validDs.add(D);
                break;
            }
        }
        return validDs;
    }
    private static ArrayList<Integer> validEList(Integer phiN)
    {
        ArrayList<Integer> validEs = new ArrayList<Integer>(){};
        // it's a valid E if  (1 < x < phiN)  and  (GCD(phiN, E) == 1) 
        // all even numbers are out because phiN = (q-1)(p-1) both p or q are primes and one of them - 1 MUST become even
        for (Integer number = 2; number < phiN; number++)
        {
            if ((number & 1) == 1)
            {
                if ( (GENERAL.GCD(number, phiN)) == 1 )
                {
                    validEs.add(number);
                }
            }
        }
        return validEs;
    }
    private static ArrayList<Integer> generatePrimeList(int floor, int ceil) 
    { /* Includes both ends (if possible) */
        ArrayList<Integer> numbers = new ArrayList<Integer>(){};
        for (int number = 2; number <= ceil; ++ number)
        {
            numbers.add(number);
        }        
        ArrayList<Integer> checks = new ArrayList<Integer>(numbers);
        ArrayList<Integer> flaggedPrimes = new ArrayList<Integer>(){};
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
        ArrayList<Integer> primes = new ArrayList<Integer>(flaggedPrimes);
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
