package util;

import javax.swing.JOptionPane;

public class MessageUtil {

    public static void showInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "", JOptionPane.ERROR_MESSAGE);
    }
    
}
