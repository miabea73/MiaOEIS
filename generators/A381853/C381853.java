import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;

// Mia Boudreau
// va1alo@outlook.com
// OEIS A381853 'non-obfuscated' a(n)
// 'C' for 'cleaner'

public class C381853
{
    public static BigInteger a(int n)
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

        System.out.println(Arrays.toString(counts));
        return lcm(counts);
        // returning the LCM of the order of each index will be the
        // order of the entire permutation because the LCM is the
        // first time all the cycles 'line up'
    }

    // returns specific permutation for given n
    public static int[] getPerm(int n)
    {
        int[] p = new int[n];

        for(int r = 0; r < 2; r++)
        {
            for(int i = 0; i < n; i++)
            {
                if(r % 2 == 0)
                {
                    p[i] = i;
                }
                else
                {
                    swap(p, 2 * i % n, i);
                }
            }
        }
        return p;
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
            if(commas) s+= ",";
        }
        return s.toUpperCase(Locale.getDefault());
    }

    // returns a string representation of a rule or a permutation
    public static String getReadableRule(BigInteger rule, int base, boolean commas)
    {
        String s = "";
        for(int i = 0; i < rule.bitCount() / 8; i++)
        {
            s += rule.toString(base);
            if(commas) s+= ",";
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

    public static BigInteger[] pigeonhole(int n)
    {
        int length = pow(2, n);
        BigInteger[] arr = new BigInteger[length];
        for(int i = 1; i < length; i *= 2)
        {
            arr[i] = BigInteger.valueOf(i);
            arr[i + 1] = BigInteger.valueOf((int)(Math.log(i) / Math.log(2)));
            if(i > 2)
            {
                for(int dn = -i; dn < length / 4-1; dn++)
                {
                    if(i + dn > 0 && i + dn < length)
                    {
                        arr[i + pow(2, Math.abs(dn)) * sign(dn)] = BigInteger.valueOf((long) pow(2, dn) * (i - dn));
                    }
                }
            }
        }

        return arr;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(pigeonhole(4)));

    }

    public static int sign(long x)
    {
        return x > 0 ? 1 : 0;
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

    public static int log2(int a)
    {
        return (int) Math.ceil(Math.log(a) / Math.log(2));
    }
}
