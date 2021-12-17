package example.simple.random;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

    public static final int PORT = 8888;

    private ServerSocket serverSocket;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private int rand;

    public ServerTCP() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server dang chay");
        client = serverSocket.accept();
        System.out.println("Client ket noi");
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        rand = (int) (Math.random() * 100);
    }

    public void run() {
        System.out.println("so: " + rand);
        try {
            while (true) {
                int n = dis.readInt();
                if (n > rand) {
                    dos.writeUTF("lon hon so ngau nhien");
                } else if (n < rand) {
                    dos.writeUTF("nhỏ hon so ngau nhien");
                } else {
                    dos.writeUTF("dung");
                }
            }
        } catch (SocketException ex) {
            System.out.println("Client da dong");
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ServerTCP server = new ServerTCP();
            server.run();
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
