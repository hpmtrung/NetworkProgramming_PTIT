package core.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AServerTCP {

    protected ServerSocket serverSocket;
    protected Socket client;
    protected DataInputStream dis;
    protected DataOutputStream dos;

    public AServerTCP() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Server đang chạy.");
            client = serverSocket.accept();
            System.out.println("Server đang cho client ket noi");
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(AServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(AServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        try {
            while (true) {
                task();
            }
        } catch (IOException ex) {
            Logger.getLogger(AServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract void task() throws IOException;

}
