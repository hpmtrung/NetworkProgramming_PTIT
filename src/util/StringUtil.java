package util;

public class StringUtil {

    /*
        Arrays.stream(splitByComma("he, dk   ,  dk, d  ,e")).forEach(System.out::println);
    */
    public static String[] splitByComma(String str) {
        return str.split("(\\s*),(\\s*)");
    }
    
    /*
        fillSpace(10, "Hello", true)    => '     Hello'
        fillSpace(10, "Hello", false)   => 'Hello     '
    */
    public static String fillSpace(int n, String str, boolean left) {
        return String.format("%" + (left ? "" : "-") + n + "s", str);
    }
    

    public static boolean isBlank(String str) {
        return str.trim().isEmpty();
    }
    
    /*
        Whole Numbers – ^\d+$
        Decimal Numbers – ^\d*\.\d+$
        Whole + Decimal Numbers – ^\d*(\.\d+)?$
        Alphanumeric without space – ^[a-zA-Z0-9]*$
        Alphanumeric with space – ^[a-zA-Z0-9 ]*$
    */
    public static boolean matchEmail(String str) {
//        String part1 = "[a-zA-Z0-9._%-]+";
//        String part2 = "@[a-zA-Z0-9.-]+";
//        String part3 = "\\.[a-zA-Z]{2,6}";
        return str.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$");
    }
    
    public static String capitalize(String str) {
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            sb.append(words[i].substring(0, 1).toUpperCase());
            if (words[i].length() > 1)
                sb.append(words[i].substring(1).toLowerCase());
            if (i != n - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    }
    
}
