import java.math.BigInteger;

// Mia Boudreau
// va1alo@outlook.com
// runs in linear time

public class A384184
{
    // 'n' (array size) with  'm' (multiplier)
    public static BigInteger a(int n, int m)
    {
        return lcm(getCounts(n, m));
    }

    // OEIS is with m = 2.
    public static BigInteger a(int n)
    {
        return lcm(getCounts(n, 2));
    }

    // returns the order of each index
    public static BigInteger[] getCounts(int n, int m)
    {
        int[] rule = getPerm(n, m);
        BigInteger[] counts = new BigInteger[n];
        boolean[] visited = new boolean[n];

        for(int i = 0; i < n; i++)
        {
            if(!visited[i])
            {
                int count = 0;
                int j = i;
                do
                {
                    visited[j] = true;
                    j = rule[j];
                    count++;
                }
                while (j != i);

                BigInteger c = BigInteger.valueOf(count);
                do
                {
                    counts[j] = c;
                    j = rule[j];
                }
                while (j != i);
            }
        }
        return counts;
    }

    // Returns the specific permutation for given n and multiplier.
    public static int[] getPerm(int n, int m)
    {
        int[] perm = initArr(n);
        for(int i = 0; i < n; i++)
        {
            swap(perm, m * i % n, i);
        }
        return perm;
    }

    // returns a swapping rule for a given n
    public static int[] getRule(int n, int m)
    {
        int[] rule = initArr(n);
        for(int i = 0; i < n; i++)
        {
            rule[i] = (m * i) % n;
        }
        return rule;
    }

    // returns an array with 0...n-1
    public static int[] initArr(int n)
    {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++)
        {
            arr[i] = i;
        }
        return arr;
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
}
