package example.findpath;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                ServerUDP server = ServerUDP.getInstance();
                server.start();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

        ClientUDPFrame client = new ClientUDPFrame();
        client.setVisible(true);
    }
}
