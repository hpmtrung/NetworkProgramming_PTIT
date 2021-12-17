package util;

import java.io.Serializable;

public class Fraction implements Serializable {

    private int num;
    private int dem;

    public Fraction() {
        num = 0;
        dem = 1;
    }

    public Fraction(int numerator, int denominator) {
        this.num = numerator;
        this.dem = denominator;
    }

    private static int ucln(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        while (x != 0 && y != 0) {
            if (x > y) {
                x %= y;
            } else {
                y %= x;
            }
        }
        return x + y;
    }

    public static Fraction sum(Fraction x, Fraction y) {
        int dem = x.getDem() * y.getDem();
        int num = x.getNum() * y.getDem() + y.getNum() * x.getDem();
        int ucln = ucln(dem, num);
        return new Fraction(num / ucln, dem / ucln);
    }

    public static Fraction subtract(Fraction x, Fraction y) {
        int dem = x.getDem() * y.getDem();
        int num = x.getNum() * y.getDem() - y.getNum() * x.getDem();
        int ucln = ucln(dem, num);
        return new Fraction(num / ucln, dem / ucln);
    }

    public int getNum() {
        return num;
    }

    public int getDem() {
        return dem;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDem(int dem) {
        if (dem == 0) {
            throw new IllegalArgumentException("Mau so bang 0");
        }
        this.dem = dem;
    }

    @Override
    public String toString() {
        return "Fraction{" + "numerator=" + num + ", demominator=" + dem + '}';
    }

}
