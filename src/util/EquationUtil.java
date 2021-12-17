package util;

import java.util.Arrays;

public class EquationUtil {

    /*
        System.out.println(solve1(2, 3));
        System.out.println(solve1(0, 1));
        System.out.println(solve1(0, 0));
     */
    public static String solve1(int a, int b) {
        if (a == 0) {
            if (b == 0) {
                return "Phuong trinh vo so nghiem";
            } else {
                return "Phuong trinh vo nghiem";
            }
        } else {
            return String.format("#%.1f", -b * 1.0 / a);
        }
    }

    /*
        System.out.println(solve2(2, 3, 1));
    */
    public static String solve2(int a, int b, int c) {
        double delta = b * b - 4 * a * c;
        if (delta < 0) {
            return "Phuong trinh vo nghiem";
        } else if (delta == 0) {
            double x = -b / (2 * a);
            return String.format("#%f#%f", x, x);
        } else {
            double t = Math.sqrt(delta);
            return String.format("#%.1f#%.1f", (-b + t) / (2 * a), (-b - t) / (2 * a));
        }
    }

    public static void main(String[] args) {
        System.out.println(solve2(2, 3, 1));
    }

}
