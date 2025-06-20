import java.util.ArrayList;
import java.util.List;

public class A155072Funcs
{
    public static void main(String[] args)
    {
        int[] A155072 = {17, 41, 97, 137, 193, 313, 401, 409, 449, 521, 569, 761, 769, 809, 857, 929, 977};
        int[] A115591 = {7, 17, 23, 41, 47, 71, 79, 97, 103, 137, 167, 191, 193, 199, 239, 263, 271};

        System.out.println("A155072(n):");
        for (int n : A155072) check(n);

        System.out.println("A115591(n):");
        for (int n : A115591) check(n);
    }

    public static void check(int n)
    {
        int order = multiplicativeOrder(2, n);
        int res = countResidues(n, true);
        int expectedOrder = (n - 1) / 2;
        int expectedRes = (n - 1) / 4;

        System.out.println(
                "n: " + n +
                "\nMultiplicative order of 2 mod n: " + order +
                "\nResidues >= n/2: " + res +
                "\n" + (order == expectedOrder) + ", " + (res == expectedRes) +"\n");
    }

    public static int gcd(int a, int b)
    {
        while (b != 0)
        {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    // residues comparing n/2 for 2^n mod n
    public static int countResidues(int n, boolean big)
    {
        List<Integer> residues = new ArrayList<>();

        int pow = 2 % n;
        while(pow != 1)
        {
            residues.add(pow);
            pow = (2 * pow) % n;
        }

        int count = 0;
        for (int residue : residues) if(big ? residue >= n / 2 : residue <= n / 2) count++;

        return count - 1;
    }

    // multiplicative order of b % n
    public static int multiplicativeOrder(int b, int n)
    {
        if (gcd(b, n) != 1)
        {
            return -1;
        }

        int order = 1;
        int result = b % n;
        while (result != 1)
        {
            result = (result * b) % n;
            order++;
        }
        return order;
    }
}