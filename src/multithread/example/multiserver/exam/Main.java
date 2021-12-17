package multithread.example.multiserver.exam;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                ServerTCP server = ServerTCP.getInstance();
                server.start();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

        LoginForm form = new LoginForm();
        form.setVisible(true);
    }
}
