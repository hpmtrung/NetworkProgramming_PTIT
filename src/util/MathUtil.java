package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MathUtil {

    public static int random(int left, int right) {
        return (int) (Math.random() * (right - left + 1)) + left;
    }
    
    /*
        To sort DESC
        Integer[] a = new Integer[]{9, 3, 1, 2, 19, 5};
        Collections.sort(Arrays.asList(a), (x, y) -> x < y ? 1 : (x == 0 ? 0 : -1));
    */
    public void sort(Integer[] arr) {
        Collections.sort(Arrays.asList(arr));
    }
    
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= (int) Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    public static int ucln(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (a != 0 && b != 0) {
            if (a > b) {
                a %= b; // a = a % b
            } else {
                b %= a;
            }
        }
        return a + b; //
    }

    public static int bcnn(int a, int b) {
        int t = a * b;
        int ucln = ucln(a, b);
        return t / ucln;
    }
    
    public int fibo(int n) {
        if (n == 1 || n == 2) return 1;
        int f1 = 1, f2 = 1;
        for (int i = 3; i <= n; i++) {
            int t = f1 + f2;
            f1 = f2;
            f2 = t;
        }
        return f2;
    }
    
    public static boolean isSquare(int n) {
        if (n < 1) return false;
        for (int i = 1; i * i < n; i++) {
            if (i * i == n) return true;
        }
        return false;
    }
    
    public static boolean isPerfect(int n) {
        if (n < 1) return false;
        int sum = 0;
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) sum += i;
        }
        return sum == n;
    }
    
    public Pair ucln_bcnn_ofArray(String[] strs) {
        int[] arrInt = Arrays.stream(strs).mapToInt(Integer::parseInt).toArray();
        int ucln = arrInt[0];
        int bcnn = arrInt[0];
        for (int i = 0; i < arrInt.length; i++) {
            ucln = MathUtil.ucln(ucln, arrInt[i]);
            bcnn = bcnn * arrInt[i] / MathUtil.ucln(bcnn, arrInt[i]);
        }
        return new Pair(ucln, bcnn);
    }
    
    public static class Pair {
        public int x;
        public int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        Fraction f1 = new Fraction(1, 10);
        Fraction f2 = new Fraction(3, 7);
        System.out.println(Fraction.sum(f1, f2));
        System.out.println(Fraction.subtract(f1, f2));
    }
    
}
