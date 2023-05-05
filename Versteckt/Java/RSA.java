import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class RSA 
{
    public static void main(String[] args) 
    {
        generatePrimeList(0, 10);
    }
    private static ArrayList<Integer> generatePrimeList(int floor, int roof) 
    {
        ArrayList<Integer> numbers = new ArrayList<>(){};
        for (int number = floor; number <= roof; ++ number)
        {
            numbers.add(number);
        }
        
        ArrayList<Integer> primes = numbers;
        ArrayList<Integer> copycat = primes;
        int maxPrime = ((Double)(Math.sqrt(roof))).intValue();
        ArrayList<Integer> flaggedPrimes = new ArrayList<>(Arrays.asList(0,1)){};

        int currentPrime = 1;
        for (Integer number : numbers)
        {
            Boolean ended = false;
            for (Integer nextPrime : primes)
            {
                if (! (flaggedPrimes.contains(nextPrime)))
                {
                    currentPrime = nextPrime;
                    ended = true;
                }
            }
            if (ended)
            {
                break;
            }
        }

        /*
         * make a list of all numbers = [floor -> roof] INCLUDING roof
         * do a seive
         */

        return primes;
    }
}
