package util;

import java.util.Arrays;

public class MatrixUtil {

    public static int[] cheoChinh(int[][] mat) {
        int nr = mat.length;
        int nc = mat[0].length;
        int mn = Math.min(nr, nc);
        int[] res = new int[mn];
        for (int i = 0; i < mn; i++) {
            res[i] = mat[i][i];
        }
        return res;
    }
    
    public static int[] cheoPhu(int[][] mat) {
        int nr = mat.length;
        int nc = mat[0].length;
        int mn = Math.min(nr, nc);
        int[] res = new int[mn];
        for (int i = 0; i < mn; i++) {
            res[i] = mat[i][nc - 1 - i];
        }
        return res;
    }
    
    public static void main(String[] args) {
        int[][] mat = new int[][] {
            {1, 2, 3, 4},  
            {5, 6, 7, 8},  
            {9, 10, 11, 12}, 
            {13, 14, 15, 16}, 
        };
        Arrays.stream(cheoChinh(mat)).forEach(System.out::println);
        System.out.println("");
        Arrays.stream(cheoPhu(mat)).forEach(System.out::println);
    }
    
}
