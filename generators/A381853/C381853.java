import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;

// Mia Boudreau
// va1alo@outlook.com
// OEIS A381853 'non-obfuscated' a(n) with extra functions
// 'C' for 'clean'

public class C381853
{
    public static BigInteger a(int n)
    {
        return lcm(getCounts(n));
        // returning the LCM of the order of each index will be the
        // order of the entire permutation because the LCM is the
        // first time all the cycles 'line up'
    }

    public static BigInteger[] getCounts(int n)
    {
        int[] arr = new int[n];
        int[] rule = getPerm(n);
        BigInteger[] counts = new BigInteger[n];

        for(int r = 0; r < 2; r++)
        {
            for(int i = 0; i < n; i++)
            {
                if(r % 2 == 0)
                {
                    arr[i] = i;
                }
                else // counts how long it takes for an index to reach its original value
                {
                    int a = rule[arr[i]];
                    BigInteger count = BigInteger.ONE;
                    while(a != arr[i])
                    {
                        count = count.add(BigInteger.ONE);
                        a = rule[a];
                    }
                    counts[i] = count;
                }
            }

        }
        return counts;
    }

    // returns specific permutation for given n
    public static int[] getPerm(int n)
    {
        int[] perm = new int[n];

        for(int r = 0; r < 2; r++)
        {
            for(int i = 0; i < n; i++)
            {
                if(r % 2 == 0) // fills an array with 0...n-1
                {
                    perm[i] = i;
                }
                else // successively applies swaps
                {
                    swap(perm, 2 * i % n, i);
                }
            }
        }
        return perm;
    }

    // returns a swapping rule for a given n
    public static int[] getRule(int n)
    {
        int[] rule = new int[n];
        for(int r = 0; r < 2; r++)
        {
            for(int i = 0; i < n; i++)
            {
                if(r % 2 == 0)
                {
                    rule[i] = i;
                }
                else
                {
                    rule[i] = (2 * i) % n;
                }
            }
        }
        return rule;
    }

    // returns a string representation of a rule or a permutation
    public static String getReadableRule(int[] rule, int base, boolean commas)
    {
        String s = "";
        for(int i = 0; i < rule.length; i++)
        {
            s += Integer.toString(rule[i], base);
            if(commas) s += ",";
        }
        return s.toUpperCase(Locale.getDefault());
    }

    // returns LCM of (1...n-1)
    public static BigInteger lcm1n(int n)
    {
        BigInteger[] arr = new BigInteger[n];
        for(int i = 1; i < n; i++)
        {
            arr[i - 1] = BigInteger.valueOf(i);
        }
        arr[n-1] = BigInteger.ONE;
        return lcm(arr);
    }

    public static BigInteger lcm(BigInteger... in)
    {
        BigInteger a = in[0];
        for(int i = 1; i < in.length; i++)
        {
            BigInteger n1 = a;
            BigInteger n2 = in[i];
            BigInteger gcd = n1.gcd(n2);
            a = a.divide(gcd).multiply(n2);
        }
        return a;
    }

    public static void swap(int[] arr, int i, int j)
    {
        arr[i] = (arr[i] + arr[j]) - (arr[j] = arr[i]);
    }

    public static int pow(int base, int exponent)
    {
        int y = 1;
        for(int i = 0; i < exponent; i++)
        {
            y *= base;
        }
        return y;
    }
}
