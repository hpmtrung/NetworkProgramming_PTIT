package core.multithread;

import example.accountregister.MulticlientServerTCP;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    public ClientTCP() throws IOException {
        client = new Socket("localhost", MulticlientServerTCP.PORT);
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
    }
    
    public void close() {
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void process() {
        // ...
    }

    public static void main(String[] args) {
        try {
            ClientTCP client = new ClientTCP();
            client.process();
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
